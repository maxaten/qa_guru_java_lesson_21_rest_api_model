package tests.api;

import models.createUser.CreateSuccessfulUserRequestModel;
import models.createUser.CreateSuccessfulUserResponseModel;
import models.loginUser.LoginSuccessfulRequestBodyModel;
import models.loginUser.LoginSuccessfulResponseModel;
import models.loginUser.LoginUnsuccessfulRequestModel;
import models.loginUser.LoginUnsuccessfulResponseModel;
import models.registerUser.RegisterSuccessfulUserRequestModel;
import models.registerUser.RegisterSuccessfulUserResponseModel;
import models.registerUser.RegisterUnsuccessfulUserRequestModel;
import models.registerUser.RegisterUnsuccessfulUserResponseModel;
import models.singleUser.SingleUserModel;
import models.userList.UserListResponseModel;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.CreateUserSpec.createRequestSpec;
import static specs.CreateUserSpec.createResponseSpec;
import static specs.ListUsersSpec.listUsersRequestSpec;
import static specs.ListUsersSpec.listUsersResponseSpec;
import static specs.LoginUserSpec.*;
import static specs.RegisterUserSpec.*;

public class ReqresIn extends TestBase {


    @Test
    @Disabled
    public void singleUserTest200() {

        SingleUserModel user = new SingleUserModel();
        given()
                .log().uri()
                .log().method()
                .when()
                .get("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.first_name", is("Janet"));
        assertEquals("Janet", user.getData(user.getFirst_name()));
    }


    @Test
    public void registerSuccessfulTest200() {
        RegisterSuccessfulUserRequestModel register = new RegisterSuccessfulUserRequestModel();
        register.setEmail("eve.holt@reqres.in");
        register.setPassword("pistol");

        RegisterSuccessfulUserResponseModel response = step("Ввод данных для регистрации", () ->
                given(registerRequestSpec)
                        .body(register)
                        .when()
                        .post("/register")
                        .then()
                        .spec(registerResponseSpec)
                        .extract().as(RegisterSuccessfulUserResponseModel.class));

        step("", () ->
                assertAll("Получение токена и присвоение ID",
                        () ->     assertEquals("QpwL5tke4Pnpja7X4", response.getToken()),
                        () ->    assertEquals("4", response.getId())));
    }

    @Test
    public void registerUnsuccessfulTest400() {
        RegisterUnsuccessfulUserRequestModel request = new RegisterUnsuccessfulUserRequestModel();
        request.setEmail("sydney@fife");

        RegisterUnsuccessfulUserResponseModel response = step("", () ->
                given(registerRequestSpec)
                        .body(request)
                        .when()
                        .contentType(JSON)
                        .post("/register")
                        .then()
                        .spec(unsuccessfulRegisterResponseSpec)
                        .extract().as(RegisterUnsuccessfulUserResponseModel.class));

        step("Проверка текста ошибки", () ->
                assertEquals("Missing password", response.getError(), "Текст ошибки не совпадает"));
    }



    @Test
    public void loginSuccessfulTest200() {

        LoginSuccessfulRequestBodyModel authBody = new LoginSuccessfulRequestBodyModel();
        authBody.setEmail("eve.holt@reqres.in");
        authBody.setPassword("cityslicka");

        LoginSuccessfulResponseModel response = step("Ввод данных для авторизации", () ->
                given(loginRequestSpec)
                        .body(authBody)
                        .when()
                        .post("/login")
                        .then()
                        .spec(loginResponseSpec)
                        .extract().as(LoginSuccessfulResponseModel.class));

        step("Получение токена", () ->
                assertEquals("QpwL5tke4Pnpja7X4", response.getToken()));
    }


    @Test
    public void loginUnsuccessfulTest400() {
        LoginUnsuccessfulRequestModel unAuthEmail = new LoginUnsuccessfulRequestModel();
        unAuthEmail.setEmail("peter@klaven");

        LoginUnsuccessfulResponseModel response = step("", () ->
                given(loginRequestSpec)
                        .body(unAuthEmail)
                        .when()
                        .post("/login")
                        .then()
                        .spec(unsuccessfulLoginResponseSpec)
                        .extract().as(LoginUnsuccessfulResponseModel.class));

        step("", () ->
                assertEquals("Missing password", response.getError(), "Текст ошибки не совпадает"));
    }


    @Test
    public void createUserTest201() {
        CreateSuccessfulUserRequestModel requestBody = new CreateSuccessfulUserRequestModel();

        requestBody.setName("morpheus");
        requestBody.setJob("leader");

        CreateSuccessfulUserResponseModel responseBody = step("", ()->
                given(createRequestSpec)
                        .body(requestBody)
                        .when()
                        .post("/users")
                        .then()
                        .spec(createResponseSpec)
                        .extract().as(CreateSuccessfulUserResponseModel.class));

        step("Проверка совпадения имени и должности", () ->
                assertAll(
                        () -> assertEquals("morpheus", responseBody.getName(), "Имя не совпадает"),
                        () -> assertEquals("leader", responseBody.getJob(), "Должность не совпадает")));
    }


    @Test
    public void listUserTest200() {
        UserListResponseModel response = step("Запрос списка пользователей", () ->
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
                    () ->   assertEquals(6, response.getPerPage()),
                    () ->   assertEquals(12, response.getTotal()),
                    () ->   assertEquals(2, response.getTotalPages())
            ));


        step("Проверка данных о первом первого пользователя", () -> {
            List<UserListResponseModel.DataList> data = response.getData();
            assertAll(
                    () ->   assertEquals(7, data.get(0).getId()),
                    () ->  assertEquals("michael.lawson@reqres.in", data.get(0).getEmail()),
                    () ->  assertEquals("Michael", data.get(0).getFirstName()),
                    () ->   assertEquals("Lawson", data.get(0).getLastName()),
                    () ->    assertEquals("https://reqres.in/img/faces/7-image.jpg", data.get(0).getAvatar())
            );
        });
    }
}