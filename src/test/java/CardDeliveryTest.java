import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {


    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @BeforeEach
    void openBrowser() {
        open("http://localhost:9999/");
    }

    // FIRST
    @Test
    void shouldSendForm() {
        String planningDate = generateDate(5);

        $("[data-test-id='city'] input").setValue("Пенза");
        $x("//input[@placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@placeholder='Дата встречи']").setValue(planningDate);
        $x("//input[@name='name']").setValue("Поросенок Фон-Петр");
        $x("//input[@name='phone']").setValue("+79099876545");
        $("[data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x("//*[contains(text(),'Успешно!')]").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }

    // SECOND
    @Test
    void shouldSendFormWithChoiceCityAndDate() {

        String validDate = generateDate(7);
        String calendarDate = String.valueOf(LocalDate.now().plusDays(7).getDayOfMonth());
        String planningDate = String.valueOf(LocalDate.now().plusDays(7).getMonth());
        String deliveryDate = String.valueOf(LocalDate.now().plusDays(3).getMonth());

        $("[data-test-id='city'] input").setValue("Пе");
        $$x("//span[@class='menu-item__control']").get(1).click();
        $x("//span[@class='input__icon']").click();

        if (!Objects.equals(planningDate, deliveryDate)) {
            $("[data-step='1']").click();
        }

        $$("table.calendar__layout td").find(text(calendarDate)).click();
        $x("//input[@name='name']").setValue("Поросенок Фон-Петр");
        $x("//input[@name='phone']").setValue("+79099876545");
        $("[data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x("//*[contains(text(),'Успешно!')]").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + validDate), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }

}
