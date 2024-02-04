package com.example.PicrossProject;

public class BinaryOutOfBounds extends RuntimeException {
    /**
     * gives the exception BinaryOutOfBounds and gives a message
     * @param message the message that will be displayed
     */
    public BinaryOutOfBounds (String message){
        super (message);
    }
}
