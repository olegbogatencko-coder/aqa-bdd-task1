package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class TransferPage {
    // Ищем поля ввода по тексту заголовков, которые идут перед ними
    private final SelenideElement amountInput = $x("//*[contains(text(), 'Сумма')]/following::input[1]");
    private final SelenideElement fromInput = $x("//*[contains(text(), 'Откуда')]/following::input[1]");

    public DashboardPage makeTransfer(String fromCardNumber, int amount) {
        amountInput.setValue(String.valueOf(amount));

        // Очищаем поле перед вводом
        fromInput.clear();
        fromInput.setValue(fromCardNumber);

        // Нажимаем на первую кнопку на странице (это точно "Пополнить", так как "Отмена" идет второй)
        $("button").click();

        return new DashboardPage();
    }
}