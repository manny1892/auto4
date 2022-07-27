import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    @BeforeEach
    void openBrowser() {
        open("http://localhost:9999/");
    }

    // FIRST
    @Test
    void shouldSendForm() {
        $("[data-test-id='city'] input").setValue("Пенза");
        $x("//input[@placeholder='Дата встречи']").setValue("30.07.2022");
        $x("//input[@name='name']").setValue("Поросенок Петр");
        $x("//input[@name='phone']").setValue("+79099876545");
        $("[data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x("//*[contains(text(),'Успешно!')]").shouldBe(visible, Duration.ofSeconds(15));

    }

    // SECOND
    @Test
    void shouldSendFormWithChoiceCity() {
        $("[data-test-id='city'] input").setValue("Пе");
        $$x("//span[@class='menu-item__control']").get(1).click();
        $x("//input[@placeholder='Дата встречи']").setValue("30.07.2022");
        $x("//input[@name='name']").setValue("Поросенок Петр");
        $x("//input[@name='phone']").setValue("+79099876545");
        $("[data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x("//*[contains(text(),'Успешно!')]").shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldSendWithChoiceDate() {
        $("[data-test-id='city'] input").setValue("Пенза");
        $x("//span[@class='input__icon']").click();
        $("[data-step='1']").click();
        $("[data-day='1659474000000']").click();
        $x("//input[@name='name']").setValue("Поросенок Петр");
        $x("//input[@name='phone']").setValue("+79099876545");
        $("[data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x("//*[contains(text(),'Успешно!')]").shouldBe(visible, Duration.ofSeconds(15));
    }

}
