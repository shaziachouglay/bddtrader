package net.bddtrader;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class WhenGettingCompanyDetailsByShazia {
    @Before
    public void prepare_rest_config(){
        RestAssured.baseURI = "http://localhost:9000/api";
    }

    @Test
    public void should_return_name_and_sector(){

//        RestAssured.get("/stock/aapl/company")
//                .then()
//                .body("companyName", Matchers.equalTo("Apple, Inc."))
//                .body("sector",Matchers.equalTo("Electronic Technology"));
        RestAssured.given()
                .pathParam("symbol","aapl")
                .when()
                .get("/stock/{symbol}/company")
                .then()
                .body("companyName", equalTo("Apple, Inc."))
                .body("sector", equalTo("Electronic Technology"));
    }

    @Test
    public void should_return_news_for_a_requested_company(){
        RestAssured.given()
                .queryParam("symbols","fb")
                .when()
                .get("/news")
                .then()
                .body("related",everyItem(containsString("FB")));
    }
    @Test
    public void should_return_news_for_a_requested_company_aapl(){
        RestAssured.given()
                .queryParam("symbols","aapl")
                .when()
                .get("/news")
                .then()
                .body("related",everyItem(containsString("AAPL")));
    }
}
