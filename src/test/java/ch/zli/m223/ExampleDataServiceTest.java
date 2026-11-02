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

import ch.zli.m223.model.Entry;
import ch.zli.m223.repository.EntryRepository;

@QuarkusTest
@TestProfile(ExampleDataServiceTest.ExampleDataEnabledProfile.class)
class ExampleDataServiceTest {

    private static final String DEMO_EMAIL = "jdoe@quarkus.io";
    private static final String DEMO_PASSWORD = "demo-password";

    @Inject
    @ConfigProperty(name = "punchclock.example-data.enabled")
    boolean exampleDataEnabled;

    @Inject
    EntryRepository entryRepository;

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

    @Test
    void loginTokenProtectsResourceAndAssociatesEntryOwner() {
        String token = given()
          .contentType("application/json")
          .body("""
              {"email":"%s","password":"%s"}
              """.formatted(DEMO_EMAIL, DEMO_PASSWORD))
          .when().post("/auth/login")
          .then()
             .statusCode(200)
             .extract().asString();

        given()
          .contentType("application/json")
          .body("""
              {"email":"%s","password":"wrong-password"}
              """.formatted(DEMO_EMAIL))
          .when().post("/auth/login")
          .then()
             .statusCode(401);

        given()
          .header("Authorization", "Bearer " + token)
          .when().get("/secure/data")
          .then()
             .statusCode(200);

        Long entryId = given()
          .header("Authorization", "Bearer " + token)
          .contentType("application/json")
          .body("""
              {"checkIn":"2026-10-03T08:00:00","checkOut":"2026-10-03T16:00:00"}
              """)
          .when().post("/entries")
          .then()
             .statusCode(200)
             .extract().jsonPath().getLong("id");

        Entry entry = entryRepository.findById(entryId);
        org.junit.jupiter.api.Assertions.assertNotNull(entry);
        org.junit.jupiter.api.Assertions.assertEquals(DEMO_EMAIL, entry.getApplicationUser().getEmail());
    }

    public static class ExampleDataEnabledProfile implements QuarkusTestProfile {
        @Override
        public java.util.Map<String, String> getConfigOverrides() {
            return java.util.Map.of("punchclock.example-data.enabled", "true");
        }
    }

}
