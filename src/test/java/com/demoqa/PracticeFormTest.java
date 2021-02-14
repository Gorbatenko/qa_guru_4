package com.demoqa;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static utils.Randomizer.randomDate;

public class PracticeFormTest {
    @BeforeEach
    void setUp() {
        Configuration.browserSize = "1540x1080";
    }

    private Faker faker = new Faker();
    private String firstName;
    private String lastName;
    private String userEmail;
    private String gender;
    private String userNumber;
    private String dayOfBirth;
    private String monthOfBirth;
    private String yearOfBirth;
    private String subjectOne;
    private String subjectTwo;
    private String picture;
    private String currentAddress;
    private String state;
    private String city;

    @Test
    void checkStudentRegistrationForm() {
        Calendar date = randomDate(-2208985139000L);
        firstName = faker.name().firstName();
        lastName = faker.name().lastName();
        userEmail = faker.internet().emailAddress();
        gender = "Male";
        userNumber = faker.phoneNumber().subscriberNumber(10);
        dayOfBirth = String.valueOf(date.get(Calendar.DAY_OF_MONTH));
        monthOfBirth = String.valueOf(new DateFormatSymbols(Locale.ENGLISH).getMonths()[date.get(Calendar.MONTH)-1]);
        yearOfBirth = String.valueOf(date.get(Calendar.YEAR));
        subjectOne = "Physics";
        subjectTwo = "History";
        picture = "photo.jpg";
        currentAddress = faker.address().streetAddress();
        state = "Rajasthan";
        city = "Jaipur";

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
        elements.filterBy(text("Hobbies")).shouldHave(texts("Sports"), texts("Reading"), texts("Music"));
        elements.filterBy(text("Picture")).shouldHave(texts(picture));
        elements.filterBy(text("Address")).shouldHave(texts(currentAddress));
        elements.filterBy(text("State and City")).shouldHave(texts(state + " " + city));
    }

}