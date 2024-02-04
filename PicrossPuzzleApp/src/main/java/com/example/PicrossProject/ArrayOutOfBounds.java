package com.example.PicrossProject;

public class ArrayOutOfBounds extends RuntimeException
{
    /**
     * gives the exception ArrayOutOfBounds and gives a message
     * @param message the message that will be displayed
     */
    public ArrayOutOfBounds (String message) {
        super(message);
    }
}
