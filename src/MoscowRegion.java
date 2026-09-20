public class MoscowRegion implements AdministrativeUnit {
    String name = "Московская область";

    public void printInfo() {
        printInfoAbout("Московская область");
    }

    public void printInfoAbout(String someone) {
        name = someone;
        System.out.println("Область: " + name);
    }
}
