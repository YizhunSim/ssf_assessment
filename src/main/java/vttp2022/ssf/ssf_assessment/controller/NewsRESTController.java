package vttp2022.ssf.ssf_assessment.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import vttp2022.ssf.ssf_assessment.models.NewsFeed;
import vttp2022.ssf.ssf_assessment.services.NewsService;

@RestController
@RequestMapping(path="/news")
public class NewsRESTController {
  @Autowired
  private NewsService newsService;

   @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getRollAsJson(@PathVariable("id") String id, Model model) {

        NewsFeed nfResult = newsService.getArticleById(id);

        if (nfResult == null){
           JsonObject errResp = Json
                    .createObjectBuilder()
                    .add("error", "Cannot find news article %s".formatted(id))
                    .build();
            String payload = errResp.toString();
            // Return 400
            return ResponseEntity
                    //.status(HttpStatus.BAD_REQUEST)
                    .badRequest() //400
                    .body(payload);
        }

        // Create the response payload
        JsonObjectBuilder builder = Json
                .createObjectBuilder()
                .add("id", nfResult.getId())
                .add("title", nfResult.getTitle())
                .add("body", nfResult.getBody())
                .add("published_on", nfResult.getBody())
                .add("url", nfResult.getUrl())
                .add("imageurl", nfResult.getImageurl())
                .add("tags", nfResult.getTags())
                .add("categories", nfResult.getCategories());

        // Get the JsonObject object from JsonBuilder
        JsonObject resp = builder.build();

        return ResponseEntity.ok(resp.toString());
    }

}
