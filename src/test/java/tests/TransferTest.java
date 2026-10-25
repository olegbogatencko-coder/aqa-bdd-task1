package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.DashboardPage;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.codeborne.selenide.Selenide.open;

public class TransferTest {
    private DashboardPage dashboard;

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        LoginPage loginPage = new LoginPage();
        dashboard = loginPage.login("vasya", "qwerty123", "12345");
    }

    @Test
    @DisplayName("Should successfully transfer from first card to second card")
    void shouldTransferFromFirstToSecond() {
        String card1 = "5559 0000 0000 0001";
        String card2 = "5559 0000 0000 0002";

        // Получаем ТЕКУЩИЕ балансы (чтобы не сломаться, если тест запускают несколько раз)
        int balance1Start = dashboard.getCardBalance(card1);
        int balance2Start = dashboard.getCardBalance(card2);
        int transferAmount = 1000;

        // Выбираем карту №2 и переводим на неё с карты №1
        var transferPage = dashboard.clickTopUpButton(card2);
        dashboard = transferPage.makeTransfer(card1, transferAmount);

        // Проверяем новые балансы
        assertEquals(balance1Start - transferAmount, dashboard.getCardBalance(card1));
        assertEquals(balance2Start + transferAmount, dashboard.getCardBalance(card2));
    }

    @Test
    @DisplayName("Should successfully transfer from second card to first card")
    void shouldTransferFromSecondToFirst() {
        String card1 = "5559 0000 0000 0001";
        String card2 = "5559 0000 0000 0002";

        int balance1Start = dashboard.getCardBalance(card1);
        int balance2Start = dashboard.getCardBalance(card2);
        int transferAmount = 500;

        // Выбираем карту №1 и переводим на неё с карты №2
        var transferPage = dashboard.clickTopUpButton(card1);
        dashboard = transferPage.makeTransfer(card2, transferAmount);

        assertEquals(balance1Start + transferAmount, dashboard.getCardBalance(card1));
        assertEquals(balance2Start - transferAmount, dashboard.getCardBalance(card2));
    }
}