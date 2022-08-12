package vttp2022.ssf.ssf_assessment.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp2022.ssf.ssf_assessment.models.NewsFeed;
import vttp2022.ssf.ssf_assessment.services.NewsService;

@Controller
@RequestMapping(path={"/", ""})
public class NewsController {

  @Autowired
  private NewsService newsService;

  @GetMapping
  public String retrieveNewsArticles(Model model){
    List<NewsFeed> listOfArticles = newsService.getArticles();
    model.addAttribute("articles", listOfArticles);
    return "index";
  }

  @PostMapping (path={"/articles"})
  public String saveArticles(Model model, @RequestBody(required = false) MultiValueMap<String, String> form){
    List<NewsFeed> listOfArticles = newsService.getArticles();
    model.addAttribute("articles", listOfArticles);
    if (form == null) {
      System.out.println("No articles to save!");
      return "index";
    }
    List<String> ids = form.get("articleCheckBox");
    List<NewsFeed> articlesToSave = new LinkedList<>();
    for (String id : ids){
      System.out.println("NewsController - saveArticles - id: " + id);
      for (NewsFeed nf : listOfArticles){
        if (id.equals(nf.getId())){
          articlesToSave.add(nf);
        }
      }
    }
    System.out.println("NewsController: saveArticles - articlesToSave: " + articlesToSave);

    newsService.saveArticles(articlesToSave);

    return "index";
  }

}
