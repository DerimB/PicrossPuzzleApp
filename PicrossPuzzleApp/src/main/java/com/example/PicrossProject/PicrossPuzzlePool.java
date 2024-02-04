package com.example.PicrossProject;


import javafx.scene.control.Alert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

/**
 * Maintains a pool of data read from files in a folder
 */
public class PicrossPuzzlePool {

    //for PuzzlePool, add instance data which is an array of PicrossPuzzle objects
    private ArrayList<PicrossPuzzle> picrossPuzzlePool = new ArrayList<PicrossPuzzle>();
    private ArrayList<String> readfiles = new ArrayList<String>();
    private File[] files;
    private String currentFile;
    /**
     * Loads all the files in the given path
     * @param path the path that contains files to be read
     */
    public PicrossPuzzlePool (Path path)
    {
        File aFile;
        int fileCount = 0;
        int[][] array2D;

        //dump all the file contents of the path into the local File array
        //all files and folders in the Data folder are collected
        aFile = new File(path.toString());
        files = aFile.listFiles();

        //iterate over each file in files
        for (File file : files)
        {
            //check to make sure file name ends with .txt, skips files like desktop.ini
            String fileName = file.toString();
            int dotIndex = fileName.lastIndexOf ('.');
            if(dotIndex > 0) { //filename has an extension
                if (fileName.substring(dotIndex + 1).equals("txt")) {
                    System.out.println("File #: " + fileCount + "\t" + file);
                    try {
                        array2D = readFile(file);
                        PicrossPuzzle puzzle1 = new PicrossPuzzle(array2D);
                        picrossPuzzlePool.add(puzzle1);
                        readfiles.add(file.getName());
                    } catch (BinaryOutOfBounds b) {
                        //confirmation alert
                        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                        a.setContentText("FILE: " + file + " has non-binary numbers");
                        a.show();
                    }catch (ArrayOutOfBounds c) {
                        //confirmation alert
                        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                        a.setContentText("FILE: " + file + " Array either too big or small");
                        a.show();
                    }
                    fileCount++;
                }
            }
        }
    }

    /**
     * Reads one file and store the contents into a 5x5 int array
     * File content is expected to be integers
     * @param file the file to be read
     * @return the 5x5 int array read from the file
     */
    private int[][] readFile(File file)
    {
        int [][] puzzle = new int [5][5] ;

        try {
            Scanner readFrom = new Scanner(file);
            for (int i = 0; i < 5; i++)
                for (int j = 0; j < 5; j++)
                {
                    int currentint = readFrom.nextInt();
                    //read the 25 numbers from the file and store them into the 2D int array, data
                    if (currentint < 0 || currentint > 1) {
                        // throws Binary exception
                        throw new BinaryOutOfBounds("File must be Binary");
                    }
                    else {
                        //data array is now filled, this may be used to instantiate a PicrossPuzzle object, but changed to 5x5
                        puzzle[i][j] = currentint;
                    }
                }
            if (readFrom.hasNext())
                // throws array exception
                throw new ArrayOutOfBounds("File must be 5 by 5 array");

            readFrom.close();
        }
        catch (IOException ex) {
            System.out.println(ex);
        }
        return puzzle;
    }

    /**
     * Randomly picks a file from the data pool
     * @return returns the randomly selected file
     */
    public PicrossPuzzle getRandomPuzzle() {
        Random r = new Random();
        int rr = r.nextInt(picrossPuzzlePool.size());
        currentFile = readfiles.get(rr);
        return picrossPuzzlePool.get(rr);
    }

    /**
     * Gets the file name
     * @return returns the current file / name of file
     */
    public String getFile() {
        return currentFile;
    }


}
