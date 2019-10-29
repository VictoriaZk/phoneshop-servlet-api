package com.es.phoneshop.model.product;

import com.es.phoneshop.model.exception.ProductNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private List<Product> products;

    ArrayListProductDao() {
        products = new ArrayList<>();
    }

    @Override
    public synchronized Product getProduct(Long id) {
        return products.stream().
                filter(product -> product.getId().equals(id)).
                findAny().orElseThrow( () -> new ProductNotFoundException( "Product is not found"));
    }

    @Override
    public synchronized List<Product> findProducts() {
        return products.stream().
                filter(product -> product.getPrice() != null && product.getStock() > 0).
                collect(Collectors.toList());
    }

    @Override
    public synchronized void save(Product product) {
        Product productWithSameId;
        if (product == null || product.getId() == null) {
            throw new IllegalArgumentException("Product or id can not be null");
        }
        try {
            productWithSameId = getProduct(product.getId());
        } catch (ProductNotFoundException exception) {
            productWithSameId = null;
        }
        if (productWithSameId == null) {
            products.add(product);
        } else {
            throw new IllegalArgumentException("Product with such id is already exists");
        }
    }

    @Override
    public synchronized void delete(Long id) {
        products.remove(getProduct(id));
    }
}
