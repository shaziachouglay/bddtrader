package net.bddtrader.unittests;

import io.restassured.RestAssured;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class WhenGettingCompanyDetailsSecondPart {
    @Before
    public void prepareRestConfig(){
        RestAssured.baseURI = "https://bddtrader.herokuapp.com/api";
    }

    @Test
    public void shouldReturnNameAndSector(){
        RestAssured.given()
                .pathParam("symbol","aapl")
                .when()
                .get("https://bddtrader.herokuapp.com/api/stock/{symbol}/company")
                .then()
                .body("companyName",equalTo("Apple, Inc."))
                .body("sector",equalTo("Electronic Technology"));
    }

    @Test
    public void shouldReturnNewsFortheRequestedCompany(){
        RestAssured.given()
                .queryParam("symbols","fb")
                .when()
                .get("/news")
                .then()
                .body("related",everyItem(containsString("FB")));
    }
    @Test
    public void shouldReturnNewsFortheRequestedCompanyAAPL(){
        RestAssured.given()
                .queryParam("symbols","aapl")
                .when()
                .get("/news")
                .then()
                .body("related",everyItem(containsString("AAPL")));
    }
    @Test
    public void findASimpleFieldValue(){
        RestAssured.given()
                .pathParam("stockid","aapl")
                .when()
                .get("https://bddtrader.herokuapp.com/api/stock/{stockid}/company")
                .then()
                .body("industry",equalTo("Telecommunications Equipment"));
    }
    @Test
    public void check_that_a_field_value_contains_a_given_string() {
      RestAssured.given()
      .pathParam("stockid","aapl")
      .when().get("https://bddtrader.herokuapp.com/api/stock/{stockid}/company")
      .then().body("description",containsString("smartphones"));
    }

    @Test
    public void find_a_nested_field_value() {
        RestAssured.given()
                .pathParam("stockid","aapl")
                .when().get("https://bddtrader.herokuapp.com/api/stock/{stockid}/book")
                .then().body("quote.symbol",equalTo("AAPL"));
    }
    @Test
    public void find_a_list_of_values() {
        RestAssured.given().get("/tops/last").then()
                .body("symbol",hasItems("PTN", "PINE", "TRS"));
    }
    @Test
    public void make_sure_at_least_one_item_matches_a_given_condition() {
        RestAssured.given().get("/tops/last").then()
        .body("price",hasItems(greaterThan(100.0f)));
    }
    @Test
    public void find_a_field_of_an_element_in_a_list() {
        RestAssured.given().pathParam("symbol","aapl")
                .when().get("/stock/{symbol}/book")
                .then().body("trades[0].price",equalTo(319.59f));
    }
    @Test
    public void find_a_field_of_the_last_element_in_a_list() {
        RestAssured.given().pathParam("symbol","aapl")
                .when().get("/stock/{symbol}/book")
                .then().body("trades[-1].price",equalTo(319.54f));
    }
    @Test
    public void find_the_number_of_trades() {
        RestAssured.given().pathParam("symbol","aapl")
                .when().get("/stock/{symbol}/book")
                .then().body("trades.size()",equalTo(20));
    }
    @Test
    public void find_the_minimum_trade_price() {
        RestAssured.given().pathParam("symbol","aapl")
                .when().get("/stock/{symbol}/book")
                .then().body("trades.price.min()",equalTo(319.38f));
    }
    @Test
    public void find_the_size_of_the_trade_with_the_minimum_trade_price() {
        RestAssured.given().pathParam("symbol","aapl")
                .when().get("/stock/{symbol}/book")
                .then().body("trades.min{it.price}.volume",equalTo(100f));
    }
    @Test
    public void find_the_number_of_trades_with_a_price_greater_than_some_value() {
        RestAssured.given().pathParam("symbol","aapl")
                .when().get("/stock/{symbol}/book")
                .then().body("trades.findAll{it.price > 319.50}.size()",equalTo(13));
    }
}
