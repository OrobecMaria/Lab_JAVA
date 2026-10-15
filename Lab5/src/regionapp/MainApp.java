package regionapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Реестр Областей и Районов");

        initRootLayout();
        showRegionOverview();
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            RootLayoutController controller = loader.getController();
            controller.setMainApp(primaryStage);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showRegionOverview() {
        try {
            System.out.println("DEBUG: Начинаю загрузку RegionOverview.fxml...");

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RegionOverview.fxml"));

            if (loader.getLocation() == null) {
                throw new RuntimeException("FXML файл не найден! Проверьте путь: view/RegionOverview.fxml");
            }

            AnchorPane regionOverview = loader.load();
            System.out.println("DEBUG: FXML загружен успешно.");

            RegionOverviewController controller = loader.getController();

            System.out.println("DEBUG: Контроллер получен: " + controller);

            if (controller == null) {
                throw new RuntimeException("Контроллер равен NULL! Проверьте fx:controller в FXML и отсутствие ошибок в конструкторе/инициализации.");
            }

            controller.setMainApp(primaryStage);
            rootLayout.setCenter(regionOverview);
            System.out.println("DEBUG: Интерфейс отображен.");

        } catch (Exception e) {
            System.err.println("КРИТИЧЕСКАЯ ОШИБКА ПРИ ЗАГРУЗКЕ ИНТЕРФЕЙСА:");
            e.printStackTrace(); // Это покажет точную причину, а не просто NullPointerException
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}