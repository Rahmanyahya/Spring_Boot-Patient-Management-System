import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.path.json.JsonPath.given;

public class AuthIntegrationTest {
    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "http://localhost:4000";
    }

    @Test
    public  void shouldReturnOkWithValidToken() {
        String loginPayload = """
                    {
                        "email": "testuser@test.com",
                        "password": "password123"
                    }
                """;

        Response response = given()
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(200)
                .body("data.token", notNullValue())
                .extract().response();

        System.out.println("Generated Token : " + response.jsonPath().getString("data.token"));
    }
}
