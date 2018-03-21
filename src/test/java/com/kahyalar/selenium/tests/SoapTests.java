package com.kahyalar.selenium.tests;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * Created by kahyalar on 25/07/2017.
 */
public class SoapTests {
    String word = "ambitious";
    String env = "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
            "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">" +
            "<soap12:Body>" +
            "<Define xmlns=\"http://services.aonaware.com/webservices/\">" +
            "<word>" + word + "</word>" +
            "</Define>" +
            "</soap12:Body>" +
            "</soap12:Envelope>";

    @Before
    public void setUp(){
        RestAssured.baseURI = "http://services.aonaware.com/";
        RestAssured.basePath = "DictService/";
    }

    @Test
    public void soapTest(){
        Map<String, String> authHeaders = new HashMap<String, String>();
        authHeaders.put("SOAPAction", "Define");

        given().
                request().headers(authHeaders).contentType("application/soap+xml; charset=UTF-8;").body(env).
        when().
                post("DictService.asmx").
        then().
                extract().response().prettyPrint();
    }
}
