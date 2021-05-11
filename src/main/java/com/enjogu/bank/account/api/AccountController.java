package com.enjogu.bank.account.api;

import com.enjogu.bank.account.exception.InvalidTransactionException;
import com.enjogu.bank.account.exception.NotFoundException;
import com.enjogu.bank.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.math.BigDecimal;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
public class AccountController implements com.enjogu.bank.account.api.AccountApi {
    private final AccountService accountService;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return AccountApi.super.getRequest();
    }

    @Override
    public ResponseEntity<BigDecimal> getBalance(String accountNumber) throws NotFoundException {
        return ResponseEntity.of(Optional.ofNullable(accountService.getBalance(accountNumber)));
    }

    @Override
    public ResponseEntity<Void> postDeposit(String accountNumber, BigDecimal amount) throws NotFoundException {
        accountService.deposit(accountNumber, amount);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<Void> postWithdraw(String accountNumber, BigDecimal amount)
            throws NotFoundException, InvalidTransactionException {
        accountService.withdraw(accountNumber, amount);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
