package com.example.service;

import com.example.repository.MessageRepository;
import com.example.entity.Message;

import org.h2.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService 
{
    MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository)
    {
        this.messageRepository = messageRepository;
    }

    /**
     * Add a message to the database if it was posted by an actual user, and if the message is between 0 and 255 characters long
     * 
     * @param message, the message to be added to the database
     * @return the added message
     */
    public Message persistMessage(Message message)
    {
        if(message.getMessage_text().length() > 0 && message.getMessage_text().length() < 255)
        {
            return messageRepository.save(message);
        }
        return null;
    }

    /**
     * Retrieve all messages in the database
     * 
     * @return a list of all messages in the database
     */
    public List<Message> getAllMessages()
    {
        return messageRepository.getAllMessages();
    }

    /**
     * Retrieve a message from the database by its id
     * @param id, the message_id of the message to be retrieved
     * @return the retrieved message, or nothing if no message exists
     */
    public Message getMessageById(int id)
    {
        if((Integer) id != null)
        {
            return messageRepository.getMessageById(id);
        }
        return null;
    }

    /**
     * Delete a message from the database given the message_id
     * @param id, the message_id of the message to be deleted
     * @return the number of rows updated
     */
    public int deleteMessageById(int id)
    {
        return messageRepository.deleteMessageById(id);
    }

    /**
     * Update a message with new message_text
     * 
     * @param messageText, the new message_text
     * @param id, the message_id of the message to be udated
     * @return the number of rows updated
     */
    public int updateMessageById(String messageText, int id)
    {
        if(messageRepository.getMessageById(id) != null)
        {
            if(!StringUtils.isNullOrEmpty(messageText))
            {
                if(!(messageText.isEmpty()) && messageText.length() < 255)
                {
                    Message message = messageRepository.getMessageById(id);
                    message.setMessage_text(messageText);
                    messageRepository.save(message);
                    return 1;
                }
            }
        }
        return 0;
    }

    /**
     * Retrieve all messages posted by a specific user
     * 
     * @param postedBy, the user_id of the user
     * @return the list of all messages posted by a particular user
     */
    public List<Message> getMessagesByUser(int postedBy)
    {
        return messageRepository.getMessagesByUser(postedBy);
    }
}
