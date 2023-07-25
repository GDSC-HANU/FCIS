package com.gdsc.inventory.infrastructure.api;

import com.gdsc.inventory.domain.service.CheckInService;
import com.gdsc.inventory.domain.service.CheckInServiceInput;
import com.gdsc.shared.domain.exception.DomainException;
import com.gdsc.shared.domain.model.Result;
import com.gdsc.shared.domain.model.Void;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CheckInAPI {
    private final CheckInService checkInService;

    @PostMapping("/inventory/checkIn")
    public ResponseEntity<?> checkIn(@RequestBody
                                     CheckInServiceInput input) {
        try {
            final Result<Void, DomainException> checkInResult = checkInService.checkIn(input);
            if (checkInResult.isSuccess)
                return ResponseEntity.status(HttpStatus.OK).body(checkInResult.successData);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(checkInResult.failedData);
        } catch (Throwable e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
}
