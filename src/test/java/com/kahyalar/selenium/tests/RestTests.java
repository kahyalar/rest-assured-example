package com.kahyalar.selenium.tests;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by kahyalar on 25/07/2017.
 */

@RunWith(DataProviderRunner.class)
public class RestTests {
    String BulbasaurID = "1";
    String BulbasaurName = "bulbasaur";

    @Before
    public void startUp(){
        RestAssured.baseURI = "http://pokeapi.co/";
        RestAssured.basePath = "api/v2/";
    }

    @DataProvider
    public static Object[][] pokemons() {
        return new Object[][] {
                {"pokemon","1"},
                {"pokemon","2"},
                {"pokemon","3"},
                {"pokemon","4"},
                {"pokemon","5"},
                {"pokemon","6"}
        };
    }

    @Test
    public void pokemonNameTestWithID(){
        given().
                pathParam("path", "pokemon").pathParam("pokeID", BulbasaurID).
        when().
                get("{path}/{pokeID}").
        then().
                assertThat().body("name", equalTo(BulbasaurName));
    }

    @Test
    public void pokemonMoveTestWithName(){
        given().
                pathParam("path", "pokemon").pathParam("pokemon", BulbasaurName).
        when().
                get("{path}/{pokemon}").
        then().
                assertThat().body("moves.move.name[0]", equalTo("razor-wind"));
    }

    @Test
    @UseDataProvider("pokemons")
    public void pokemonWeightTest(String path, String pokeID){
        given().
                pathParam("path", path).pathParam("pokeID",pokeID).
        when().
                get("{path}/{pokeID}").
        then().
                body("weight", greaterThan(50)).
        and().
                assertThat().statusCode(200);
    }


}
