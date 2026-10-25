package data;

public class DataHelper {
    private DataHelper() {}

    // Извлекает число из строки, например из "баланс: 9900 р." достанет 9900
    public static int extractBalance(String text) {
        String[] parts = text.split(":");
        String balancePart = parts[1].trim();
        String cleanNumber = balancePart.replaceAll("[^0-9]", "");
        return Integer.parseInt(cleanNumber);
    }
}