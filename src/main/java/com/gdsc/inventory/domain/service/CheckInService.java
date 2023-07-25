package com.gdsc.inventory.domain.service;

import com.gdsc.inventory.domain.exception.InsufficientCapacityException;
import com.gdsc.inventory.domain.model.Inventory;
import com.gdsc.inventory.domain.model.InventoryItem;
import com.gdsc.inventory.domain.repository.InventoryRepository;
import com.gdsc.shared.domain.exception.DomainException;
import com.gdsc.shared.domain.exception.NotFoundException;
import com.gdsc.shared.domain.exception.SaveFailedException;
import com.gdsc.shared.domain.exception.ValidateException;
import com.gdsc.shared.domain.model.Id;
import com.gdsc.shared.domain.model.Result;
import com.gdsc.shared.domain.model.Void;
import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CheckInService {
    private final InventoryRepository inventoryRepository;

    public Result<Void, DomainException> checkIn(CheckInServiceInput input) {
        // Step 1: Parse inventoryId from input
        final Result<Id, ValidateException> parseInventoryIdResult = Id.parse(input.inventoryId);
        if (parseInventoryIdResult.isFailed())
            return Result.failed(parseInventoryIdResult.failedData);

        final Id parsedInventoryId = parseInventoryIdResult.successData;

        // Step 2: Get Inventory
        final Result<Inventory, NotFoundException> inventoryGetResult = inventoryRepository.getById(parsedInventoryId);
        if (inventoryGetResult.isFailed())
            return Result.failed(inventoryGetResult.failedData);

        final Inventory oldInventory = inventoryGetResult.successData;

        // Step 3: Convert InventoryItem list from input
        final Result<ImmutableList<InventoryItem>, ValidateException> convertToInventoryItemsResult = CheckInServiceInputConverter
                .convertToInventoryItems(input.inventoryItems);
        if (convertToInventoryItemsResult.isFailed())
            return Result.failed(convertToInventoryItemsResult.failedData);

        final ImmutableList<InventoryItem> inventoryItemsToCheckIn = convertToInventoryItemsResult.successData;

        // Step 4: Check-in the Inventory
        final Result<Inventory, InsufficientCapacityException> checkInResult = oldInventory.checkIn(inventoryItemsToCheckIn);
        if (checkInResult.isFailed())
            return Result.failed(checkInResult.failedData);

        final Inventory checkedInInventory = checkInResult.successData;

        // Step 5: Save the checked-in Inventory
        final Result<Void, SaveFailedException> saveCheckedInInventoryResult = inventoryRepository.save(checkedInInventory);
        if (saveCheckedInInventoryResult.isFailed())
            return Result.failed(saveCheckedInInventoryResult.failedData);

        return Result.success(Void.INSTANCE);
    }
}
