package com.example.service;

import com.example.repository.AccountRepository;
import com.example.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService 
{
    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository)
    {
        this.accountRepository = accountRepository;
    }

    /**
     * Add an account to the database as long as the username is free and the password is 4 or more characters
     * 
     * @param account, the account to be added
     * @return the added account
     */
    public Account persistAccount(Account account)
    {
        if(accountRepository.getAccountByUsername(account.getUsername()) == null)
        {
            if(account.getUsername().length() > 0 && account.getPassword().length() >= 4)
            {
                return accountRepository.save(account);
            }
        }
        return null;
    }

    /**
     * Login to an account using an account's username and password
     * 
     * @param username, the username of the account trying to be logged into
     * @param password, the password of the account trying to be logged into
     * @return the logged into account
     */
    public Account loginAccount(String username, String password)
    {
        if(accountRepository.getAccountByUsername(username) != null)
        {
            return accountRepository.logInAccount(username, password);
        }
        return null;
    }

    /**
     * Retrieve an account from the database by its username
     * 
     * @param username, the username of the account to be retrieved
     * @return the retrieved account
     */
    public Account getAccountByUsername(String username)
    {
        return accountRepository.getAccountByUsername(username);
    }

    /**
     * Retrieve an account from the database by its id
     * 
     * @param id, the id of the account to be retrieved
     * @return the retrieved account
     */
    public Account getAccountById(int id)
    {
        return accountRepository.getAccountById(id);
    }
}
