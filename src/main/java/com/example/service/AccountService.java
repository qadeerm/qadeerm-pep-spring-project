package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.*;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
    public Account getAccount(Integer accountId) throws ResourceNotFoundException {              
            return accountRepository.findById(accountId)
            .orElseThrow(()->new ResourceNotFoundException("Account ID : "+ accountId + " not found"));                
    }
    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }
    public Account loginAccount(Account account) throws NotAuthorisedException {
        return accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword())
            .orElseThrow(()->new NotAuthorisedException("UserName or Password not valid/found"));
    }
    public Account registerAccount(Account account) throws DuplicateAccountException {
        
        Optional<Account>  optional = accountRepository.findByUsername(account.getUsername());                
        if (optional.isEmpty())     
            return accountRepository.save(account);
        else
            throw new DuplicateAccountException(account.getUsername() + " Duplicate !");
    }
    public void deleteAccount(Integer accountId) {
        accountRepository.deleteById(accountId);
    }

}
