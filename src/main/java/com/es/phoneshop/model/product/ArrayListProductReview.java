package com.es.phoneshop.model.product;

import com.es.phoneshop.model.cart.HttpSessionCartService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ArrayListProductReview implements ProductReviewDao {
    private static volatile ArrayListProductReview INSTANCE;
    private List<ProductReview> productReviews = new ArrayList<>();

    private ArrayListProductReview(){}

    public static ProductReviewDao getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpSessionCartService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ArrayListProductReview();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public ProductReview create(Product product, String userName, String text, int rating) {
        ProductReview productReview = new ProductReview();
        if (rating > 0 && rating <=5) {
            productReview.setRating(rating);
            productReview.setProduct(product);
            productReview.setText(text);
            productReview.setUserName(userName);
            productReview.setId(generateUniqueId());
        } else {
            throw new IllegalArgumentException("Rating should be greater 0 and less or equals 5");
        }
        return productReview;
    }

    @Override
    public ProductReview getProductReview(String id) {
        return productReviews.stream()
                .filter(productReview -> productReview.getId().equals(id))
                .findAny()
                .get();
    }

    @Override
    public void save(ProductReview productReview) {
        productReviews.add(productReview);
    }

    @Override
    public List<ProductReview> getProductReviews(Product product) {
        return productReviews.stream()
                .filter(productReview -> productReview.getProduct().equals(product))
                .filter(ProductReview::isApproved)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductReview> getProductReviews() {
        return productReviews.stream()
                .filter(productReview -> !productReview.isApproved())
                .collect(Collectors.toList());
    }

    private String generateUniqueId() {
        return UUID.randomUUID().toString();
    }
}
