package com.gdsc.inventory.domain.model;

import com.gdsc.shared.domain.exception.ValidateException;
import com.gdsc.shared.domain.model.NonEmptyString;
import com.gdsc.shared.domain.model.PositiveDouble;
import com.gdsc.shared.domain.model.Result;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE, toBuilder = true)
public class InventoryItem {
    public final NonEmptyString name;
    public final PositiveDouble height;
    public final PositiveDouble length;
    public final PositiveDouble width;

    public static Result<InventoryItem, ValidateException> create(NonEmptyString name,
                                                                  PositiveDouble height,
                                                                  PositiveDouble length,
                                                                  PositiveDouble width) {
        if (name == null)
            return Result.failed(new ValidateException("name must not be null"));
        if (height == null)
            return Result.failed(new ValidateException("height must not be null"));
        if (length == null)
            return Result.failed(new ValidateException("length must not be null"));
        if (width == null)
            return Result.failed(new ValidateException("width must not be null"));
        final InventoryItem inventory = InventoryItem.builder()
                .name(name)
                .height(height)
                .length(length)
                .width(width)
                .build();
        return Result.success(inventory);
    }

    public double volume() {
        return height.toDouble() * width.toDouble() * length.toDouble();
    }
}
