package com.nhom1.service;

import java.util.List;

import com.nhom1.exception.ProductException;
import com.nhom1.model.Review;
import com.nhom1.model.User;
import com.nhom1.request.ReviewRequest;

public interface ReviewService {
	
	public Review createReview(ReviewRequest req, User user)throws ProductException;
	public List<Review> getAllReview(Long productId);
}
