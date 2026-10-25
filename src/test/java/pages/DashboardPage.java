package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    // Ищем все элементы, содержащие текст с балансом (обычно это div или span)
    private final ElementsCollection cards = $$("div").filterBy(text("баланс:"));

    // Ищем все кнопки "Пополнить"
    private final ElementsCollection topUpButtons = $$("button").filterBy(text("Пополнить"));

    public int getCardBalance(String cardNumber) {
        String cleanNumber = cardNumber.replaceAll("\\s", "");
        String last4Digits = cleanNumber.substring(cleanNumber.length() - 4);

        // Находим карту по последним 4 цифрам и берем её текст
        SelenideElement card = cards.findBy(text(last4Digits));
        String cardText = card.text();
        return DataHelper.extractBalance(cardText);
    }

    public TransferPage clickTopUpButton(String cardNumber) {
        String cleanNumber = cardNumber.replaceAll("\\s", "");
        String last4Digits = cleanNumber.substring(cleanNumber.length() - 4);

        // В этом приложении кнопки идут в том же порядке, что и карты.
        // Если карта заканчивается на 0001 — жмем первую кнопку, если на 0002 — вторую.
        if (last4Digits.equals("0001")) {
            topUpButtons.get(0).click();
        } else {
            topUpButtons.get(1).click();
        }

        return new TransferPage();
    }
}