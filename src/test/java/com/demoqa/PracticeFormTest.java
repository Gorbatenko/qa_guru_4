package com.demoqa;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class PracticeFormTest {
    @BeforeEach
    void setUp() {
        Configuration.browserSize = "1540x1080";
    }

    @Test
    void checkStudentRegistrationForm() {
        //arrange
        String firstName = "Anatolii";
        String lastName = "Wasserman";
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

        $("#firstName").val(firstName);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(userEmail);

        $(byText(gender)).click();
        $("#userNumber").setValue(userNumber);

        $("#dateOfBirthInput").click();
        $(".react-datepicker__year-select").click();
        $$(".react-datepicker__year-select option").find(value(yearOfBirth)).click();
        $(".react-datepicker__month-select").click();
        $$(".react-datepicker__month-select option").find(text(monthOfBirth)).click();
        $$(".react-datepicker__day").filter(not(cssClass(".react-datepicker__day--outside-month")))
                .find(text(dayOfBirth)).click();

        $("#subjectsContainer input").val(subjectOne).pressEnter();
        $("#subjectsContainer input").val(subjectTwo).pressEnter();

        $(byText("Sports")).click();
        $(byText("Reading")).click();
        $(byText("Music")).click();

        $("#uploadPicture").uploadFromClasspath(picture);

        $("#currentAddress").val(currentAddress);
        $("#state").click();
        $(byText(state)).click();
        $("#city").click();
        $(byText(city)).click();

        $("#submit").click();

        //assert
        ElementsCollection elements = $$(".table-responsive tr");
        elements.filterBy(text("Student Name")).shouldHave(texts(firstName + " " + lastName));
        elements.filterBy(text("Student Email")).shouldHave(texts(userEmail));
        elements.filterBy(text("Gender")).shouldHave(texts(gender));
        elements.filterBy(text("Mobile")).shouldHave(texts(userNumber));
        elements.filterBy(text("Date of Birth")).shouldHave(texts(dayOfBirth + " " + monthOfBirth + "," + yearOfBirth));
        elements.filterBy(text("Student Email")).shouldHave(texts(userEmail));
        elements.filterBy(text("Subjects")).shouldHave(texts(subjectOne), texts(subjectTwo));
        elements.filterBy(text("Hobbies")).shouldHave(texts("Sports"),texts("Reading"),texts("Music"));
        elements.filterBy(text("Picture")).shouldHave(texts(picture));
        elements.filterBy(text("Address")).shouldHave(texts(currentAddress));
        elements.filterBy(text("State and City")).shouldHave(texts(state + " " + city));
    }
}