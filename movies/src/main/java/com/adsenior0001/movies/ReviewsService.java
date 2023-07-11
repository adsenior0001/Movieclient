package com.adsenior0001.movies;


import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class ReviewsService {
    private final ReviewsRepository reviewsRepository;

    public ReviewsService(ReviewsRepository reviewsRepository, MongoTemplate mongoTemplate) {
        this.reviewsRepository = reviewsRepository;
        this.mongoTemplate = mongoTemplate;
    }
    private final MongoTemplate mongoTemplate;

        public Reviews createReview(String reviewsBody, String imdbId){
            Reviews review =reviewsRepository.insert(new Reviews(reviewsBody));

            mongoTemplate.update(Movie.class)
                    .matching(Criteria.where("imdbId").is(imdbId))
                    .apply(new Update().push("reviewIds").value(review))
                    .first();

            return review;

        }


    }

