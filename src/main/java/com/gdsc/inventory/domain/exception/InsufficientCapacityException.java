package com.gdsc.inventory.domain.exception;

import com.gdsc.shared.domain.exception.DomainException;

public class InsufficientCapacityException extends DomainException {
    public InsufficientCapacityException() {
        super("Insufficient capacity");
    }
}
