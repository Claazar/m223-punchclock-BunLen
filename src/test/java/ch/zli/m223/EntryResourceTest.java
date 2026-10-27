package ch.zli.m223;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class EntryResourceTest {

    @Test
    public void testEntryEndpoints() {
        given()
          .when().get("/entries")
          .then()
             .statusCode(200)
             .body(is("[]"));

        Long id = given()
          .contentType("application/json")
          .body("""
              {"checkIn":"2026-09-29T08:00:00","checkOut":"2026-09-29T17:00:00"}
              """)
          .when().post("/entries")
          .then()
             .statusCode(200)
             .body("checkIn", is("2026-09-29T08:00:00"))
             .body("checkOut", is("2026-09-29T17:00:00"))
             .extract().jsonPath().getLong("id");

        given()
          .when().get("/entries")
          .then()
             .statusCode(200)
             .body("size()", is(1))
             .body("[0].id", is(id.intValue()))
             .body("[0].checkIn", is("2026-09-29T08:00:00"))
             .body("[0].checkOut", is("2026-09-29T17:00:00"))
             .body("[0].categoryId", is((Object) null))
             .body("[0].tagIds", is(java.util.Collections.emptyList()));

        Long categoryId = given()
          .contentType("application/json")
          .body("""
              {"title":"Work"}
              """)
          .when().post("/categories")
          .then()
             .statusCode(200)
             .extract().jsonPath().getLong("id");

        Long tagId = given()
          .contentType("application/json")
          .body("""
              {"title":"Office"}
              """)
          .when().post("/tags")
          .then()
             .statusCode(200)
             .extract().jsonPath().getLong("id");

        Long relatedEntryId = given()
          .contentType("application/json")
          .body("""
              {
                "checkIn":"2026-09-30T08:00:00",
                "checkOut":"2026-09-30T17:00:00",
                "categoryId":%d,
                "tagIds":[%d]
              }
              """.formatted(categoryId, tagId))
          .when().post("/entries")
          .then()
             .statusCode(200)
             .body("categoryId", is(categoryId.intValue()))
             .body("tagIds", is(java.util.List.of(tagId.intValue())))
             .extract().jsonPath().getLong("id");

        given()
          .when().get("/entries")
          .then()
             .statusCode(200)
             .body("find { it.id == " + relatedEntryId + " }.categoryId", is(categoryId.intValue()))
             .body("find { it.id == " + relatedEntryId + " }.tagIds", is(java.util.List.of(tagId.intValue())));

        given()
          .contentType("application/json")
          .body("""
              {
                "checkIn":"2026-10-01T08:00:00",
                "checkOut":"2026-10-01T16:00:00",
                "categoryId":%d,
                "tagIds":[%d]
              }
              """.formatted(categoryId, tagId))
          .when().put("/entries/" + relatedEntryId)
          .then()
             .statusCode(200)
             .body("checkOut", is("2026-10-01T16:00:00"))
             .body("categoryId", is(categoryId.intValue()))
             .body("tagIds", is(java.util.List.of(tagId.intValue())));

        given()
          .when().get("/entry-details/" + relatedEntryId)
          .then()
             .statusCode(200)
             .body("category.id", is(categoryId.intValue()))
             .body("category.title", is("Work"))
             .body("tags.size()", is(1))
             .body("tags[0].id", is(tagId.intValue()))
             .body("tags[0].title", is("Office"));

        given()
          .when().get("/entry-details")
          .then()
             .statusCode(200)
             .body("size()", is(2));

        given()
          .when().get("/entry-details/" + id)
          .then()
             .statusCode(200)
             .body("category", is((Object) null))
             .body("tags", is(java.util.Collections.emptyList()));

        given()
          .when().get("/entry-details/" + Long.MAX_VALUE)
          .then()
             .statusCode(404);

        given()
          .contentType("application/json")
          .body("""
              {
                "checkIn":"2026-10-02T08:00:00",
                "checkOut":"2026-10-02T16:00:00",
                "categoryId":%d,
                "tagIds":[]
              }
              """.formatted(Long.MAX_VALUE))
          .when().post("/entries")
          .then()
             .statusCode(404);

        given()
          .contentType("application/json")
          .body("""
              {
                "checkIn":"2026-10-02T08:00:00",
                "checkOut":"2026-10-02T16:00:00",
                "categoryId":%d,
                "tagIds":[%d]
              }
              """.formatted(categoryId, Long.MAX_VALUE))
          .when().post("/entries")
          .then()
             .statusCode(404);
    }

}