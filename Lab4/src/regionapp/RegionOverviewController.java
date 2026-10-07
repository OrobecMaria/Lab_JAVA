package regionapp;

import regionapp.model.Region;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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

    private ObservableList<Region> regionData = FXCollections.observableArrayList();

    public RegionOverviewController() {
        regionData.add(new Region("Московская область", "45 800 км²", "Москва", "Воробьёв А.Ю."));
        regionData.add(new Region("Ленинградская область", "83 900 км²", "Санкт-Петербург", "Дрозденко А.Ю."));
        regionData.add(new Region("Свердловская область", "194 300 км²", "Екатеринбург", "Куйвашев В.В."));
    }

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        areaSizeColumn.setCellValueFactory(new PropertyValueFactory<>("areaSize"));

        regionTable.setItems(regionData);

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
            regionData.add(tempRegion);
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
            regionTable.getItems().remove(selectedIndex);
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Ничего не выбрано");
            alert.setHeaderText("Не выбрана область");
            alert.setContentText("Пожалуйста, выберите область в таблице.");
            alert.showAndWait();
        }
    }

    private boolean showRegionEditDialog(Region region) {
        // Пока просто показываем информацию — полноценный диалог будет в следующей части
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Редактирование области");
        alert.setHeaderText("Функция редактирования");
        alert.setContentText("Здесь будет диалог редактирования области: " + region.getName());
        alert.showAndWait();
        return false;
    }
}