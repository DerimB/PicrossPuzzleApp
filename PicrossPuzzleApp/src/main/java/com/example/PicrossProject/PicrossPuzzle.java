package com.example.PicrossProject;

import java.util.Arrays;
import java.util.Random;

/**
 * Class holds an array of 0s and 1s representing a Picross puzzle
 */
public class PicrossPuzzle {
    private int[][] solution;
    private int[][] entered;
    /**
     * Constructor for PicrossPuzzle with a default size of 5x5
     */
    public PicrossPuzzle()
    {
        this(5);
    }

    /**
     * Constructor for PicrossPuzzle with arguments, fills array with random 0s and 1s
     * @param size the size of the 2-D array
     */
    public PicrossPuzzle(int size) {
        Random rand = new Random();
        solution = new int[size][size];
        entered = new int[size][size];

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                solution[i][j] = rand.nextInt(2);
                entered[i][j] = 0;
            }
    }


    /**
     * Constructor for PicrossPuzzle to initialize solution with the array passed
     * @param solution solution array
     */
    public PicrossPuzzle(int[][] solution) {
        this.solution = Arrays.copyOf(solution, solution.length);
        entered = new int[solution.length][solution.length];
    }

    /**
     * Returns the string representation of the data array
     * @return the string representation of the data array
     */
    public String toString()
    {
        String result = "";
        for (int i = 0; i < solution.length; i++) {
            for (int j = 0; j < solution[i].length; j++) {
                result += solution[i][j] + "\t";
            }
            result += "\n";
        }
        return result;
    }

    /**
     * Gets the value at a specified row and column
     * @param indrow the row index
     * @param indcol the column index
     * @return the array's value at the specified row and column
     */
    public int getValue(int indrow, int indcol)
    {
        return solution[indrow][indcol];
    }

    /**
     * Returns the row clues of the puzzle solution
     * @return the row clues of the puzzle solution
     */
    public String[] getRowClues() {
        int rows = solution.length;
        String[] clues = new String[rows];
        getRowClues(clues, 0);
        return clues;
    }

    /**
     * Recursive helper method to get one row's clue at a time
     * @param clues the array holding the clues
     * @param row the row number being considered
     * @return the array holding the clues
     */
    private String[] getRowClues(String [] clues, int row)
    {
        int cols = solution[0].length;
        int count;
        int j;
        if (row == clues.length)
            return clues;
        else
        {
            clues[row] = "";
            j = 0;
            count = 0;
            while (j < cols) {
                while (j < cols && solution[row][j] == 0)
                    j++;
                while (j < cols && solution[row][j] == 1) {
                    j++;
                    count++;
                }
                clues[row] += (count>0) ? count+" " : "";
                count = 0;
            }
            return getRowClues(clues, row+1);
        }
    }

    /**
     * Returns the column clues of the puzzle solution
     * @return the column clues of the puzzle solution
     */
    public String[] getColumnClues() {
        int cols = solution.length;
        String[] clues = new String[cols];
        getColumnClues(clues, 0);
        return clues;
    }

    /**
     * Recursive helper method to get one column's clue at a time
     * @param clues the array holding the clues
     * @param col the row number being considered
     * @return the array holding the clues
     */
    private String[] getColumnClues(String [] clues, int col)
    {
        int rows = solution[0].length;
        int count;
        int i;
        if (col == clues.length)
            return clues;
        else
        {
            clues[col] = "";
            i = 0;
            count = 0;
            while (i < rows)
            {
                while (i < rows && solution[i][col] == 0)
                    i++;
                while (i < rows && solution[i][col] == 1)
                {
                    i++;
                    count++;
                }
                clues[col] += (count > 0) ? count + "\n" : "";  //   \n adding endlines in between to make label appear vertical
                count = 0;
            }
        }
        return getColumnClues(clues, col+1);
    }

    /**
     * Turns the cell on and off, making it black or white.
     * @param i this represents the row.
     * @param j this represents the column
     */
    public void toggleCell(int i, int j) {
        i = i-1;
        j = j-1;

        if (entered[i][j] == 0)
            entered[i][j] = 1;
        else
            entered[i][j] = 0;

        for(int a = 0; a<entered.length; a++){
            for (int b = 0; b<entered[a].length; b++){
                System.out.println(entered[a][b]);
            }
        }
    }

    /**
     * Checks if the puzzles are solved by counting how many cells are the same between the entered array and solution array,
     * and checking if that is the full amount of cells.
     * @return returns a true or false, false if checker is less than the total number of cells in the solutions array.
     */
    public boolean puzzleSolved() {
        int checker = 0;

        for (int i = 0; i < solution.length; i++) {
            for (int j = 0; j < solution[i].length; j++) {
                if (solution[i][j] == entered[i][j])
                    checker ++;
            }
        }
        if (checker == solution.length*solution.length)
            return true;
        else
            return false;
    }

    /**
     * Returns array entered[][]
     * @return returns array entered[][]
     */
    public int[][] getEntered() {
        return entered;
    }

    /**
     * Sets the entered array back to all cells being 0's.
     */
    public void resetEntered(){
        entered = new int[5][5];
        for (int i = 0; i < solution.length; i++)
            for (int j = 0; j < solution[i].length; j++) {
                entered[i][j] = 0;
            }
    }
}
