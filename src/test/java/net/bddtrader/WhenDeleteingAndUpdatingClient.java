package net.bddtrader;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.bddtrader.clients.Client;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class WhenDeleteingAndUpdatingClient {
    @Before
    public void setUpBaseUrl(){
        baseURI = "http://localhost:9000/api";
    }
    @Test
    public void should_be_able_to_delete_a_client(){
        //Given a client exists
        Client existingClient = Client.withFirstName("Pam").andLastName("Beasley").andEmail("pambs@test.com");
       String id = aClientExists(existingClient);

        //When I delete a client
        //DELETE http://localhost:9000/api/client/1234
        given().pathParam("id",id).delete("/client/{id}");

        //Then the client should no longer exist
        given().pathParam("id",id)
                .get("/client/{id}")
                .then()
                .statusCode(404);
    }
    @Test
    public void shouldBeAbleToUpdateTheClient(){
        Client pam = Client.withFirstName("Pam").andLastName("Beasley").andEmail("pambs@test.com");
        //GIVEN client exists
        String id = aClientExists(pam);
        //WHEN i update the client

        Client pamWithUpdates = Client.withFirstName("Pam").andLastName("Beasley").andEmail("pambs@alpha.com");
        RestAssured.given().contentType(ContentType.JSON)
                .and().body(pamWithUpdates)
                .when().put("/client/{id}",id)
                .then().statusCode(200);
        RestAssured.when().get("/client/{id}",id)
                .then().body("email",equalTo("pambs@alpha.com"));

    }

    private String aClientExists(Client existingClient) {
        return given()
                .contentType(ContentType.JSON)
                 .body(existingClient).when()
                 .post("/client")
                 .jsonPath().getString("id");
    }
}
