package regionapp;

import regionapp.model.Region;

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
        regionData.add(new Region("Московская область", "45 800 км²", "Москва", "Воробьёв А.Ю."));
        regionData.add(new Region("Ленинградская область", "83 900 км²", "Санкт-Петербург", "Дрозденко А.Ю."));
        regionData.add(new Region("Свердловская область", "194 300 км²", "Екатеринбург", "Куйвашев В.В."));
    }

    public ObservableList<Region> getRegionData() {
        return regionData;
    }

    public File getRegionFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        }
        return null;
    }

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
     * Загрузка данных из XML через DOM-парсер.
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
     * Сохранение данных в XML через DOM-парсер.
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

                Element nameElement = doc.createElement("name");
                nameElement.appendChild(doc.createTextNode(region.getName()));
                regionElement.appendChild(nameElement);

                Element areaElement = doc.createElement("areaSize");
                areaElement.appendChild(doc.createTextNode(region.getAreaSize()));
                regionElement.appendChild(areaElement);

                Element adminElement = doc.createElement("adminCenter");
                adminElement.appendChild(doc.createTextNode(region.getAdminCenter()));
                regionElement.appendChild(adminElement);

                Element headElement = doc.createElement("head");
                headElement.appendChild(doc.createTextNode(region.getHead()));
                regionElement.appendChild(headElement);

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
        initRootLayout();
        showRegionOverview();

        File file = getRegionFilePath();
        if (file != null && file.exists()) {
            loadRegionDataFromFile(file);
        }
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = loader.load();

            Scene scene = new Scene(rootLayout, 800, 500);
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
            controller.setMainApp(primaryStage);
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