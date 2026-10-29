package regionapp;

import regionapp.model.Region;
import regionapp.controller.RootLayoutController;
import regionapp.controller.RegionOverviewController;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<Region> regionData = FXCollections.observableArrayList();
    private File regionFilePath = null;

    public MainApp() {
        // Конструктор пустой — тестовые данные НЕ добавляются автоматически
        // Пользователь должен открыть файл вручную через меню
    }

    public ObservableList<Region> getRegionData() {
        return regionData;
    }

    /**
     * Возвращает путь к последнему открытому файлу из Preferences.
     */
    public File getRegionFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        }
        return null;
    }

    /**
     * Сохраняет путь к текущему файлу в Preferences и обновляет заголовок окна.
     */
    public void setRegionFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());
            primaryStage.setTitle("Реестр Областей - " + file.getName());
        } else {
            prefs.remove("filePath");
            primaryStage.setTitle("Реестр Областей");
        }
        this.regionFilePath = file;
    }

    /**
     * Загрузка данных из XML-файла (через встроенный DOM-парсер).
     */
    public void loadRegionDataFromFile(File file) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();

            regionData.clear();

            NodeList regionNodes = doc.getElementsByTagName("region");
            for (int i = 0; i < regionNodes.getLength(); i++) {
                Element regionElement = (Element) regionNodes.item(i);
                String name = regionElement.getElementsByTagName("name").item(0).getTextContent();
                String areaSize = regionElement.getElementsByTagName("areaSize").item(0).getTextContent();
                String adminCenter = regionElement.getElementsByTagName("adminCenter").item(0).getTextContent();
                String head = regionElement.getElementsByTagName("head").item(0).getTextContent();
                regionData.add(new Region(name, areaSize, adminCenter, head));
            }

            setRegionFilePath(file);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не удалось загрузить данные");
            alert.setContentText("Файл:\n" + file.getPath() + "\n\n" + e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Сохранение данных в XML-файл (через встроенный DOM-парсер).
     */
    public void saveRegionDataToFile(File file) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element rootElement = doc.createElement("regions");
            doc.appendChild(rootElement);

            for (Region region : regionData) {
                Element regionElement = doc.createElement("region");

                Element nameEl = doc.createElement("name");
                nameEl.appendChild(doc.createTextNode(region.getName()));
                regionElement.appendChild(nameEl);

                Element areaEl = doc.createElement("areaSize");
                areaEl.appendChild(doc.createTextNode(region.getAreaSize()));
                regionElement.appendChild(areaEl);

                Element adminEl = doc.createElement("adminCenter");
                adminEl.appendChild(doc.createTextNode(region.getAdminCenter()));
                regionElement.appendChild(adminEl);

                Element headEl = doc.createElement("head");
                headEl.appendChild(doc.createTextNode(region.getHead()));
                regionElement.appendChild(headEl);

                rootElement.appendChild(regionElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);

            setRegionFilePath(file);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не удалось сохранить данные");
            alert.setContentText("Файл:\n" + file.getPath() + "\n\n" + e.getMessage());
            alert.showAndWait();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Реестр Областей");

        // Очистка пути к последнему файлу при запуске
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        prefs.remove("filePath");

        initRootLayout();
        showRegionOverview();
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = loader.load();

            Scene scene = new Scene(rootLayout, 900, 600);
            scene.getStylesheets().add(getClass().getResource("view/DarkTheme.css").toExternalForm());

            primaryStage.setScene(scene);

            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showRegionOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RegionOverview.fxml"));
            AnchorPane regionOverview = loader.load();

            rootLayout.setCenter(regionOverview);

            RegionOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}