package pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.visible;

public class TransferPage {
    private final SelenideElement amountField = $("[data-test-id='amount'] input");
    private final SelenideElement fromField = $("[data-test-id='from'] input");
    private final SelenideElement transferButton = $("[data-test-id='action-transfer']");

    public TransferPage() {
        amountField.shouldBe(visible);
    }

    public DashboardPage makeTransfer(String fromCard, int amount) {
        // Очистка поля "Откуда"
        fromField.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        fromField.setValue(fromCard);

        // Очистка поля "Сумма"
        amountField.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        amountField.setValue(String.valueOf(amount));

        // Нажимаем "Перевести"
        transferButton.click();

        // Возвращаемся на главную страницу
        return new DashboardPage();
    }
}