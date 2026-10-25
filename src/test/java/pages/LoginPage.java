package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement loginInput = $("[name='login']");
    private final SelenideElement passwordInput = $("[name='password']");

    public DashboardPage login(String login, String password, String code) {
        loginInput.setValue(login);
        passwordInput.setValue(password);

        // Ищем кнопку "Войти" через type=button (как в твоем прошлом успешном тесте)
        $("button[type=button]").click();

        // Ждем появления поля для ввода кода
        $("[name='code']").shouldBe(visible, java.time.Duration.ofSeconds(5));
        $("[name='code']").setValue(code);

        // На странице подтверждения кнопка тоже обычно type=button
        $("button[type=button]").click();

        return new DashboardPage();
    }
}