package com.example.service;

import java.util.*;
import com.example.exception.*;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
//import com.example.exception.ResourceNotFoundException;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message saveMessage(Message message) throws ResourceNotFoundException{   
        Optional<Account>  account = accountRepository.findById(message.getPostedBy());
            if (account.isEmpty()) throw new ResourceNotFoundException("Account # " + message.getPostedBy() + " not found");
            return messageRepository.save(message);
    }

    public Message getMessage(Integer messageId) throws ResourceNotFoundException {
      
        return messageRepository.findById(messageId)
                        .orElseThrow(()->new ResourceNotFoundStatusOkException("Message ID : "+ messageId + " not found"));     
    }
    public Boolean deleteMessage(Integer messageId)  {
        Optional<Message>  optional = messageRepository.findById(messageId);
        if (optional.isPresent()) {
            messageRepository.deleteById(messageId);
            return true;
        }
        else 
            return false;
    }
    public Message updateMessage(Integer messageId, Message newMessage) throws ResourceNotFoundException {
        Message oldMessage =null;
        Optional<Message>  optional = messageRepository.findById(messageId);
                //.orElseThrow(()->new ResourceNotFoundException(messageId + " not found")));
        if (optional.isPresent()){
            oldMessage = optional.get();
            oldMessage.setMessageText(newMessage.getMessageText());
            oldMessage = messageRepository.save(oldMessage);           
        }
        return oldMessage;
    }
    public List<Message> getUserMessages(Integer accountId) {
            return messageRepository.findAllByPostedBy(accountId);     
    }
    public List<Message> getUserMessagesByQery(Integer accountId) {
        return messageRepository.findAllByQuery(accountId);     
    }
    public List<Message> getUserMessagesByOrderByMessageIdDesc(Integer accountId) {
        return messageRepository.findAllByPostedByOrderByMessageIdDesc(accountId);     
    }
}

