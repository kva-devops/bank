package com.example.bank.controller;

import com.example.bank.BankApplication;
import com.example.bank.model.Account;
import com.example.bank.model.DepositDTO;
import com.example.bank.model.TransferDTO;
import com.example.bank.model.WithdrawDTO;
import com.example.bank.repository.AccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = BankApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountRepository accountRepository;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void simpleCheckExistingMockMvcAndRestTemplate() {
        Assert.assertNotNull(mockMvc);
    }

    @Test
    void whenCreateAccountAndReturnStatusCreated() throws Exception {
        Account account = Account.of("00001111", 100.00);
        mockMvc.perform(
                post("/create-account")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(account))
        ).andExpect(status().isCreated());
    }

    @Test
    void whenTransferMoneyThenReturnStatusOk() throws Exception {
        Account accountSender = Account.of("00003333", 200.00);
        Account accountRecipient = Account.of("00004444", 300.00);
        TransferDTO transferDTO = new TransferDTO(
                accountSender.getRequisite(), accountRecipient.getRequisite(), 100);
        Mockito.when(accountRepository.findByRequisite(accountSender.getRequisite())).thenReturn(java.util.Optional.of(accountSender));
        Mockito.when(accountRepository.findByRequisite(accountRecipient.getRequisite())).thenReturn(java.util.Optional.of(accountRecipient));
        mockMvc.perform(
                post("/transfer")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(transferDTO))
        ).andExpect(status().isOk());
    }

    @Test
    void whenDepositMoneyThenReturnStatusOk() throws Exception {
        Account accountSender = Account.of("00003333", 200.00);
        DepositDTO depositDTO = new DepositDTO(accountSender.getRequisite(), 100);
        Mockito.when(accountRepository.findByRequisite(accountSender.getRequisite())).thenReturn(java.util.Optional.of(accountSender));
        mockMvc.perform(
                post("/deposit")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(depositDTO))
        ).andExpect(status().isOk());
    }

    @Test
    void whenDepositMoneyAndAccountNotFoundThenReturnStatusNotFound() throws Exception {
        Account accountSender = Account.of("00003333", 200.00);
        DepositDTO depositDTO = new DepositDTO(accountSender.getRequisite(), 100);
        Mockito.when(accountRepository.findByRequisite(accountSender.getRequisite())).thenReturn(java.util.Optional.empty());
        mockMvc.perform(
                post("/deposit")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(depositDTO))
        ).andExpect(status().isNotFound());
    }

    @Test
    void whenWithdrawMoneyThenReturnStatusOk() throws Exception {
        Account accountRecipient = Account.of("00004444", 200);
        WithdrawDTO withdrawDTO = new WithdrawDTO(accountRecipient.getRequisite(), 100);
        Mockito.when(accountRepository.findByRequisite(accountRecipient.getRequisite())).thenReturn(java.util.Optional.of(accountRecipient));
        mockMvc.perform(
                post("/withdraw")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(withdrawDTO))
        ).andExpect(status().isOk());
    }

    @Test
    void whenTransferMoneyAndNotFoundRecipientAccountThenResponseStatusException() throws Exception {
        Account accountSender = Account.of("00003333", 200.00);
        Account accountRecipient = Account.of("00004444", 300.00);
        TransferDTO transferDTO = new TransferDTO(
                accountSender.getRequisite(), accountRecipient.getRequisite(), 100);
        Mockito.when(accountRepository.findByRequisite(accountSender.getRequisite())).thenReturn(java.util.Optional.of(accountSender));
        Mockito.when(accountRepository.findByRequisite(accountRecipient.getRequisite())).thenReturn(java.util.Optional.empty());
        mockMvc.perform(
                post("/transfer")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(transferDTO))
        ).andExpect(status().isNotFound());
    }

    @Test
    void whenWithdrawMoneyAndAccountHasNotEnoughAmountThenReturnBadRequestStatus() throws Exception {
        Account accountRecipient = Account.of("00004444", 200);
        WithdrawDTO withdrawDTO = new WithdrawDTO(accountRecipient.getRequisite(), 300);
        Mockito.when(accountRepository.findByRequisite(accountRecipient.getRequisite())).thenReturn(java.util.Optional.of(accountRecipient));
        mockMvc.perform(
                post("/withdraw")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(withdrawDTO))
        ).andExpect(status().isBadRequest());
    }
}