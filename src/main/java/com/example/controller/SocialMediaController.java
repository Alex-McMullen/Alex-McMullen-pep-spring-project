package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.h2.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController 
{
    AccountService accountService;
    MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService)
    {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    /**
     * Post endpoint to handle any account creation or register requests
     * Will return 200 status upon successful registration
     * Will return 409 status if the username is already taken
     * Will return 400 status if the registration fails for other reasons
     * 
     * @param account, the account that is being registered
     * @return the registered account
     */
    @PostMapping("register")
    public ResponseEntity<Account> postAccountCreation(@RequestParam Account account)
    {
        if(account != null)
        {
            if(accountService.getAccountByUsername(account.getUsername()) == null)
            {
                return ResponseEntity.status(200).body(accountService.persistAccount(account));
            }
            else
            {
                return ResponseEntity.status(409).body(null);
            }
        }
        else
        {
           return ResponseEntity.status(400).body(null); 
        }
    }

    /**
     * Post endpoint to handle any logins
     * Will return 200 status upon successful login
     * Will retrun 401 status if the credentials are incorrect
     * 
     * @param username, the username of the account to be logged into
     * @param password, the password of the account to be logged into
     * @return the account that was logged into
     */
    @PostMapping("login")
    public ResponseEntity<Account> postAccountLogin(@RequestParam String username, @RequestParam String password)
    {
        if(accountService.getAccountByUsername(username) != null)
        {
            return ResponseEntity.status(200).body(accountService.loginAccount(username, password));
        }
        else
        {
            return ResponseEntity.status(401).body(null);
        }
    }

    /**
     * Post endpoint to handle any messsage creation requests
     * Will return 200 status upon successful creation of the message
     * Will return 400 status if the message creation is not successful for any reason
     * 
     * @param message, the message that is being created
     * @return the message that was created
     */
    @PostMapping("messages")
    public ResponseEntity<Message> postMessageCreation(@RequestParam Message message)
    {
        if(messageService.getMessagesByUser(message.getPosted_by()) != null)
        {
            if(message.getMessage_text().length() > 0 && message.getMessage_text().length() < 255)
            {
                return ResponseEntity.status(200).body(messageService.persistMessage(message));
            }
        }
        return ResponseEntity.status(400).body(null);
    }

    /**
     * Get endpoint to handle requests to retrieve all messages in the database
     * Will return 200 upon successful retrieval
     * 
     * @return a list of all messages in the database
     */
    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessages()
    {
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

    /**
     * Get endpoint to handle requests for specific messages based on their message_id
     * Will return 200 upon successful retrieval of the message
     * 
     * @param message_id, the id of the message to be retrieved
     * @return the retrieved messgage
     */
    @GetMapping("messages/{message_id}")
    public ResponseEntity<Message> getMessageById(@PathVariable int message_id)
    {
        return ResponseEntity.status(200).body(messageService.getMessageById(message_id));
    }

    /**
     * Delete endpoint to handle requests to delete a message
     * Will return 200 upon successful deletion of the message
     * 
     * @param message_id, the id of the message to be deleted
     * @return the number of rows that were updated
     */
    @DeleteMapping("messages/{message_id}")
    public int deleteMessageById(@PathVariable int message_id)
    {
        return messageService.deleteMessageById(message_id);
    }

    /**
     * Patch endpoint to handle requests to update a message
     * Will return 200 status upon successful message update
     * Will return 400 status if the update is not successful for any reason
     * 
     * @param message_id, the id of the message to be updated
     * @param message_text, the new message text
     * @return the number of rows updated
     */
    @PatchMapping("messages/{message_id}")
    public ResponseEntity<Integer> patchMessageById(@PathVariable int message_id, @RequestBody String message_text)
    {
        if(messageService.getMessageById(message_id) != null)
        {
            if(message_text.trim() == null || message_text.trim().isEmpty())
            {
                return ResponseEntity.status(400).body(0);
            }
            else if(!StringUtils.isNullOrEmpty(message_text.trim()))
            {
                if(message_text.length() < 255)
                {
                    return ResponseEntity.status(200).body(messageService.updateMessageById(message_text, message_id));
                }
            }
        }
        return ResponseEntity.status(400).body(0);
    }

    /**
     * Get endpoint to handle requests to retrive all messages from a specific user
     * Will return 200 status upon successful retrieval of the messages
     * 
     * @param account_id, the account id of the user who posted the messages that are being retrieved
     * @return a list of all messages posted by a particular user
     */
    @GetMapping("accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getMessagesByUser(@PathVariable int account_id)
    {
        return ResponseEntity.status(200).body(messageService.getMessagesByUser(account_id));
    }
}
