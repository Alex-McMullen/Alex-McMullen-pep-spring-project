package com.example.repository;

import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>
{
    /**
     * Retrieve an account by its username and password
     * 
     * @param username, the username of the account to be retrieved
     * @param password, the password of the account to be retrieved
     * @return the retrieved account
     */
    @Query("from Account where username = :userName and password = :password")
    Account logInAccount(@Param("userName") String username, @Param("password") String password);

    /**
     * Retrieve an account by its username
     * 
     * @param username, the username of the account to be retrieved
     * @return the retrieved account
     */
    @Query("from Account where username = :userName")
    Account getAccountByUsername(@Param("userName") String username);

    /**
     * Retrieve an account by its id
     * 
     * @param account_id, the id of the account to be retrieved
     * @return the retrieved account
     */
    @Query("from Account where account_id = :accountId")
    Account getAccountById(@Param("accountId") int account_id);
}
