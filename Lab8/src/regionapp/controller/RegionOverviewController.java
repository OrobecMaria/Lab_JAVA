package regionapp.controller;

import regionapp.model.Region;
import regionapp.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class RegionOverviewController {

    @FXML
    private TableView<Region> regionTable;
    @FXML
    private TableColumn<Region, String> nameColumn;
    @FXML
    private TableColumn<Region, String> areaSizeColumn;

    @FXML
    private Label nameLabel;
    @FXML
    private Label areaSizeLabel;
    @FXML
    private Label adminCenterLabel;
    @FXML
    private Label headLabel;

    private MainApp mainApp;

    public RegionOverviewController() {
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void initialize() {
        // Проверяем, что поля инициализированы (защита от ошибок)
        if (nameColumn == null || regionTable == null) {
            System.err.println("ОШИБКА: Поля FXML не инициализированы! Проверьте fx:id в FXML файле.");
            return;
        }

        // Настраиваем таблицу на данные из MainApp
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        areaSizeColumn.setCellValueFactory(new PropertyValueFactory<>("areaSize"));
        regionTable.setItems(mainApp.getRegionData());

        // Добавляем слушатель выделения
        regionTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showRegionDetails(newValue));
    }


    private void showRegionDetails(Region region) {
        if (region != null) {
            nameLabel.setText(region.getName());
            areaSizeLabel.setText(region.getAreaSize());
            adminCenterLabel.setText(region.getAdminCenter());
            headLabel.setText(region.getHead());
        } else {
            nameLabel.setText("");
            areaSizeLabel.setText("");
            adminCenterLabel.setText("");
            headLabel.setText("");
        }
    }

    @FXML
    private void handleNewRegion() {
        Region tempRegion = new Region();
        boolean okClicked = showRegionEditDialog(tempRegion);
        if (okClicked) {
            mainApp.getRegionData().add(tempRegion);
        }
    }

    @FXML
    private void handleEditRegion() {
        Region selectedRegion = regionTable.getSelectionModel().getSelectedItem();
        if (selectedRegion != null) {
            boolean okClicked = showRegionEditDialog(selectedRegion);
            if (okClicked) {
                showRegionDetails(selectedRegion);
            }
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Ничего не выбрано");
            alert.setHeaderText("Не выбрана область");
            alert.setContentText("Пожалуйста, выберите область в таблице.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleDeleteRegion() {
        int selectedIndex = regionTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            mainApp.getRegionData().remove(selectedIndex);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Ничего не выбрано");
            alert.setHeaderText("Не выбрана область");
            alert.setContentText("Пожалуйста, выберите область в таблице перед удалением.");
            alert.showAndWait();
        }
    }

    private boolean showRegionEditDialog(Region region) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(RegionOverviewController.class.getResource("../view/RegionEditDialog.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактирование области");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            RegionEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setRegion(region);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}