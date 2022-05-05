package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MenuTest {
    private Menu testMenu;
    private Menu testMenu2;
    private Product testProduct;
    private Product testProduct2;

    @BeforeEach
    void runBefore() {
        testMenu = new Menu();
        testMenu2 = new Menu();

        testProduct = new Product("test", 5,"a test product");
        testProduct2 = new Product("test2", 3,"a test product");

        testMenu.add(testProduct);
    }

    @Test
    void testAddEmpty() {
        testMenu2.add(testProduct);
        assertEquals(1, testMenu2.getMenuSize());
    }

    @Test
    void testAddNonEmpty() {
        testMenu.add(testProduct2);
        assertEquals(2, testMenu.getMenuSize());
    }

    @Test
    void testInitializeEmptyMenu() {
        assertEquals("", testMenu2.initializeMenu());
        assertEquals(0, testMenu2.getMenuSize());
    }

    @Test
    void testInitializeNonEmptyMenu() {
        testMenu.add(testProduct2);
        assertEquals("\n\t- test, a test product\n\t- test2, a test product", testMenu.initializeMenu());
        assertEquals(2, testMenu.getMenuSize());
    }


}
