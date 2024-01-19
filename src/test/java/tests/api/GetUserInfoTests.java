package tests.api;

import io.qameta.allure.*;
import models.SingleUserResponseModel;
import models.UserListResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.RequestSpec.requestSpec;
import static specs.ResponseSpec.responseSpec;

@Tag("api")
public class GetUserInfoTests extends TestBase {
    @Test
    @DisplayName("Получение информации по одному пользователю")
    @Owner("Maksim A")
    @Epic("Информация о пользователях")
    @Story("Один пользователь")
    @Feature("Получение информации из нескольких источников")
    @TmsLink("HOMEWORK-1039")
    @Tags({@Tag("regress"), @Tag("smoke")})
    @Severity(SeverityLevel.NORMAL)
    public void singleUserTest200() {

        SingleUserResponseModel user = step("Запрос данных по пользователю", () ->

                given(requestSpec)
                        .when()
                        .get("/users/2")
                        .then()
                        .spec(responseSpec)
                        .statusCode(200)
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
    @DisplayName("Получения списка пользователей")
    @Owner("Maksim A")
    @Epic("Информация о пользователях")
    @Story("Много пользователей")
    @Feature("Получение информации из нескольких источников")
    @TmsLink("HOMEWORK-1039")
    @Tags({@Tag("regress"), @Tag("smoke")})
    @Severity(SeverityLevel.NORMAL)
    public void listUserTest200() {
        UserListResponseModel response = step("Ввод данных для запроса списка пользователей", () ->
                given(requestSpec)
                        .when()
                        .get("users?page=2")
                        .then()
                        .spec(responseSpec)
                        .statusCode(200)
                        .extract().as(UserListResponseModel.class));

        step("Проверка данных ответа", () ->
                assertAll(
                        () -> assertEquals(2, response.getPage()),
                        () -> assertEquals(6, response.getPerPage()),
                        () -> assertEquals(12, response.getTotal()),
                        () -> assertEquals(2, response.getTotalPages())
                ));


        step("Проверка данных первого пользователя", () -> {
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
