package com.enjogu.bank.account.api;

import com.enjogu.bank.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class AccountControllerTestIT {
    private final AccountService accountService;
    private final MockMvc mockMvc;

    private static final String TEST_ACCOUNT_NUMBER = "2342";

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("get balance should succeed for existing account")
    void getBalance_01() throws Exception {
        mockMvc.perform(get("/account/balance/{account}", TEST_ACCOUNT_NUMBER)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("get balance should return 404 for non-existent account")
    void getBalance_02() throws Exception {
        mockMvc.perform(get("/account/balance/{account}", "no-account")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("check deposit amount validation")
    void postDeposit_01() throws Exception {
        mockMvc.perform(post("/account/deposit/ac01/40001")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("check withdraw amount validation")
    void postWithdraw_01() throws Exception {
        mockMvc.perform(post("/account/withdraw/ac01/50001")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}