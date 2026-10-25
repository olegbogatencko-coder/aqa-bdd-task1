package pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Condition.partialText;

public class DashboardPage {
    private final SelenideElement refreshButton = $("[data-test-id='action-reload']");

    public DashboardPage() {
        $("[data-test-id='dashboard']").shouldBe(visible);
    }

    private SelenideElement getCardBlock(String cardNumber) {
        // Берем последние 4 цифры номера карты, так как на странице номер замаскирован (**** **** **** 0001)
        String last4Digits = cardNumber.substring(cardNumber.length() - 4);
        return $$("[data-test-id]").findBy(partialText(last4Digits));
    }

    public int getCardBalance(String cardNumber) {
        String cardText = getCardBlock(cardNumber).getText();
        // Берем часть строки после слова "баланс:" и оставляем только цифры
        String balanceStr = cardText.split("баланс:")[1].replaceAll("\\D", "");
        return Integer.parseInt(balanceStr);
    }

    public TransferPage clickTopUpButton(String cardNumber) {
        getCardBlock(cardNumber).$("[data-test-id='action-deposit']").click();
        return new TransferPage();
    }

    public void refresh() {
        refreshButton.click();
    }
}