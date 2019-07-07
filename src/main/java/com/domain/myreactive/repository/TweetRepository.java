package com.domain.myreactive.repository;

/**
 * User: franc
 * Date: 09/09/2018
 * Time: 1:11
 */

import com.domain.myreactive.model.Tweet;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Repository
public interface TweetRepository extends ReactiveMongoRepository<Tweet, String> {


    @Tailable
    public Flux<Tweet> findByUpdateAtAfter(LocalDate date);


}
