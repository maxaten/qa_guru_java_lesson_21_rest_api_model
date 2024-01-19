package tests.api;

import io.qameta.allure.*;
import models.LoginErrorModel;
import models.LoginRequestModel;
import models.LoginResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.junit.jupiter.api.Assertions.*;
import static specs.RequestSpec.requestSpec;
import static specs.ResponseSpec.responseSpec;


@Tag("api")
public class AuthorizationTests extends TestBase {
    @Test
    @DisplayName("Успешная регистрация пользователя")
    @Owner("Maksim A")
    @Epic("Авторизация")
    @Story("Авторизация пользователей по логину и паролю")
    @TmsLink("HOMEWORK-1039")
    @Severity(SeverityLevel.CRITICAL)
    @Tags({@Tag("regress"), @Tag("smoke")})
    public void registerSuccessfulTest200() {
        LoginRequestModel register = new LoginRequestModel();
        register.setEmail("eve.holt@reqres.in");
        register.setPassword("pistol");

        LoginResponseModel response = step("Ввод данных для регистрации", () ->
                given(requestSpec)
                        .body(register)
                        .when()
                        .post("/register")
                        .then()
                        .spec(responseSpec)
                        .statusCode(200)
                        .extract().as(LoginResponseModel.class));

        step("Проверка текста ошибки", () ->
                assertAll(
                        () -> assertEquals("QpwL5tke4Pnpja7X4", response.getToken()),
                        () -> assertEquals("4", response.getId())));
    }

    @Test
    @DisplayName("Неуспешная регистрация пользователя")
    @Owner("Maksim A")
    @Epic("Авторизация")
    @Story("Авторизация пользователей по логину и паролю")
    @TmsLink("HOMEWORK-1039")
    @Tags({@Tag("regress"), @Tag("smoke")})
    @Severity(SeverityLevel.CRITICAL)
    public void registerUnsuccessfulTest400() {
        LoginRequestModel request = new LoginRequestModel();
        request.setEmail("sydney@fife");

        LoginErrorModel response = step("Ввод данных для регистрации", () ->
                given(requestSpec)
                        .body(request)
                        .when()
                        .contentType(JSON)
                        .post("/register")
                        .then()
                        .spec(responseSpec)
                        .statusCode(400)
                        .extract().as(LoginErrorModel.class));

        step("Проверка текста ошибки", () ->
                assertEquals("Missing password", response.getError(), "Текст ошибки не совпадает"));
    }

    @Test
    @DisplayName("Успешная авторизация пользователя")
    @Owner("Maksim A")
    @Epic("Авторизация")
    @Story("Авторизация пользователей по логину и паролю")
    @TmsLink("HOMEWORK-1039")
    @Tags({@Tag("regress"), @Tag("smoke")})
    @Severity(SeverityLevel.CRITICAL)
    public void loginSuccessfulTest200() {

        LoginRequestModel authBody = new LoginRequestModel();
        authBody.setEmail("eve.holt@reqres.in");
        authBody.setPassword("cityslicka");

        LoginResponseModel response = step("Ввод данных для авторизации", () ->
                given(requestSpec)
                        .body(authBody)
                        .when()
                        .post("/login")
                        .then()
                        .spec(responseSpec)
                        .statusCode(200)
                        .extract().as(LoginResponseModel.class));

        step("Получение токена", () ->
                assertNotNull(response.getToken()));
    }


    @Test
    @DisplayName("Неуспешная авторизация пользователя")
    @Owner("Maksim A")
    @Epic("Авторизация")
    @Story("Авторизация пользователей по логину и паролю")
    @Feature("Изменение кнопок")
    @TmsLink("HOMEWORK-1039")
    @Tags({@Tag("regress"), @Tag("smoke")})
    @Severity(SeverityLevel.CRITICAL)
    public void loginUnsuccessfulTest400() {
        LoginRequestModel unAuthEmail = new LoginRequestModel();
        unAuthEmail.setEmail("peter@klaven");

        LoginErrorModel response = step("Ввод данных для авторизации", () ->
                given(requestSpec)
                        .body(unAuthEmail)
                        .when()
                        .post("/login")
                        .then()
                        .spec(responseSpec)
                        .statusCode(400)
                        .extract().as(LoginErrorModel.class));

        step("Проверка текста ошибки", () ->
                assertEquals("Missing password", response.getError(), "Текст ошибки не совпадает"));
    }
}
