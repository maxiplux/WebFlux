package com.domain.myreactive;

/**
 * User: franc
 * Date: 09/09/2018
 * Time: 1:17
 */
import com.domain.myreactive.model.Tweet;
import com.domain.myreactive.repository.TweetRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebfluxDemoApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    TweetRepository tweetRepository;

    @Test
    public void testCreateTweet() {
        Tweet tweet = new Tweet();
        tweet.setText("This is a Test Tweet");

        webTestClient.post().uri("/tweets")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(tweet), Tweet.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.text").isEqualTo("This is a Test Tweet");
    }

    @Test
    public void testGetAllTweets() {
        webTestClient.get().uri("/tweets")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Tweet.class);
    }

    @Test
    public void testGetSingleTweet() {
        Tweet tweet2 = new Tweet();
        tweet2.setText("Hello, World!");
        Tweet tweet = tweetRepository.save(tweet2).block();

        webTestClient.get()
                .uri("/tweets/{id}", Collections.singletonMap("id", tweet.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response ->
                        Assertions.assertThat(response.getResponseBody()).isNotNull());
    }

    @Test
    public void testUpdateTweet() {
        Tweet tweet1 = new Tweet();
        tweet1.setText("Initial Tweet");
        Tweet tweet = tweetRepository.save(tweet1).block();

        Tweet newTweetData = new Tweet();
        newTweetData.setText("Updated Tweet");

        webTestClient.put()
                .uri("/tweets/{id}", Collections.singletonMap("id", tweet.getId()))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(newTweetData), Tweet.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.text").isEqualTo("Updated Tweet");
    }

    @Test
    public void testDeleteTweet() {
        Tweet tweet1 = new Tweet();
        tweet1.setText("To be deleted");
        Tweet tweet = tweetRepository.save(tweet1).block();

        webTestClient.delete()
                .uri("/tweets/{id}", Collections.singletonMap("id",  tweet.getId()))
                .exchange()
                .expectStatus().isOk();

        //http://www.javawa.top/posts/75cc52fb/
    }
}
