package ch.zli.m223;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import io.quarkus.test.junit.QuarkusTestProfile;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import jakarta.inject.Inject;

@QuarkusTest
@TestProfile(ExampleDataServiceTest.ExampleDataEnabledProfile.class)
class ExampleDataServiceTest {

    @Inject
    @ConfigProperty(name = "punchclock.example-data.enabled")
    boolean exampleDataEnabled;

    @Test
    void loadsConsistentExampleDataAtStartup() {
        org.junit.jupiter.api.Assertions.assertTrue(exampleDataEnabled);

        given()
          .when().get("/categories")
          .then()
             .statusCode(200)
             .body("size()", is(3));

        given()
          .when().get("/tags")
          .then()
             .statusCode(200)
             .body("size()", is(5));

        given()
          .when().get("/entry-details")
          .then()
             .statusCode(200)
             .body("size()", is(6))
             .body("category.id", everyItem(notNullValue()))
             .body("category.title", everyItem(notNullValue()))
             .body("tags.size()", everyItem(greaterThan(0)));
    }

    public static class ExampleDataEnabledProfile implements QuarkusTestProfile {
        @Override
        public java.util.Map<String, String> getConfigOverrides() {
            return java.util.Map.of("punchclock.example-data.enabled", "true");
        }
    }

}
