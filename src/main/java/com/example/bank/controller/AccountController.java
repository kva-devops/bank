package com.example.bank.controller;

import com.example.bank.model.Account;
import com.example.bank.model.DepositDTO;
import com.example.bank.model.TransferDTO;
import com.example.bank.model.WithdrawDTO;
import com.example.bank.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create-account")
    public ResponseEntity<Account> create(@RequestBody Account account) {
        return accountService.saveAccount(account);
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> transferMoney(@RequestBody TransferDTO transferDTO) {
        return accountService.transfer(transferDTO.getSenderRequisite(),
                                       transferDTO.getRecipientRequisite(),
                                       transferDTO.getAmount());
    }

    @PostMapping("/deposit")
    public ResponseEntity<Void> depositMoney(@RequestBody DepositDTO depositDTO) {
        return accountService.deposit(depositDTO.getRecipientRequisite(), depositDTO.getAmount());
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Void> withdrawMoney(@RequestBody WithdrawDTO withdrawDTO) {
        return accountService.withdraw(withdrawDTO.getOwnerRequisite(), withdrawDTO.getAmount());
    }

}
