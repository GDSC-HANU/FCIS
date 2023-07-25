package com.gdsc.shared.domain.model;

import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Result<S, F> {
    public final S successData;
    public final F failedData;
    public final boolean isSuccess;

    public static <S, F> Result<S, F> success(S successData) {
        return new Result<>(
                successData,
                null,
                true
        );
    }

    public static <S, F> Result<S, F> failed(F failedData) {
        return new Result<>(
                null,
                failedData,
                false
        );
    }

    @SafeVarargs
    public static <F> Optional<Result<?, F>> firstFailedResult(Result<?, F>... results) {
        return Arrays.stream(results)
                .filter(Result::isFailed)
                .findFirst();
    }

    public static <S, F> Optional<Result<S, F>> firstFailedResult(ImmutableList<Result<S, F>> results) {
        return results
                .stream()
                .filter(Result::isFailed)
                .findFirst();
    }

    public boolean isFailed() {
        return !isSuccess;
    }
}
