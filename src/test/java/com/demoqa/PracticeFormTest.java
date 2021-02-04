package com.demoqa;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

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
        ElementsCollection elements = $$(".table-responsive td");
        elements.find(text("Student Name")).sibling(0).shouldHave(text(firstName + " " + lastName));
        elements.find(text("Student Email")).sibling(0).shouldHave(text(userEmail));
        elements.find(text("Gender")).sibling(0).shouldHave(text(gender));
        elements.find(text("Mobile")).sibling(0).shouldHave(text(userNumber));
        elements.find(text("Date of Birth")).sibling(0).shouldHave(text(dayOfBirth + " " + monthOfBirth + "," + yearOfBirth));
        elements.find(text("Student Email")).sibling(0).shouldHave(text(userEmail));
        elements.find(text("Subjects")).sibling(0).shouldHave(text(subjectOne), text(subjectTwo));
        elements.find(text("Hobbies")).sibling(0).shouldHave(text("Sports"), text("Reading"), text("Music"));
        elements.find(text("Picture")).sibling(0).shouldHave(text(picture));
        elements.find(text("Address")).sibling(0).shouldHave(text(currentAddress));
        elements.find(text("State and City")).sibling(0).shouldHave(text(state + " " + city));
    }
}