package vttp2022.ssf.ssf_assessment.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class NewsFeed {
  private int id;
  private int published_on;
  private String title;
  private String url;
  private String imageurl;
  private String body;
  private String tags;
  private String categories;

  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public int getPublished_on() {
    return published_on;
  }
  public void setPublished_on(int published_on) {
    this.published_on = published_on;
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public String getUrl() {
    return url;
  }
  public void setUrl(String url) {
    this.url = url;
  }
  public String getImageurl() {
    return imageurl;
  }
  public void setImageurl(String imageurl) {
    this.imageurl = imageurl;
  }
  public String getBody() {
    return body;
  }
  public void setBody(String body) {
    this.body = body;
  }
  public String getTags() {
    return tags;
  }
  public void setTags(String tags) {
    this.tags = tags;
  }
  public String getCategories() {
    return categories;
  }
  public void setCategories(String categories) {
    this.categories = categories;
  }

  // Json to Model
  public static NewsFeed create(int index, JsonObject jo){
    NewsFeed nf = new NewsFeed();
    nf.setId(index + 1);
    nf.setPublished_on(jo.getInt("published_on"));
    nf.setTitle(jo.getString("title"));
    nf.setUrl(jo.getString("url"));
    nf.setImageurl(jo.getString("imageurl"));
    nf.setBody(jo.getString("body"));
    nf.setTags(jo.getString("tags"));
    nf.setCategories(jo.getString("categories"));
    return nf;
  }

  // Model to Json
  public JsonObject toJson(NewsFeed nf){
    return Json.createObjectBuilder()
    .add("id", nf.getId())
    .add("title", nf.getTitle())
    .add("body", nf.getBody())
    .add("published_on", nf.getPublished_on())
    .add("url", nf.getUrl())
    .add("imageurl", nf.getImageurl())
    .add("tags", nf.getTags())
    .add("categories", nf.getCategories())
    .build();
  }

  public String toString(){
    return "News Feed - id: " + id + " title: " + title + " body: " + body + " published_on: " + published_on + " url" + url + " imageurl: " + imageurl + " tags: " + " categories: " + categories;
  }
}
