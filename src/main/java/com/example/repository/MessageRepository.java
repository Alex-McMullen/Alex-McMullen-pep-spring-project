package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>
{
    /**
     * Retrieve all messages from the database
     * 
     * @return a list of all messages in the database
     */
    @Query("from Message")
    List<Message> getAllMessages();

    /**
     * Retrieve a message by its message_id
     * 
     * @param message_id, the message_id of the message to be retrieved
     * @return the message that is being retrieved
     */
    @Query("from Message where message_id = :messageId")
    Message getMessageById(@Param("messageId") int message_id);

    /**
     * Delete a message from the database given its message_id
     * 
     * @param message_id, the message_id of the message to be deleted
     * @return the number of rows updated
     */
    @Transactional
    @Modifying
    @Query("delete from Message where message_id = :messageId")
    int deleteMessageById(@Param("messageId") int message_id);

    /**
     * Update the message_text of a message given the message_id
     * 
     * @param message_text, the new message_text
     * @param message_id, the id of the message to be updated
     * @return the number of rows updated
     */
    @Transactional
    @Modifying
    @Query("update Message set message_text = :messageText where message_id = :messageId")
    int updateMessageById(@Param("messageText") String message_text, @Param("messageId") int message_id);

    /**
     * Retrieve all messages posted by a particular user
     * 
     * @param posted_by, the id of the user who posted the messages
     * @return a list of all messages posted by a particular user
     */
    @Query("from Message where posted_by = :postedBy")
    List<Message> getMessagesByUser(@Param("postedBy") int posted_by);
}
