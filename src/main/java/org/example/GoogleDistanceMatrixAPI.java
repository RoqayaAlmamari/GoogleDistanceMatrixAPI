package org.example;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class GoogleDistanceMatrixAPI {

  public static void main(String[] args) throws JSONException {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter pickup location:");
    String pickup = scanner.nextLine();
    System.out.println("Enter dropoff location:");
    String dropoff = scanner.nextLine();

    HttpUrl.Builder urlBuilder = HttpUrl.parse("https://maps.googleapis.com/maps/api/distancematrix/json").newBuilder();
    urlBuilder.addQueryParameter("origins", pickup);
    urlBuilder.addQueryParameter("destinations", dropoff);
    urlBuilder.addQueryParameter("units", "imperial");
    urlBuilder.addQueryParameter("key", "myKey");


    OkHttpClient client = new OkHttpClient().newBuilder().build();

    Request request = new Request.Builder()
            .url(urlBuilder.build())
            .get()
            .build();

    try (Response response = client.newCall(request).execute()) {
      String responseBody = response.body().string();
      System.out.println(responseBody);

      FileWriter fileWriter = new FileWriter("response.json");
      Gson gson =new Gson();
      gson.toJson(responseBody,fileWriter);

      fileWriter.close();

    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    System.out.println("Exiting Application!");
  }
}

