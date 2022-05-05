package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class OrderTest {
    private Order testOrder;
    private Order testOrder2;
    private Product testProduct;
    private Product testProduct2;

    @BeforeEach
    void runBefore() {
        testOrder = new Order("Test");
        testOrder2 = new Order("Test 2");

        testProduct = new Product("test", 5,"a test product");
        testProduct2 = new Product("test2", 3,"a test product");

        testOrder.add(testProduct);
    }

    @Test
    void testAddToEmpty() {
        testOrder2.add(testProduct2);
        assertEquals(1, testOrder2.getSize());
    }

    @Test
    void testAddToNonEmpty() {
        testOrder.add(testProduct2);
        assertEquals(2, testOrder.getSize());
    }

    @Test
    void testRemove() {
        testOrder.remove(testProduct);
        assertEquals(0, testOrder.getSize());
    }

    @Test
    void testTotalOrderTimeEmpty() {
        assertEquals(0, testOrder2.totalOrderTime());
    }

    @Test
    void testTotalOrderTimeNonEmpty() {
        assertEquals(5, testOrder.totalOrderTime());

        testOrder.add(testProduct2);
        assertEquals(8, testOrder.totalOrderTime());
    }

    @Test
    void testViewOrderContentsEmpty() {
        assertEquals("", testOrder2.viewOrderContents());
        assertEquals(0, testOrder2.getSize());
    }

    @Test
    void testViewOrderContentsNonEmpty() {
        assertEquals("\n\t- test", testOrder.viewOrderContents());
        assertEquals(1, testOrder.getSize());

        testOrder.add(testProduct2);
        assertEquals("\n\t- test\n\t- test2", testOrder.viewOrderContents());
        assertEquals(2, testOrder.getSize());
    }

    @Test
    void testGetName() {
        assertEquals("Test", testOrder.getName());
        assertEquals("Test 2", testOrder2.getName());
    }

    @Test
    void testOrderToJson() {
        JSONObject testingOrder = testOrder.toJson();
        assertEquals("{\"name\":\"Test\",\"order\":[{\"prepare time\":5,\"name\":\"test\",\"description\"" +
                ":\"a test product\"}]}", testingOrder.toString());
    }

    @Test
    void testGetProductNames() {
        ArrayList<String> test = testOrder.getProductNames();
        assertEquals(test, testOrder.getProductNames());
    }

    @Test
    void testGetProductNamesEmptyOrder() {
        ArrayList<String> test = testOrder2.getProductNames();
        assertEquals(test, testOrder2.getProductNames());
    }

    @Test
    void testRemoveInt() {
        testOrder.remove(0);
        assertEquals(0, testOrder.getSize());
    }

}
