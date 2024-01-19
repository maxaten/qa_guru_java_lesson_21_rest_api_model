package specs;

import io.restassured.specification.RequestSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;

public class RequestSpec {
    public static RequestSpecification requestSpec = with()
            .filters(withCustomTemplates())
            .log().uri()
            .log().method()
            .contentType(JSON);
}
