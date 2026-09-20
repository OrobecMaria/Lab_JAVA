public class Lab1 {
    public static void main(String[] args) {
        Lab1 myApp = new Lab1();
        myApp.showUnits();
    }

    public void showUnits() {
        AdministrativeUnit moscowRegion = new MoscowRegion();

        AdministrativeUnit odintsovoDistrict = new AdministrativeUnit() {
            String name = "Одинцовский район";

            public void printInfo() {
                printInfoAbout("Одинцовский район");
            }

            public void printInfoAbout(String someone) {
                name = someone;
                System.out.println("Район: " + name);
            }
        };

        AdministrativeUnit pushkinDistrict = new AdministrativeUnit() {
            String name = "Пушкинский район";

            public void printInfo() {
                printInfoAbout("Пушкинский район");
            }

            public void printInfoAbout(String someone) {
                name = someone;
                System.out.println("Район: " + name);
            }
        };

        moscowRegion.printInfo();
        odintsovoDistrict.printInfoAbout("Красногорский район");
        pushkinDistrict.printInfo();
    }
}