package com.gdsc.inventory.domain.service;

import com.gdsc.inventory.domain.model.InventoryItem;
import com.gdsc.shared.domain.exception.ValidateException;
import com.gdsc.shared.domain.model.Result;
import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CheckInServiceInput {
    public final String inventoryId;
    public final ImmutableList<InputInventoryItem> inventoryItems;
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class InputInventoryItem {
        public final String name;
        public final double height;
        public final double length;
        public final double width;
    }

}
