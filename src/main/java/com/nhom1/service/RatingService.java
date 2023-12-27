package com.nhom1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nhom1.exception.ProductException;
import com.nhom1.model.Rating;
import com.nhom1.model.User;
import com.nhom1.request.RatingRequest;

@Service
public interface RatingService {

	public Rating createRating(RatingRequest req, User usser) throws ProductException;
	
	public List<Rating> getProductsRatings(Long productId);
	
	
}
