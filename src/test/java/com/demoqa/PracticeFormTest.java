package com.demoqa;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PracticeFormTest {
    @BeforeEach
    void setUp() {
        Configuration.browserSize = "1540x1080";
    }

    @Test
    void checkStudentRegistrationForm() {
        //arrange
        String firstName = "Anatolij";
        String lastName = "Vasserman";
        String userEmail = "Vasserman@anatolij.gov";
        String gender = "Male";
        String userNumber = "0661350321";
        String dayOfBirth = "19";
        String monthOfBirth = "December";
        String yearOfBirth = "1952";
        String subjectOne = "Physics";
        String subjectTwo = "History";
        String picture = "photo.jpg";
        String currentAddress = "New Zealand, Shire";
        String state = "Rajasthan";
        String city = "Jaipur";

        //act
        open("https://demoqa.com/automation-practice-form");

        $("#firstName").shouldBe(Condition.visible).val(firstName);
        $("#lastName").shouldBe(Condition.visible).setValue(lastName);
        $("#userEmail").shouldBe(Condition.visible).setValue(userEmail);

        $(byText(gender)).shouldBe(Condition.visible).click();
        $("#userNumber").shouldBe(Condition.visible).setValue(userNumber);

        $("#dateOfBirthInput").shouldBe(Condition.visible).click();
        $(".react-datepicker__year-select").shouldBe(Condition.visible).click();
        $x("//option[contains(text(),'" + yearOfBirth + "')]").scrollTo().click();
        $(".react-datepicker__month-select").shouldBe(Condition.visible).click();
        $x("//option[contains(text(),'" + monthOfBirth + "')]").shouldBe(Condition.visible).click();
        $x("//*[@class='react-datepicker__month']/descendant::" +
                "div[text()='" + dayOfBirth +
                "' and not(contains(@class,'react-datepicker__day--outside-month'))]").click();

        $x("//*[@id='subjectsContainer']/descendant::input").val(subjectOne).pressEnter();
        $x("//*[@id='subjectsContainer']/descendant::input").val(subjectTwo).pressEnter();

        $(byText("Sports")).shouldBe(Condition.visible).click();
        $(byText("Reading")).shouldBe(Condition.visible).click();
        $(byText("Music")).shouldBe(Condition.visible).click();

        File file = new File("src/test/resources/pictures/" + picture);
        $("#uploadPicture").uploadFile(file);

        $("#currentAddress").shouldBe(Condition.visible).val(currentAddress);
        $("#state").shouldBe(Condition.visible).click();
        $(byText(state)).shouldBe(Condition.visible).click();
        $("#city").shouldBe(Condition.visible).click();
        $(byText(city)).shouldBe(Condition.visible).click();

        $("#submit").click();

        //assert
        $(byText("Thanks for submitting the form")).shouldBe(Condition.visible);
        assertEquals(firstName + " " + lastName, getActualResult("Student Name"));
        assertEquals(userEmail, getActualResult("Student Email"));
        assertEquals(gender, getActualResult("Gender"));
        assertEquals(userNumber, getActualResult("Mobile"));
        assertEquals(dayOfBirth + " " + monthOfBirth + "," + yearOfBirth,
                getActualResult("Date of Birth"));
        assertTrue(getActualResult("Subjects").contains(subjectOne) &&
                getActualResult("Subjects").contains(subjectTwo), "Subjects not contains all positions.");
        assertTrue(getActualResult("Hobbies").contains("Sports, Reading, Music"), "Not all hobbies.");
        assertEquals(picture, getActualResult("Picture"));
        assertEquals(currentAddress, getActualResult("Address"));
        assertEquals(state + " " + city, getActualResult("State and City"));
    }

    private String getActualResult(String label) {
        return $x("//td[text()='" + label + "']/following-sibling::td").getText();
    }
}
