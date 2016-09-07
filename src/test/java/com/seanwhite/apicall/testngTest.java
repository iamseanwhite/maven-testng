package com.seanwhite.apicall;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by iamseanwhite on 9/7/2016.
 * Uses TestNG to hit the public Weather Underground API
 * and see if the current temperature in a given location
 * is above a given threshold
 */
public class testngTest {

    @Test(groups = {"myOnlyGroup"})
    public void temperatureTest() throws Exception {

        String apiKey = "f61b08db479e20e7";
        String city = "Atlanta";
        String state =  "GA";
        Double thresholdTemp = 80.0;

        URL weather = new URL("http://api.wunderground.com/api/" + apiKey + "/conditions/q/" + state + "/" + city + ".json");
        URLConnection connection = weather.openConnection();
        BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = input.readLine()) != null) {    //prints response text to the console and builds a response string
            System.out.println(inputLine);
            response.append(inputLine);
        }

        input.close();

        String responseString = response.toString();
        JSONObject responseObject = new JSONObject(responseString);         //creates a new JSONObject with name/value mappings from the JSON string.
        Double currentTemp = responseObject.getJSONObject("current_observation").getDouble("temp_f");

        System.out.println("\nCurrent temperature in " + city + ", " + state + " is " + currentTemp + "F");

        Assert.assertTrue(currentTemp <= thresholdTemp, "Current temp is greater than " + thresholdTemp + "F");      //Fails when temp > 80

    }

}
