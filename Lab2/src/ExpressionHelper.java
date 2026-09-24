class ExpressionHelper {
    // Статический метод для ссылки ExpressionHelper::isLarge
    static boolean isLarge(Oblast o) {
        return o.area > 100;
    }

    // Нестатический метод для ссылки helper::startsWithA
    boolean startsWithA(Oblast o) {
        return o.head != null && o.head.startsWith("А");
    }
}