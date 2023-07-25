package com.gdsc.shared.domain.model;

import com.gdsc.shared.domain.exception.ValidateException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PositiveDouble {
    public final double data;

    public static Result<PositiveDouble, ValidateException> parse(double rawDouble) {
        if (rawDouble <= 0)
            return Result.failed(new ValidateException("Double must be positive"));
        final PositiveDouble positiveDouble = new PositiveDouble(rawDouble);
        return Result.success(positiveDouble);
    }

    public double toDouble() {
        return data;
    }
}
