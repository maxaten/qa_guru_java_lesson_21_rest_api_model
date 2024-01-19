package tests.api;

import models.CreateUserRequestModel;
import models.CreateUserResponseModel;
import models.LoginRequestModel;
import models.LoginResponseModel;
import models.LoginErrorModel;
import models.SingleUserResponseModel;
import models.UserListResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.CreateUserSpec.createRequestSpec;
import static specs.CreateUserSpec.createResponseSpec;
import static specs.ListUsersSpec.listUsersRequestSpec;
import static specs.ListUsersSpec.listUsersResponseSpec;
import static specs.LoginUserSpec.*;
import static specs.RegisterUserSpec.*;
import static specs.SingleUserSpec.singleRequestSpec;
import static specs.SingleUserSpec.singleResponseSpec;

@Tag("api")
public class ReqresIn extends TestBase {


    @Test
    @DisplayName("Получение информации по одному пользователю")
    public void singleUserTest200() {

        SingleUserResponseModel user = step("Запрос данных по пользователю", () ->

        given(singleRequestSpec)
                .when()
                .get("/users/2")
                .then()
                .spec(singleResponseSpec)
                .extract().as(SingleUserResponseModel.class));

        step("Проверка информации пользователя", () ->
                assertAll(
                        () -> assertEquals(2, user.getData().getId(), "Не совпадает ID"),
                        () -> assertEquals("janet.weaver@reqres.in", user.getData().getEmail(), "Не совпадает почта"),
                        () -> assertEquals("Janet", user.getData().getFirstName(), "Не совпадает имя"),
                        () -> assertEquals("Weaver", user.getData().getLastName(), "Не совпадает фамилия"),
                        () -> assertEquals("https://reqres.in/img/faces/2-image.jpg", user.getData().getAvatar(), "Не совпадает изображение")));
    }


    @Test
    @DisplayName("Успешная регистрация пользователя")
    public void registerSuccessfulTest200() {
        LoginRequestModel register = new LoginRequestModel();
        register.setEmail("eve.holt@reqres.in");
        register.setPassword("pistol");

        LoginResponseModel response = step("Ввод данных для регистрации", () ->
                given(registerRequestSpec)
                        .body(register)
                        .when()
                        .post("/register")
                        .then()
                        .spec(registerResponseSpec)
                        .extract().as(LoginResponseModel.class));

        step("Проверка текста ошибки", () ->
                assertAll(
                        () -> assertEquals("QpwL5tke4Pnpja7X4", response.getToken()),
                        () -> assertEquals("4", response.getId())));
    }

    @Test
    @DisplayName("Неуспешная регистрация пользователя")
    public void registerUnsuccessfulTest400() {
        LoginRequestModel request = new LoginRequestModel();
        request.setEmail("sydney@fife");

        LoginErrorModel response = step("Ввод данных для регистрации", () ->
                given(registerRequestSpec)
                        .body(request)
                        .when()
                        .contentType(JSON)
                        .post("/register")
                        .then()
                        .spec(unsuccessfulRegisterResponseSpec)
                        .extract().as(LoginErrorModel.class));

        step("Проверка текста ошибки", () ->
                assertEquals("Missing password", response.getError(), "Текст ошибки не совпадает"));
    }



    @Test
    @DisplayName("Успешная авторизация пользователя")
    public void loginSuccessfulTest200() {

        LoginRequestModel authBody = new LoginRequestModel();
        authBody.setEmail("eve.holt@reqres.in");
        authBody.setPassword("cityslicka");

        LoginResponseModel response = step("Ввод данных для авторизации", () ->
                given(loginRequestSpec)
                        .body(authBody)
                        .when()
                        .post("/login")
                        .then()
                        .spec(loginResponseSpec)
                        .extract().as(LoginResponseModel.class));

        step("Получение токена", () ->
                assertEquals("QpwL5tke4Pnpja7X4", response.getToken()));
    }


    @Test
    @DisplayName("Неуспешная авторизация пользователя")
    public void loginUnsuccessfulTest400() {
        LoginRequestModel unAuthEmail = new LoginRequestModel();
        unAuthEmail.setEmail("peter@klaven");

        LoginErrorModel response = step("Ввод данных для авторизации", () ->
                given(loginRequestSpec)
                        .body(unAuthEmail)
                        .when()
                        .post("/login")
                        .then()
                        .spec(unsuccessfulLoginResponseSpec)
                        .extract().as(LoginErrorModel.class));

        step("Проверка текста ошибки", () ->
                assertEquals("Missing password", response.getError(), "Текст ошибки не совпадает"));
    }


    @Test
    @DisplayName("Успешное создание пользователя")
    public void createUserTest201() {
        CreateUserRequestModel requestBody = new CreateUserRequestModel();

        requestBody.setName("morpheus");
        requestBody.setJob("leader");

        CreateUserResponseModel responseBody = step("Ввод данных для создания пользователя", ()->
                given(createRequestSpec)
                        .body(requestBody)
                        .when()
                        .post("/users")
                        .then()
                        .spec(createResponseSpec)
                        .extract().as(CreateUserResponseModel.class));

        step("Проверка совпадения имени и должности", () ->
                assertAll(
                        () -> assertEquals("morpheus", responseBody.getName(), "Имя не совпадает"),
                        () -> assertEquals("leader", responseBody.getJob(), "Должность не совпадает")));
    }


    @Test
    @DisplayName("Получения списка пользователей")
    public void listUserTest200() {
        UserListResponseModel response = step("Ввод данных для запроса списка пользователей", () ->
                given(listUsersRequestSpec)
                        .when()
                        .get("users?page=2")
                        .then()
                        .spec(listUsersResponseSpec)
                        .statusCode(200)
                        .extract().as(UserListResponseModel.class));

        step("Проверка данных ответа", () ->
            assertAll(
                    () -> assertEquals(2, response.getPage()),
                    () -> assertEquals(6, response.getPerPage()),
                    () -> assertEquals(12, response.getTotal()),
                    () -> assertEquals(2, response.getTotalPages())
            ));


        step("Проверка данных о первом первого пользователя", () -> {
            List<UserListResponseModel.DataList> data = response.getData();
            assertAll(
                    () -> assertEquals(8, data.get(1).getId(), "Не совпадает ID"),
                    () -> assertEquals("lindsay.ferguson@reqres.in", data.get(1).getEmail(), "Не совпадает почта"),
                    () -> assertEquals("Lindsay", data.get(1).getFirstName(), "Не совпадает имя"),
                    () -> assertEquals("Ferguson", data.get(1).getLastName(), "Не совпадает фамилия"),
                    () -> assertEquals("https://reqres.in/img/faces/8-image.jpg", data.get(1).getAvatar(), "Не совпадает изображение")
            );
        });
    }
}