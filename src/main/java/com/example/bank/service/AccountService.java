package com.example.bank.service;

import com.example.bank.model.Account;
import com.example.bank.repository.AccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public ResponseEntity<Account> saveAccount(Account account) {
        Optional<Account> buffAccount = accountRepository.findByRequisite(account.getRequisite());
        if (buffAccount.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account is already exists");
        }
        if ( account.getBalance() > 0) {
             Account result = accountRepository.save(
                    Account.of(account.getRequisite(),
                            account.getBalance()));
            return new ResponseEntity<>(
                    result,
                    HttpStatus.CREATED
            );
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Balance cannot be negative");
        }
    }

    public ResponseEntity<Void> transfer(String senderRequisite, String recipientRequisite, double amount) {
        Optional<Account> senderAccountOptional = accountRepository.findByRequisite(senderRequisite);
        Optional<Account> recipientAccountOptional = accountRepository.findByRequisite(recipientRequisite);
        Account senderAccount;
        Account recipientAccount;
        if (senderAccountOptional.isPresent()
            && recipientAccountOptional.isPresent()) {
            senderAccount = senderAccountOptional.get();
            recipientAccount = recipientAccountOptional.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sender or Recipient account not found");
        }
        if (senderAccount.getBalance() - amount > 0) {
            senderAccount.setBalance(senderAccount.getBalance() - amount);
            recipientAccount.setBalance(recipientAccount.getBalance() + amount);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sender or Recipient account hasn't enough funds on the balance");
        }
        accountRepository.save(senderAccount);
        accountRepository.save(recipientAccount);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> deposit(String recipientRequisite, double amount) {
        Optional<Account> recipientAccountOptional = accountRepository.findByRequisite(recipientRequisite);
        Account recipientAccount;
        if (recipientAccountOptional.isPresent()) {
            recipientAccount = recipientAccountOptional.get();
            recipientAccount.setBalance(recipientAccount.getBalance() + amount);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account doesn't exists");
        }
        accountRepository.save(recipientAccount);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> withdraw(String ownerRequisite, double amount) {
        Optional<Account> ownerAccountOptional = accountRepository.findByRequisite(ownerRequisite);
        Account ownerAccount;
        if (ownerAccountOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sender or Recipient account not exist");
        }
        if (ownerAccountOptional.get().getBalance() > amount) {
            ownerAccount = ownerAccountOptional.get();
            ownerAccount.setBalance(ownerAccount.getBalance() - amount);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account hasn't enough funds on the balance");
        }
        accountRepository.save(ownerAccount);
        return ResponseEntity.ok().build();
    }
}
