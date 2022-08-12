package vttp2022.ssf.ssf_assessment.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import vttp2022.ssf.ssf_assessment.models.NewsFeed;

@Repository
public class NewsFeedRepositories {
    @Autowired
    @Qualifier("redislab")
    private RedisTemplate<String, String> redisTemplate;

    public void saveNewFeed(NewsFeed newsFeed){
		  String id = String.valueOf(newsFeed.getId());
		  ValueOperations<String, String> valueOp = redisTemplate.opsForValue();
      valueOp.set(id, newsFeed.toJson(newsFeed).toString());
      System.out.println("NewsFeedRepositories: - saveNewFeed Success! ");
    }

    public Optional<String> getArticle(String id) {
      if (!redisTemplate.hasKey(id)){
          System.out.println("NewsFeedRepositories - getArticle - Unable to find key: " + id);
          return Optional.empty();
      }

      ValueOperations<String, String> valueOp = redisTemplate.opsForValue();
      String newsFeedResult = valueOp.get(id);
      System.out.println("NewsFeedRepositories - getArticle - newsFeedResult: " + newsFeedResult);
      return Optional.of(newsFeedResult);
	}
}
