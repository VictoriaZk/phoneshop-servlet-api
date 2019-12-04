package com.es.phoneshop.model.product;

import java.util.List;

public interface ProductReviewDao {
    ProductReview getProductReview(String id);
    void save(ProductReview productReview);
    List<ProductReview> getProductReviews(Product product);
    List<ProductReview> getProductReviews();
    ProductReview create(Product product, String userName, String text, int rating);
}
