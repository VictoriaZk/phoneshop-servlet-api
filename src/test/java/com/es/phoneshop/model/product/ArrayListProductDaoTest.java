package com.es.phoneshop.model.product;

import com.es.phoneshop.model.exception.ProductNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest {
    private static final Long ID = 1L;
    private static final Long ID_4 = 4L;
    private ProductDao productDao;
    private Product product;
    private Product product1;
    private Product product2;


    @Before
    public void setup() {
        productDao = ArrayListProductDao.getInstance();
        ((ArrayListProductDao) productDao).deleteAll();
        product = new Product();
        product.setId(ID);
    }


    @Test
    public void testFindProduct() {
        product.setPrice(new BigDecimal(10));
        product.setStock(12);
        productDao.save(product);
        assertNotNull(productDao.findProducts());
    }

    @Test
    public void testFindProductAfterSavingWithStockLess0() {
        product.setPrice(new BigDecimal(1));
        product.setStock(0);
        productDao.save(product);
        assertTrue(productDao.findProducts().isEmpty());
    }

    @Test
    public void testGetProductById() {
        Product product4 = new Product();
        product4.setId(4L);
        assertEquals(ID_4, product4.getId());
    }

    @Test(expected = ProductNotFoundException.class)
    public void testGetProductByIdNoResult() {
        productDao.delete(product.getId());
        productDao.getProduct(ID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveProductWithNullId() {
        product.setId(null);
        productDao.save(product);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveProductWithTheSameId() {
        product.setId(ID);
        Product productWithTheSameId = new Product();
        productWithTheSameId.setId(ID);
        productDao.save(product);
        productDao.save(productWithTheSameId);
    }

    @Test
    public void testDelete() {
        productDao.save(product);
        productDao.delete(ID);
        assertTrue(productDao.findProducts().isEmpty());
    }

    @Test
    public void testFindByDescription() {
        int stock = 1;
        BigDecimal price = new BigDecimal(1);
        product1 = new Product();
        product1.setDescription("Appu");
        product1.setId(1L);
        product1.setPrice(price);
        product1.setStock(stock);
        List<Product> controlProducts = new ArrayList<>();
        controlProducts.add(product1);
        productDao.save(product1);
        assertEquals(controlProducts, productDao.findProductsByDescription("Appu"));
        productDao.delete(product1.getId());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testSortByNotExistingField() {
        productDao.sort(productDao.findProducts(), "not existing field", "order");
    }

    @Test
    public void testSortByEmptyField() {
        productDao.save(product);
        productDao.delete(product.getId());
        List<Product> products = productDao.findProducts();
        assertEquals(products, productDao.sort(products, "", "order"));
    }
}
