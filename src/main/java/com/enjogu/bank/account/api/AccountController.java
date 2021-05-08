package com.enjogu.bank.account.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.math.BigDecimal;
import java.util.Optional;


@RestController
public class AccountController implements com.enjogu.bank.account.api.BalanceApi, com.enjogu.bank.account.api.WithdrawApi, com.enjogu.bank.account.api.DepositApi {

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return BalanceApi.super.getRequest();
    }

    @Override
    public ResponseEntity<BigDecimal> getBalance(String accountNumber) {
        return com.enjogu.bank.account.api.BalanceApi.super.getBalance(accountNumber);
    }

    @Override
    public ResponseEntity<Void> postDeposit(String accountNumber, BigDecimal amount) {
        return com.enjogu.bank.account.api.DepositApi.super.postDeposit(accountNumber, amount);
    }

    @Override
    public ResponseEntity<Void> postWithdraw(String accountNumber, BigDecimal amount) {
        return com.enjogu.bank.account.api.WithdrawApi.super.postWithdraw(accountNumber, amount);
    }
}
