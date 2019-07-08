package com.domain.myreactive.repository;

/**
 * User: franc
 * Date: 09/09/2018
 * Time: 1:11
 */

import com.domain.myreactive.model.Tweet;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TweetRepository extends ReactiveCrudRepository<Tweet, String> {



    @Tailable
    Flux<Tweet> findByTextNotLike(String argument);





}
