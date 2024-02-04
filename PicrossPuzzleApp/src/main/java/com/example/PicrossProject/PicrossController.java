package com.example.PicrossProject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

/**
 * Controller between the GUI and rest of code
 */
public class PicrossController implements Initializable {
    PicrossPuzzlePool pool1 = new PicrossPuzzlePool(Paths.get(".\\Data"));
    PicrossPuzzle puzzle1 = pool1.getRandomPuzzle();

    Button buttonChild;

    @FXML
    private Label label_2, label_3, label_4, label_5, label_6, label_a, label_b, label_c, label_d, label_e, nameofFile;

    @FXML
    private GridPane grid;

    final String ON_COLOR = "-fx-background-color: black";
    final String OFF_COLOR = "-fx-background-color: white";

    @FXML
    /**
     * Selects a puzzle and shows the solution, also prints the title
     */

    //Sets the puzzle to a random puzzle and wipes the answers put in, if there are any.
    public void setPuzzle() {
        //gets a different puzzle
        puzzle1 = pool1.getRandomPuzzle();

        //prints file name in gui
        String currentfile = pool1.getFile();
        nameofFile.setText(currentfile.substring(0, currentfile.length() - 4));

        String[] rowClues = puzzle1.getRowClues();
        String[] colClues = puzzle1.getColumnClues();
        System.out.println();
        System.out.println(puzzle1);
        restartPuzzle();

        //Prints the puzzle's solution to standard output for debugging purposes
        System.out.println("ROW CLUES: ");
        for (int i = 0; i < rowClues.length; i++)
            System.out.print("\"" + rowClues[i] + "\" ");
        System.out.println("\nCOLUMN CLUES: ");
        for (int i = 0; i < colClues.length; i++)
            System.out.print("\"" + colClues[i] + "\" ");
        System.out.println();

        //Place column clues on GUI
        label_a.setText(colClues[0] + " ");
        label_b.setText(colClues[1] + " ");
        label_c.setText(colClues[2] + " ");
        label_d.setText(colClues[3] + " ");
        label_e.setText(colClues[4] + " ");

        //Place row clues on GUI
        label_2.setText(rowClues[0] + " ");
        label_3.setText(rowClues[1] + " ");
        label_4.setText(rowClues[2] + " ");
        label_5.setText(rowClues[3] + " ");
        label_6.setText(rowClues[4] + " ");

        puzzle1.resetEntered();
        Button b;
        EventHandler<ActionEvent> event;

        System.out.println(grid.getChildren().size());

        for (int col = 1; col < 6; col++)
            for (int row = 1; row < 6; row++) {
                int i = col * 6 + row;
                System.out.println(grid.getChildren().get(i));
                b = (Button) (grid.getChildren().get(i));

                //blank out each button's text field
                b.setText("");

                //instantiate the action event
                event = new CellEventHandler(b);

                //associate event with the button being clicked
                b.setOnAction(event);
            }
    }

    //Clears the answers which the user has put in.
    public void restartPuzzle() {
        puzzle1.resetEntered();
        Button b;
        EventHandler<ActionEvent> event;

        for (int col = 1; col < 6; col++)
            for (int row = 1; row < 6; row++) {
                int i = col * 6 + row;
                System.out.println(grid.getChildren().get(i));
                b = (Button) (grid.getChildren().get(i));

                //blank out each button's text field
                b.setText("");

                //instantiate the action event
                event = new CellEventHandler(b);

                //associate event with the button being clicked
                b.setOnAction(event);
                setButton(b, 0);
            }
    }



    /**
     * Quits/stops the program
     */
    public void quit(){
        System.exit(0);
    }

    /**
     * Changes the background color of a button (i.e., pixel on the puzzle)
     * @param b the button to change
     * @param value 0 == OFF, 1 == ON
     */
    public void setButton (Button b, int value) {
        b.setStyle(value == 0 ? OFF_COLOR : ON_COLOR);
    }

    @Override
    /**
     * Acts as the start point for the Controller and shows a puzzle's solution
     */
    public void initialize (URL url, ResourceBundle rb) {
        //showSolution();
        setPuzzle();
        EventHandler<ActionEvent> event;
        System.out.println(grid.getChildren().size());

        //gridpane is 7x6, row1 and col1 hold labels, so start at index 1 (skip labels)

    }

    /**
     * Event handler for each button on the grid pane
     */
    private class CellEventHandler implements EventHandler<ActionEvent> {
        private final Button b;
        public boolean checkOn = false;

        /**
         * Constructs an object and associates the button to the event handler
         * @param b
         */
        public CellEventHandler(Button b) {
            this.b = b;
        }

        /**
         * Identifies which row and column of the clicked button
         * @param event
         */
        public void handle(ActionEvent event)
        {
            //find out which row ad col this button is at the gridpane
            int row = (grid.getRowIndex(b) == null) ? 0 : grid.getRowIndex(b);
            int col = (grid.getColumnIndex(b) == null) ? 0 : grid.getColumnIndex(b);

            System.out.println("You clicked row " + row + " col " + col);
            puzzle1.toggleCell(row,col);

            //makes a button which emulates the button clicked
            int i = row*6 + col;
            buttonChild = (Button) (grid.getChildren().get(i));

            //uses the emulated button checks if it's turned on (toggled on), and works accordingly
            if (checkOn == true) {
                setButton(buttonChild, 0);
                checkOn=false;
            }
            else {
                setButton(buttonChild, 1);
                checkOn = true;
            }

            //Check if the puzzle is completed
            if(puzzle1.puzzleSolved()){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Great Job! Puzzle Solved!");
                alert.setContentText("You have solved the puzzle, great job!");
                alert.show();
            }
        }
    }
}
