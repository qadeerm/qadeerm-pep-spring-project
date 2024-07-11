package com.example.controller;

import java.util.List;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.entity.Message;
import com.example.exception.ResourceNotFoundException;
import com.example.service.MessageService;

@RestController //@Controller + @ResponseBody
//@RequestMapping("/messages")
public class MessageController {
    
    private MessageService messageService;
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {        
       // List<Message> lst = messageService.getAllMessages(); 
        return  ResponseEntity
                    .status(HttpStatus.OK)
                    .body(messageService.getAllMessages());
    }
    //accounts/{accountId}/messages
    @RequestMapping(value = "accounts/{accountId}/messages", method = RequestMethod.GET)
    public ResponseEntity<List<Message>> getUserMessages(@PathVariable Integer accountId) {        
        return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(messageService.getUserMessages(accountId)); 
                        //.body(messageService.getUserMessagesByQery(accountId));
                        //.body(messageService.getUserMessagesByOrderByMessageIdDesc(accountId));
    }
    @RequestMapping(value = "/messages/{messageId}", method = RequestMethod.GET)
    public ResponseEntity<Message> getMessage(@PathVariable Integer messageId) throws ResourceNotFoundException {        
        return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(messageService.getMessage(messageId)); 
    }
    //@GetMapping(value = "/messages", params = "messageId")
    @GetMapping(value = "/messages", params = "messageId")
    public ResponseEntity<Message> getMessagebyQuery(@RequestParam Integer messageId) throws ResourceNotFoundException {        
        return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(messageService.getMessage(messageId));
    }
    
    @DeleteMapping(value = "/messages/{messageId}")
    public ResponseEntity<String> deleteAccount(@PathVariable Integer messageId) {
        if (messageService.deleteMessage(messageId))
            return ResponseEntity
                            .status(HttpStatus.OK)// ACCEPTED
                            .body("1 Row Deleted ");
        else
            return ResponseEntity
                            .status(HttpStatus.OK)// ACCEPTED
                            .body("");
    }
    @PostMapping("/messages")
    public ResponseEntity<Message> saveMessage(@RequestBody @Valid Message newMessage) throws ConstraintViolationException{
        messageService.saveMessage(newMessage);
        return ResponseEntity
                        .status(HttpStatus.OK) //ACCEPTED
                        .body(newMessage);
    }
    @PatchMapping(value = "/messages/{messageId}")
    public ResponseEntity<String> updateMessage(@RequestBody Message newMessage,@PathVariable Integer messageId) 
                throws ResourceNotFoundException {
       Message message = messageService.updateMessage(messageId,newMessage);
        if ( message != null)
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("1 rows Updated");        
        else{
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("error while updating");                                    
        }
    }

}
