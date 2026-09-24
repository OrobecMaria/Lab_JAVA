// 1. Функциональные интерфейсы
interface Operationable<T> { T calculate(T x, T y); }
interface Printable { void print(String s); }
interface Expression { boolean isEqual(Oblast o); }
interface OblastBuilder { Oblast create(String name, double area, String center, String head); }
interface Operation { double execute(double x, double y); }
interface SimpleOperation {
    double execute();
}

// 2. Классы предметной области
class Oblast {
    String name; double area; String adminCenter; String head;
    Oblast(String name, double area, String center, String head) {
        this.name = name; this.area = area; this.adminCenter = center; this.head = head;
    }
    public String toString() { return name + " (пл. " + area + ", центр: " + adminCenter + ")"; }
}

class Rayon {
    String name; double area; String adminCenter; String head;
    Rayon(String name, double area, String center, String head) {
        this.name = name; this.area = area; this.adminCenter = center; this.head = head;
    }
    public String toString() { return "Район: " + name + ", пл. " + area; }
}

// 3. Главный класс
public class LambdaApp {
    static double totalArea = 0; // Переменная уровня класса

    public static void main(String[] args) {
        Oblast[] oblasts = {
                new Oblast("Московская", 44.3, "Москва", "А. Воробьёв"),
                new Oblast("Свердловская", 194.3, "Екатеринбург", "Д. Паслер"),
                new Oblast("Тюменская", 160.1, "Тюмень", "А. Моор")
        };

        // 1. Простое лямбда-выражение
        Operationable<Double> op1 = (x, y) -> x + y;
        System.out.println("1. Сумма: " + op1.calculate(10.0, 20.0));

        // 2. Множество лямбд для одного интерфейса
        Operationable<Double> opAdd = (x, y) -> x + y;
        Operationable<Double> opSub = (x, y) -> x - y;
        System.out.println("2. Сложение: " + opAdd.calculate(20.0, 10.0) + ", Вычитание: " + opSub.calculate(20.0, 10.0));

        // 3. Терминальная лямбда (void)
        Printable printer = s -> System.out.println("3. Терминальная: " + s);
        printer.print("Hello!");

        // 4. Локальные переменные
        int n = 70; // effectively final

        SimpleOperation opLocal = () -> {totalArea = 30.5; return totalArea + n; };

        System.out.println("4. Результат: " + opLocal.execute() + ", totalArea стала: " + totalArea);
        // 5. Блоки кода в лямбдах
        Operationable<Double> opBlock = (x, y) -> { if (y == 0) return 0.0; else return x / y; };
        System.out.println("5. Деление 20/0: " + opBlock.calculate(20.0, 0.0));

        // 6. Обобщенный интерфейс
        Operationable<Integer> intOp = (x, y) -> x + y;
        Operationable<String> strOp = (x, y) -> x + y;
        System.out.println("6. Целые: " + intOp.calculate(5, 5) + ", Строки: " + strOp.calculate("Площадь: ", "100"));

        // 7. Лямбда как параметр метода
        Expression func = (o) -> o.area > 50;
        System.out.println("7. Сумма площадей > 50: " + sumAreas(oblasts, func));

        // 8. Ссылка на метод как параметр
        System.out.println("8. Сумма площадей (ссылка на стат. метод): " + sumAreas(oblasts, ExpressionHelper::isLarge));
        ExpressionHelper helper = new ExpressionHelper();
        System.out.println("   Сумма площадей (ссылка на нестат. метод): " + sumAreas(oblasts, helper::startsWithA));

        // 9. Ссылка на конструктор
        OblastBuilder builder = Oblast::new;
        Oblast newOblast = builder.create("Новая", 99.9, "Центр", "Глава");
        System.out.println("9. Создана через конструктор: " + newOblast);

        // 10. Лямбда как результат метода
        Operation funcResult = getOperation(1);
        System.out.println("10. Результат метода (умножение): " + funcResult.execute(6.0, 5.0));

        // 11. Отложенное выполнение
        Expression deferredFilter = (o) -> o.area > 100;
        System.out.println("11. Отложенное выполнение (большие области):");
        for (Oblast o : oblasts) {
            if (deferredFilter.isEqual(o)) System.out.println("   - " + o.name);
        }

        // использование Района
        Rayon r = new Rayon("Центральный", 15.5, "пгт Центральный", "И. Иванов");
        System.out.println("\nПример района: " + r);
    }

    // Вспомогательные методы
    private static double sumAreas(Oblast[] arr, Expression func) {
        double sum = 0;
        for (Oblast o : arr) if (func.isEqual(o)) sum += o.area;
        return sum;
    }

    private static Operation getOperation(int type) {
        if (type == 1) return (x, y) -> x * y;
        return (x, y) -> 0;
    }
}