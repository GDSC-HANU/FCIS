package com.gdsc.inventory.domain.service;

import com.gdsc.inventory.domain.model.Inventory;
import com.gdsc.inventory.domain.model.InventoryItem;
import com.gdsc.shared.domain.exception.ValidateException;
import com.gdsc.shared.domain.model.NonEmptyString;
import com.gdsc.shared.domain.model.PositiveDouble;
import com.gdsc.shared.domain.model.Result;
import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CheckInServiceInputConverter {
    public static Result<ImmutableList<InventoryItem>, ValidateException> convertToInventoryItems(
            ImmutableList<CheckInServiceInput.InputInventoryItem> inputInventoryItems
    ) {
        // Step 1: Convert all InventoryItem and get results
        final ImmutableList<Result<InventoryItem, ValidateException>> inventoryItemResults = convertToInventoryItemResults(inputInventoryItems);

        // Step 2: Check if any InventoryItem convert result is failed
        final Optional<Result<InventoryItem, ValidateException>> firstFailedInventoryItemResult = Result.firstFailedResult(inventoryItemResults);
        if (firstFailedInventoryItemResult.isPresent())
            return Result.failed(firstFailedInventoryItemResult.get().failedData);

        // Step 3: Convert all InventoryItem convert result to InventoryItem
        final ImmutableList<InventoryItem> inventoryItems = ImmutableList.copyOf(
                inventoryItemResults
                        .stream()
                        .map(inventoryItemResult -> inventoryItemResult.successData)
                        .collect(Collectors.toList())
        );
        return Result.success(inventoryItems);
    }

    private static ImmutableList<Result<InventoryItem, ValidateException>> convertToInventoryItemResults(
            ImmutableList<CheckInServiceInput.InputInventoryItem> inputInventoryItems
    ) {
        return ImmutableList.copyOf(
                inputInventoryItems
                        .stream()
                        .map(CheckInServiceInputConverter::convertToInventoryItem)
                        .collect(Collectors.toList())
        );
    }

    private static Result<InventoryItem, ValidateException> convertToInventoryItem(CheckInServiceInput.InputInventoryItem inputInventoryItem) {
        final Result<NonEmptyString, ValidateException> parseNameResult = NonEmptyString.parse(inputInventoryItem.name);
        final Result<PositiveDouble, ValidateException> parseHeightResult = PositiveDouble.parse(inputInventoryItem.height);
        final Result<PositiveDouble, ValidateException> parseLengthResult = PositiveDouble.parse(inputInventoryItem.length);
        final Result<PositiveDouble, ValidateException> parseWidthResult = PositiveDouble.parse(inputInventoryItem.width);
        final Optional<Result<?, ValidateException>> firstFailedParseResult = Result.firstFailedResult(
                parseNameResult,
                parseHeightResult,
                parseLengthResult,
                parseWidthResult
        );
        return firstFailedParseResult
                .<Result<InventoryItem, ValidateException>>map(
                        validateExceptionResult ->
                                Result.failed(validateExceptionResult.failedData)
                )
                .orElseGet(() -> InventoryItem.create(
                        parseNameResult.successData,
                        parseHeightResult.successData,
                        parseLengthResult.successData,
                        parseWidthResult.successData
                ));
    }
}
