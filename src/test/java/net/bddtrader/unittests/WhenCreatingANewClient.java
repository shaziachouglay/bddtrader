package net.bddtrader.unittests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.bddtrader.clients.Client;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class WhenCreatingANewClient {

    @Before
    public void setUpBaseUrl(){
        RestAssured.baseURI = "http://localhost:9000/api";
    }

    @Test
    public void eachNewClientShouldGetANewId(){

        Client aNewClient = Client.withFirstName("Michael").andLastName("Scott")
                .andEmail("mscott@test.com");
        Map<String,Object> clientData = new HashMap<>();
        clientData.put("email","michelle@testalpha.com");
        clientData.put("firstName","michael");
        clientData.put("lastName","tester");
        String newClient =
        "{\n" +
                "  \"email\": \"mikr@test.com\",\n" +
                "  \"firstName\": \"mike\",\n" +
                "  \"lastName\": \"tester\"\n" +
                "}";

//        RestAssured.given()
//                .contentType(ContentType.JSON)
//                .body(newClient).when()
//                .post("/client").then().statusCode(200)
//                .and().body("id",not(equalTo(0)))
//                .and().body("email",equalTo("mikr@test.com"))
//                .and().body("firstName",equalTo("mike"))
//                .and().body("lastName",equalTo("tester"));
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(clientData).when()
                .post("/client").then().statusCode(200)
                .and().body("id",not(equalTo(0)))
                .and().body("email",equalTo("michelle@testalpha.com"));


    }



}
