package com.gdsc.inventory.infrastructure.repository_implementation;

import com.gdsc.inventory.domain.model.Inventory;
import com.gdsc.inventory.domain.repository.InventoryRepository;
import com.gdsc.shared.domain.exception.NotFoundException;
import com.gdsc.shared.domain.exception.SaveFailedException;
import com.gdsc.shared.domain.model.Id;
import com.gdsc.shared.domain.model.Result;
import com.gdsc.shared.domain.model.Void;

public class InventoryMySQLRepository implements InventoryRepository {
    // Unimplemented
    @Override
    public Result<Inventory, NotFoundException> getById(Id id) {
        return null;
    }

    @Override
    public Result<Void, SaveFailedException> save(Inventory inventory) {
        return null;
    }
}
