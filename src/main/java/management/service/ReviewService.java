package management.service;

import management.model.Review;
import management.repo.ReviewRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepo reviewRepo;

    public ReviewService(ReviewRepo reviewRepo) {
        this.reviewRepo = reviewRepo;
    }

    public Review getReviewById(Long reviewId) {
        return reviewRepo.findById(reviewId).orElse(null);
    }

    public Review addReview(Review review) {
        return reviewRepo.save(review);
    }

    public Review getReview(Long reviewId) {
        return reviewRepo.findById(reviewId).orElse(null);
    }

    public void delete(Long reviewId) {
        reviewRepo.deleteById(reviewId);
    }

    public Review update(Review review, Long reviewId) {
        review.setId(reviewId);
        return reviewRepo.save(review);
    }

    public List<Review> findAll() {
        return reviewRepo.findAll();
    }
}
