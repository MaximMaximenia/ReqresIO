
import io.restassured.response.Response;
import org.apache.http.protocol.HTTP;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ReqresTest {

    String url = "https://reqres.in";

    @Test
    public void listUserApiTest() {

        given()
                .when()
                .get(url + "/api/users?page=2")
                .then()
                .log()
                .body()
                .statusCode(200)
                .body("data[1].id", equalTo(8));

    }

    @Test
    public void singleUserTest() {

        given()
                .when()
                .get(url + "/api/users/2")
                .then()
                .log().body()
                .statusCode(200)
                .body("data.email", equalTo("janet.weaver@reqres.in"));

    }

    @Test
    public void singleUserNotFoundTest() {

        given()
                .when()
                .get(url + "/api/users/23")
                .then()
                .log().body()
                .statusCode(404);

    }

    @Test
    public void listResourcesTest() {

        given()
                .when()
                .get(url + "/api/unknown")
                .then()
                .log().body()
                .statusCode(200)
                .body("data[1].id", equalTo(2));

    }

    @Test
    public void singleResourcesTest() {

        given()
                .when()
                .get(url + "/api/unknown/2")
                .then()
                .log().body()
                .statusCode(200)
                .body("data.id", equalTo(2));

    }

    @Test
    public void singleResourcesNotFoundTest() {

        given()
                .when()
                .get(url + "/api/unknown/23")
                .then()
                .log().body()
                .statusCode(404);

    }

    @Test
    public void createTest() {

        given()
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"leader\"\n" +
                        "}")
                .when()
                .post(url + "/api/users")
                .then()
                .log().body()
                .statusCode(201);

    }

    @Test
    public void updateTest() {

        given()
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"zion resident\"\n" +
                        "}")
                .when()
                .put(url + "/api/users/2")
                .then()
                .log().body()
                .statusCode(200);

    }
    @Test
    public void updatePatchTest() {

        given()
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"zion resident\"\n" +
                        "}")
                .when()
                .patch(url + "/api/users/2")
                .then()
                .log().body()
                .statusCode(200);
    }
    @Test
    public void deleteTest() {

        given()
                .when()
                .delete(url + "/api/users/2")
                .then()
                .log().body()
                .statusCode(204);

    }
    @Test
    public void registerSuccessfulTest() {

        given()
                .body("{\n" +
                        "    \"email\": \"eve.holt@reqres.in\",\n" +
                        "    \"password\": \"pistol\"\n" +
                        "}")
                .when()
                .post(url + "/api/register")
                .then()
                .log().body()
                .statusCode(200)
        .body("id",equalTo(4))
        .body("token",equalTo("QpwL5tke4Pnpja7X4"));

    }
    @Test
    public void registerUnSuccessfulTest() {

        given()
                .body("{\n" +
                        "    \"email\": \"sydney@fife\"\n" +
                        "}")
                .when()
                .post(url + "/api/register")
                .then()
                .log().body()
                .statusCode(400)
        .body("error",equalTo("Missing password"));

    }

    @Test
    public void loginSuccessfulTest() {

        given()
                .body("{\n" +
                        "    \"email\": \"eve.holt@reqres.in\",\n" +
                        "    \"password\": \"cityslicka\"\n" +
                        "}")
                .when()
                .post(url + "/api/login")
                .then()
                .log().body()
                .statusCode(200)
        .body("token",equalTo("QpwL5tke4Pnpja7X4"));

    }

    @Test
    public void loginUnSuccessfulTest() {

        given()
                .body("{\n" +
                        "    \"email\": \"test@test\"\n" +
                        "}")
                .when()
                .post(url + "/api/login")
                .then()
                .log().body()
                .statusCode(400)
        .body("error",equalTo("Missing password"));

    }
    @Test
    public void delayedResponseTest() {

        given()
                .when()
                .get(url + "/api/users?delay=3")
                .then()
                .log().body()
                .statusCode(200)
        .body("data[2].id",equalTo(3));

    }
}
