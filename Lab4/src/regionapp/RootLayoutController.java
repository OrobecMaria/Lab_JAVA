package regionapp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class RootLayoutController {
    private Stage primaryStage;

    public void setMainApp(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }

    @FXML
    private void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("О программе");
        alert.setHeaderText("Лабораторная работа 1.3");
        alert.setContentText("Приложение для управления областями и районами.\nJavaFX + Scene Builder");
        alert.showAndWait();
    }
}