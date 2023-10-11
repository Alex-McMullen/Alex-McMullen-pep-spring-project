package com.example.exception;

public class BadRequestException extends RuntimeException
{
    public BadRequestException()
    {
        super("Something was wrong with your request, please try again");
    }
}