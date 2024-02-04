module com.example.PicrossPuzzleProj {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.example.PicrossProject to javafx.fxml;
    exports com.example.PicrossProject;
}