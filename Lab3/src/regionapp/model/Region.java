package regionapp.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Region {
    private final StringProperty name;
    private final StringProperty areaSize;
    private final StringProperty adminCenter;
    private final StringProperty head;

    public Region() {
        this(null, null, null, null);
    }

    public Region(String name, String areaSize, String adminCenter, String head) {
        this.name = new SimpleStringProperty(name);
        this.areaSize = new SimpleStringProperty(areaSize);
        this.adminCenter = new SimpleStringProperty(adminCenter);
        this.head = new SimpleStringProperty(head);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getAreaSize() {
        return areaSize.get();
    }

    public void setAreaSize(String areaSize) {
        this.areaSize.set(areaSize);
    }

    public StringProperty areaSizeProperty() {
        return areaSize;
    }

    public String getAdminCenter() {
        return adminCenter.get();
    }

    public void setAdminCenter(String adminCenter) {
        this.adminCenter.set(adminCenter);
    }

    public StringProperty adminCenterProperty() {
        return adminCenter;
    }

    public String getHead() {
        return head.get();
    }

    public void setHead(String head) {
        this.head.set(head);
    }

    public StringProperty headProperty() {
        return head;
    }
}