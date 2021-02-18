package com.demoqa;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static utils.Randomizer.*;

public class PracticeFormTest {
    private final Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        Configuration.browserSize = "1540x1080";
    }

    @Test
    void checkStudentRegistrationForm() {
        Calendar date = randomDate(-2208985139000L);
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String userEmail = faker.internet().emailAddress();
        String gender = randomValueFromVariant("Male", "Female", "Other");
        String userNumber = faker.phoneNumber().subscriberNumber(10);
        String dayOfBirth = String.valueOf(date.get(Calendar.DAY_OF_MONTH));
        String monthOfBirth = String.valueOf(new DateFormatSymbols(Locale.ENGLISH).getMonths()[date.get(Calendar.MONTH)-1]);
        String yearOfBirth = String.valueOf(date.get(Calendar.YEAR));
        List<String> subjects = randomValuesFromVariant("Physics", "History", "Economics") ;
        List<String> hobbies = randomValuesFromVariant("Sports", "Reading", "Music") ;
        String picture = "photo.jpg";
        String currentAddress = faker.address().streetAddress();
        String state = "Rajasthan";
        String city = randomValueFromVariant("Jaipur", "Jaiselmer");

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

        setSubjects(subjects);
        setHobbies(hobbies);

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
        elements.filterBy(text("Picture")).shouldHave(texts(picture));
        elements.filterBy(text("Address")).shouldHave(texts(currentAddress));
        elements.filterBy(text("State and City")).shouldHave(texts(state + " " + city));
        checkByList("Subjects", subjects, elements);
        checkByList("Hobbies", hobbies, elements);
    }

    private void setSubjects(List<String> subjects) {
        for (String subject : subjects) {
            $("#subjectsContainer input").val(subject).pressEnter();
        }
    }

    private void setHobbies(List<String> hobbies) {
        for (String hobby : hobbies) {
            $(byText(hobby)).click();
        }
    }

    private void checkByList(String category, List<String> subjects, ElementsCollection elements) {
        for (String subject : subjects) {
            elements.filterBy(text(category)).shouldHave(texts(subject));
        }
    }

}