package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.exception.DuplicateAccountException;
import com.example.exception.NotAuthorisedException;
import com.example.service.AccountService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
//@RestController // @Controller + @ResponseBody
//@RequestMapping("/api")
@RestController
public class SocialMediaController {

    private AccountService accountService;
    public SocialMediaController(AccountService accountService) {
        this.accountService = accountService;
    }
    @PostMapping("/login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account account) throws NotAuthorisedException{
        //accountService;
        return ResponseEntity.status(HttpStatus.OK) //ACCEPTED
                            .body(accountService.loginAccount(account));
    }
    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account account) throws DuplicateAccountException{
        if ((account.getUsername()=="") || (account.getPassword().length()<4))        
            return ResponseEntity.status(HttpStatus.BAD_REQUEST) //BAD_REQUEST
                            .body(account);
        else
            return ResponseEntity.status(HttpStatus.OK) //ACCEPTED
            .body(accountService.registerAccount(account));
    }
}  
