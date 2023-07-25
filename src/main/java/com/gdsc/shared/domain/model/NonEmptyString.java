package com.gdsc.shared.domain.model;

import com.gdsc.shared.domain.exception.ValidateException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NonEmptyString {
    public final String data;

    public static Result<NonEmptyString, ValidateException> parse(String rawString) {
        if (rawString.isEmpty())
            return Result.failed(new ValidateException("String must not be empty"));
        final NonEmptyString nonEmptyString = new NonEmptyString(rawString);
        return Result.success(nonEmptyString);
    }
}
