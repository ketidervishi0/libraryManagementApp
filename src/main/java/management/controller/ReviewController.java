package management.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import management.mappers.RestResponseMapper;
import management.model.Author;
import management.model.Review;
import management.request.ReviewRequest;
import management.service.AuthorService;
import management.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static management.constants.Messages.*;

@RestController
@RequestMapping(value = "/review")
public class ReviewController {

    private final ReviewService reviewService;
    private final ModelMapper modelMapper;
    private final AuthorService authorService;

    public ReviewController(ReviewService reviewService, ModelMapper modelMapper, AuthorService authorService) {
        this.reviewService = reviewService;
        this.modelMapper = modelMapper;
        this.authorService = authorService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<Object> getAllReviews() {
        try {
            List<Review> reviewList = this.reviewService.findAll();
            if (reviewList.isEmpty()) {
                return RestResponseMapper.map(SUCCESS, HttpStatus.NOT_FOUND, null, RECORDS_RECEIVED);
            }
            return RestResponseMapper.map(SUCCESS, HttpStatus.OK, reviewList, RECORDS_RECEIVED);
        } catch (Exception ex) {
            return RestResponseMapper.map(FAIL, HttpStatus.INTERNAL_SERVER_ERROR, null, SERVER_ERROR);
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Object> getReviewById(@PathVariable @NotNull Long id) {
        try {
            Review review = reviewService.getReviewById(id);
            if (review == null) {
                return RestResponseMapper.map(SUCCESS, HttpStatus.NOT_FOUND, null, RECORDS_RECEIVED);
            }
            return RestResponseMapper.map(SUCCESS, HttpStatus.OK, review, RECORDS_RECEIVED);
        } catch (Exception ex) {
            return RestResponseMapper.map(FAIL, HttpStatus.INTERNAL_SERVER_ERROR, null, RECORDS_RECEIVED);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addReview(@RequestBody @Valid ReviewRequest reviewRequest) {
        try {

            Review review = modelMapper.map(reviewRequest, Review.class);
            Author author = authorService.findById(reviewRequest.getAuthorId());
            if (author == null) {
                return RestResponseMapper.map(FAIL, HttpStatus.NOT_FOUND, null, RECORDS_RECEIVED);
            }
            review.setAuthor(author);
            review = reviewService.addReview(review);
            return RestResponseMapper.map(SUCCESS, HttpStatus.OK, review, RECORD_CREATED);
        } catch (Exception e) {
            return RestResponseMapper.map(FAIL, HttpStatus.INTERNAL_SERVER_ERROR, null, SERVER_ERROR);
        }
    }

    @PutMapping("/updateById/{id}")
    public ResponseEntity<Object> updateReviewById(@PathVariable @NotNull Long id, @Valid @RequestBody ReviewRequest reviewRequest) {
        try {
            Review review = reviewService.getReview(id);
            if (review == null) {
                return RestResponseMapper.map(SUCCESS, HttpStatus.NOT_FOUND, null, NOT_FOUND);
            }
            review = modelMapper.map(reviewRequest, Review.class);
            Author author = authorService.findById(reviewRequest.getAuthorId());
            if (author == null) {
                return RestResponseMapper.map(FAIL, HttpStatus.NOT_FOUND, null, RECORDS_RECEIVED);
            }
            review.setAuthor(author);
            review = reviewService.update(review, id);

            return RestResponseMapper.map(SUCCESS, HttpStatus.OK, review, RECORD_UPDATED);
        } catch (Exception e) {
            return RestResponseMapper.map(FAIL, HttpStatus.INTERNAL_SERVER_ERROR, null, SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Object> deleteReviewById(@PathVariable @NotNull Long id) {
        try {
            Review review = reviewService.getReview(id);
            if (review == null) {
                return RestResponseMapper.map(SUCCESS, HttpStatus.NOT_FOUND, null, NOT_FOUND);
            }
            reviewService.delete(id);
            return RestResponseMapper.map(SUCCESS, HttpStatus.OK, "The review with ID " + id + " has been deleted successfully.", RECORD_DELETED);
        } catch (Exception e) {
            return RestResponseMapper.map(FAIL, HttpStatus.INTERNAL_SERVER_ERROR, null, SERVER_ERROR);

        }
    }

}