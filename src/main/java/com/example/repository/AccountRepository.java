package com.example.repository;

import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long>
{
    /**
     * Retrieve an account by its username and password
     * 
     * @param username, the username of the account to be retrieved
     * @param password, the password of the account to be retrieved
     * @return the retrieved account
     */
    @Query("from account where username = :userName and password = :password")
    Account logInAccount(@Param("userName") String username, @Param("password") String password);

    /**
     * Retrieve an account by its username
     * 
     * @param username, the username of the account to be retrieved
     * @return the retrieved account
     */
    @Query("from account where username = :userName")
    Account getAccountByUsername(@Param("userName") String username);
}
