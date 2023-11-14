package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import com.codeborne.selenide.logevents.SelenideLogger;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setUpEach() {

        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Successful plan and replan meeting")
    void SuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);

        $("[data-test-id='city'] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(firstMeetingDate);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='success-notification'] .notification__title")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Успешно!"));
        $("[data-test-id='success-notification'] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно запланирована на " + firstMeetingDate));
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(secondMeetingDate);
        $("button.button").click();
        $("[data-test-id='replan-notification'] .notification__title")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Необходимо подтверждение"));
        $("[data-test-id='replan-notification'] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactOwnText("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $("[data-test-id='replan-notification'] button").click();
        $("[data-test-id='success-notification'] .notification__title")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Успешно!"));
        $("[data-test-id='success-notification'] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно запланирована на " + secondMeetingDate));
    }
}