package com.gdsc.inventory.domain.repository;

import com.gdsc.inventory.domain.model.Inventory;
import com.gdsc.shared.domain.exception.NotFoundException;
import com.gdsc.shared.domain.exception.SaveFailedException;
import com.gdsc.shared.domain.model.Id;
import com.gdsc.shared.domain.model.Result;
import com.gdsc.shared.domain.model.Void;

public interface InventoryRepository {
    Result<Inventory, NotFoundException> getById(Id id);

    Result<Void, SaveFailedException> save(Inventory inventory);
}
