package persistence;


import model.Order;
import model.Product;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// This class is modeled based on JsonSerializationDemo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReaderTest {

    @Test
    void testReaderInvalidFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Order o = reader.read();
            fail("Expected IOException");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyOrder() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyOrder.json");
        try {
            Order o = new Order("Test order");
            JsonWriter writer = new JsonWriter("./data/testReaderEmptyOrder.json");
            writer.open();
            writer.write(o);
            writer.close();

            o = reader.read();
            assertEquals("Test order", o.getName());
            assertEquals(0, o.getSize());

        } catch (IOException e) {
            fail("Exception shouldn't have been thrown");
        }
    }

    @Test
    void testReaderExampleOrder() {
        JsonReader reader = new JsonReader("./data/testReaderExampleOrder.json");
        try {
            Order o = new Order("Test order");
            JsonWriter writer = new JsonWriter("./data/testReaderExampleOrder.json");
            Product A = new Product("A", 3,"test product");
            o.add(A);
            writer.open();
            writer.write(o);
            writer.close();

            o = reader.read();
            assertEquals("Test order", o.getName());
            assertEquals(1, o.getSize());

        } catch (IOException e) {
            fail("Exception shouldn't have been thrown");
        }
    }


}
