package tests;

import data.DataHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.DashboardPage;
import pages.LoginPage;
import pages.VerificationPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.codeborne.selenide.Selenide.open;

public class TransferTest {
    private DashboardPage dashboard;

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        LoginPage loginPage = new LoginPage();
        VerificationPage verificationPage = loginPage.validLogin(DataHelper.getAuthInfo());
        dashboard = verificationPage.validVerify(DataHelper.getVerificationCode());
    }

    @Test
    @DisplayName("Should successfully transfer from first card to second card")
    void shouldTransferFromFirstToSecond() {
        String card1 = DataHelper.getFirstCard();
        String card2 = DataHelper.getSecondCard();

        int balance1Start = dashboard.getCardBalance(card1);
        int balance2Start = dashboard.getCardBalance(card2);
        int transferAmount = 1000;

        // Пополняем ВТОРУЮ карту с ПЕРВОЙ
        dashboard.clickTopUpButton(card2)
                .makeTransfer(card1, transferAmount);

        dashboard.refresh(); // Обязательно обновляем, чтобы подтянуть новые балансы

        assertEquals(balance1Start - transferAmount, dashboard.getCardBalance(card1), "Баланс первой карты уменьшился");
        assertEquals(balance2Start + transferAmount, dashboard.getCardBalance(card2), "Баланс второй карты увеличился");
    }

    @Test
    @DisplayName("Should successfully transfer from second card to first card")
    void shouldTransferFromSecondToFirst() {
        String card1 = DataHelper.getFirstCard();
        String card2 = DataHelper.getSecondCard();

        int balance1Start = dashboard.getCardBalance(card1);
        int balance2Start = dashboard.getCardBalance(card2);
        int transferAmount = 500;

        // Пополняем ПЕРВУЮ карту со ВТОРОЙ
        dashboard.clickTopUpButton(card1)
                .makeTransfer(card2, transferAmount);

        dashboard.refresh();

        assertEquals(balance1Start + transferAmount, dashboard.getCardBalance(card1), "Баланс первой карты увеличился");
        assertEquals(balance2Start - transferAmount, dashboard.getCardBalance(card2), "Баланс второй карты уменьшился");
    }
}