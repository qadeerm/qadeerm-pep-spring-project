package com.example.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.exception.ResourceNotFoundException;
import com.example.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private AccountService accountService;
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    @GetMapping()
    public ResponseEntity<List<Account>> getAllAccounts() {        
        return  ResponseEntity.status(HttpStatus.OK)
                .body(accountService.getAllAccounts());
    }
    @RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
    public ResponseEntity<Account> getAccount(@PathVariable Integer accountId)
                throws ResourceNotFoundException{           
        return ResponseEntity.status(HttpStatus.OK)
                            .body(accountService.getAccount(accountId)); 
    }
    @DeleteMapping(value = "/{accountId}")
    public ResponseEntity<String> deleteAccount(@PathVariable Integer accountId){
        accountService.deleteAccount(accountId);
        return ResponseEntity.status(HttpStatus.OK) //ACCEPTED
                            .body("Deleted ");
    }    
    
}
