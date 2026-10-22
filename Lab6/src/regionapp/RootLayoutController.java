package regionapp;

import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;

public class RootLayoutController {
    private MainApp mainApp;

    /**
     * Вызывается главным приложением для передачи ссылки на себя.
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Создает пустой реестр (очищает данные).
     */
    @FXML
    private void handleNew() {
        mainApp.getRegionData().clear();
        mainApp.setRegionFilePath(null);
    }

    /**
     * Открывает FileChooser для выбора файла XML.
     */
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Открыть ресурс");

        // Фильтр для XML файлов
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Показываем диалог
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.loadRegionDataFromFile(file);
        }
    }

    /**
     * Сохраняет файл. Если файл еще не выбран, открывает "Сохранить как".
     */
    @FXML
    private void handleSave() {
        File regionFile = mainApp.getRegionFilePath();
        if (regionFile != null) {
            mainApp.saveRegionDataToFile(regionFile);
        } else {
            handleSaveAs();
        }
    }

    /**
     * Открывает FileChooser для выбора места сохранения.
     */
    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить ресурс");

        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Проверяем расширение
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.saveRegionDataToFile(file);
        }
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }

    @FXML
    private void handleAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("О программе");
        alert.setHeaderText("Лабораторная работа 1.7");
        alert.setContentText("Приложение для управления областями.\nJavaFX + JAXB + XML");
        alert.showAndWait();
    }
}