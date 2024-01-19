package tests.api;

import io.qameta.allure.*;
import models.CreateUserRequestModel;
import models.CreateUserResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.RequestSpec.requestSpec;
import static specs.ResponseSpec.responseSpec;

@Tag("api")
public class CreateUserTests extends TestBase {

    @Test
    @DisplayName("Успешное создание пользователя")
    @Owner("Maksim A")
    @Epic("Регистрация")
    @Story("Регистрация новых пользовательей")
    @Feature("Регистрация по логину")
    @TmsLink("HOMEWORK-1043")
    @Tags({@Tag("regress"), @Tag("smoke")})
    @Severity(SeverityLevel.CRITICAL)
    public void createUserTest201() {
        CreateUserRequestModel requestBody = new CreateUserRequestModel();

        requestBody.setName("morpheus");
        requestBody.setJob("leader");

        CreateUserResponseModel responseBody = step("Ввод данных для создания пользователя", () ->
                given(requestSpec)
                        .body(requestBody)
                        .when()
                        .post("/users")
                        .then()
                        .spec(responseSpec)
                        .statusCode(201)
                        .extract().as(CreateUserResponseModel.class));

        step("Проверка совпадения имени и должности", () ->
                assertAll(
                        () -> assertEquals("morpheus", responseBody.getName(), "Имя не совпадает"),
                        () -> assertEquals("leader", responseBody.getJob(), "Должность не совпадает")));
    }
}
