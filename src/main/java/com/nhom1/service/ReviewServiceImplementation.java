package com.nhom1.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nhom1.exception.ProductException;
import com.nhom1.model.Product;
import com.nhom1.model.Review;
import com.nhom1.model.User;
import com.nhom1.repository.ProductRepository;
import com.nhom1.repository.ReviewRepository;
import com.nhom1.request.ReviewRequest;

@Service
public class ReviewServiceImplementation implements ReviewService{

	private ReviewRepository reviewRepository;
	private ProductService productService;
	private ProductRepository productRepository;

	public ReviewServiceImplementation(ReviewRepository reviewRepository,
			ProductService productService,
			ProductRepository productRepository) {
		this.reviewRepository=reviewRepository;
		this.productRepository=productRepository;
		this.productService=productService;
	}
	@Override
	public Review createReview(ReviewRequest req, User user) throws ProductException {
		Product product = productService.findProductById(req.getProductId());
		
		Review review = new Review();
		review.setUser(user);
		review.setProduct(product);
		review.setReview(req.getReview());
		review.setCreatedAt(LocalDateTime.now());
		
		return reviewRepository.save(review);
	}

	@Override
	public List<Review> getAllReview(Long productId) {
		// TODO Auto-generated method stub
		return reviewRepository.getAllProductsReview(productId);
	}

	
}
