package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonWriter;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {
    private Product testProduct;

    @BeforeEach
    void runBefore() {
       testProduct = new Product("test", 5, "a test product");
    }

    @Test
    void testConstructor() {
        assertEquals("test", testProduct.getProductName());
        assertEquals(5, testProduct.getProductPrepareTime());
        assertEquals("a test product", testProduct.getProductDescription());
    }

    @Test
    void testProductToJson() {
        JSONObject testingJsonProduct = testProduct.toJson();
        assertEquals("{\"prepare time\":5,\"name\":\"test\",\"description\":\"a test product\"}", testingJsonProduct.toString());
    }
}
