package org.example;

import com.alibaba.fastjson.JSONException;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GoogleDistanceMatrixAPI {

  public static void main(String[] args) throws JSONException {

    // Prompt the user to enter the pickup and dropoff locations
   // Scanner scanner = new Scanner(System.in);
    //use args to post pickup and dropoff
    System.out.println("Enter pickup location:");
    String pickup = args[0];
    System.out.println("Enter dropoff location:");
    String dropoff = args[1];

    // Build the URL for the Google Distance Matrix API request
    HttpUrl.Builder urlBuilder = HttpUrl.parse("https://maps.googleapis.com/maps/api/distancematrix/json").newBuilder();
    urlBuilder.addQueryParameter("origins", pickup);
    urlBuilder.addQueryParameter("destinations", dropoff);
    urlBuilder.addQueryParameter("units", "imperial");
    urlBuilder.addQueryParameter("key", "myKey");

    // Create an OkHttpClient object to send the HTTP request
    OkHttpClient client = new OkHttpClient().newBuilder().build();

    // Build the HTTP request using the URL created above
    Request request = new Request.Builder()
            .url(urlBuilder.build())
            .get()
            .build();

    try (Response response = client.newCall(request).execute()) {
      // Get the API response body as a string
      String responseBody = response.body().string();
      // Print the response body to the console
      System.out.println(responseBody);

      // Write the response body to a file using the Gson library
      FileWriter fileWriter = new FileWriter("response.json");
      Gson gson = new Gson();
      gson.toJson(responseBody, fileWriter);
      fileWriter.close();

    } catch (IOException e) {
      // If an IOException is caught, throw a RuntimeException
      throw new RuntimeException(e);
    }

    // Print a message indicating that the program is exiting
    System.out.println("Exiting Application!");
  }
}
