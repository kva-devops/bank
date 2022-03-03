package com.example.bank.repository;

import com.example.bank.model.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface AccountRepository extends CrudRepository<Account, Integer> {
    @Query("SELECT a FROM Account a WHERE a.requisite = :requisite")
    Optional<Account> findByRequisite(@Param("requisite")String requisite);
}
