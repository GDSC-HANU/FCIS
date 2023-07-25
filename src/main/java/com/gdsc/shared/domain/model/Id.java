package com.gdsc.shared.domain.model;

import com.gdsc.shared.domain.exception.ValidateException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Id {
    public final String value;

    public static Result<Id, ValidateException> parse(String value) {
        if (isValidId(value))
            return Result.success(new Id(value));
        return Result.failed(new ValidateException("Invalid id"));
    }

    public static boolean isValidId(String id) {
        // Unimplemented
        return true;
    }
}
