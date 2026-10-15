package regionapp;

import regionapp.model.Region;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegionEditDialogController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField areaSizeField;
    @FXML
    private TextField adminCenterField;
    @FXML
    private TextField headField;

    private Stage dialogStage;
    private Region region;
    private boolean okClicked = false;

    /**
     * Инициализирует контроллер. Вызывается автоматически после загрузки FXML.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Устанавливает этап (окно) диалога.
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Устанавливает область для редактирования.
     * @param region
     */
    public void setRegion(Region region) {
        this.region = region;

        if (region != null) {
            nameField.setText(region.getName());
            areaSizeField.setText(region.getAreaSize());
            adminCenterField.setText(region.getAdminCenter());
            headField.setText(region.getHead());
        }
    }

    /**
     * Возвращает true, если пользователь нажал OK, false иначе.
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Вызывается, когда пользователь нажимает OK.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            region.setName(nameField.getText());
            region.setAreaSize(areaSizeField.getText());
            region.setAdminCenter(adminCenterField.getText());
            region.setHead(headField.getText());

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Вызывается, когда пользователь нажимает Отмена.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Проверяет корректность введенных данных.
     * @return true, если данные корректны
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (nameField.getText() == null || nameField.getText().length() == 0) {
            errorMessage += "Не указано название!\n";
        }
        if (areaSizeField.getText() == null || areaSizeField.getText().length() == 0) {
            errorMessage += "Не указана площадь!\n";
        }
        if (adminCenterField.getText() == null || adminCenterField.getText().length() == 0) {
            errorMessage += "Не указан административный центр!\n";
        }
        if (headField.getText() == null || headField.getText().length() == 0) {
            errorMessage += "Не указан глава!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Показываем сообщение об ошибке
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage); // Привязываем к окну диалога, а не к главному окну
            alert.setTitle("Некорректные поля");
            alert.setHeaderText("Пожалуйста, исправьте следующие ошибки:");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
}