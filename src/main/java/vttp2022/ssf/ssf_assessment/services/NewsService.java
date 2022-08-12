package vttp2022.ssf.ssf_assessment.services;

import java.io.Reader;
import java.io.StringReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2022.ssf.ssf_assessment.models.NewsFeed;
import vttp2022.ssf.ssf_assessment.repositories.NewsFeedRepositories;

@Service
public class NewsService {
  @Autowired
  private NewsFeedRepositories newsFeedRepositories;

  @Value("${API_KEY}")
  private String key;

  private static final String URL = "https://min-api.cryptocompare.com/data/v2/news/?lang=EN";

  public List<NewsFeed> getArticles(){

      // create the GET Request, GET url
      RequestEntity<Void> request = RequestEntity.get(URL).build();
      // System.out.println("URLLLLL++++: " + request);
      // Make the call to OpenWeatherMap
      RestTemplate template = new RestTemplate();
      ResponseEntity<String> response;
      String payload;

      try{
        response = template.exchange(request, String.class);
      } catch(Exception ex){
        System.err.printf("Error: %s\n", ex.getMessage());
        return Collections.emptyList();
      }

      if (response.getStatusCodeValue() != 200){
        System.err.println("Error status code is not 200");
        return Collections.emptyList();
      }

      payload = response.getBody();
      // System.out.println("NewsFeedService - Payload: " + payload);

      // Convert Payload to JsonObject
      // Convert the String to a Reader
      Reader strReader = new StringReader(payload);
      // Create a JsonReader from reader
      JsonReader jsonReader = Json.createReader(strReader);
      JsonObject newsFeedResult = jsonReader.readObject();
      JsonArray newsFeedData = newsFeedResult.getJsonArray("Data");

      List<NewsFeed> listOfArticles = new LinkedList<>();

      for (int i = 0; i < newsFeedData.size(); i++){
        JsonObject jo = newsFeedData.getJsonObject(i);
        listOfArticles.add(NewsFeed.create(jo));
      }

      return listOfArticles;
  }

  public void saveArticles(List<NewsFeed> newsFeedsList){
    for (NewsFeed nf : newsFeedsList){
       newsFeedRepositories.saveNewFeed(nf);
    }
  }

  public NewsFeed getArticleById (String id){
    System.out.println("NewsService - getArticleById id: " + id);
    Optional<String> opt = newsFeedRepositories.getArticle(id);
    String payload;
    if (opt.isEmpty()){
      return null;
    } else{
      payload = opt.get();
      System.out.println("NewsService - getArticleById -[PAYLOAD]:  " + payload);
      // Convert Payload to JsonObject
      // Convert the String to a Reader
      Reader strReader = new StringReader(payload);
      // Create a JsonReader from reader
      JsonReader jsonReader = Json.createReader(strReader);
      JsonObject articleObject = jsonReader.readObject();
      return NewsFeed.create(articleObject);
    }
  }
}
