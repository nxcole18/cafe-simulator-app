package persistence;

import model.Order;
import model.Product;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// This class is modeled based on JsonSerializationDemo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Order o = new Order("Test order");
            JsonWriter writer = new JsonWriter("./data/my\0illegalFile.json");
            writer.open();
            fail("Expected IOException");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyOrder() {
        try {
            Order o = new Order("Test order");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyOrder.json");
            writer.open();
            writer.write(o);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyOrder.json");
            o = reader.read();
            assertEquals("Test order", o.getName());
            assertEquals(0, o.getSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterExampleOrder() {
        try {
            Order o = new Order("Test order");
            Product A = new Product("A", 3, "Example product A");
            o.add(A);
            Product B = new Product("B", 3, "Example product B");
            o.add(B);
            JsonWriter writer = new JsonWriter("./data/testWriterExampleOrder.json");
            writer.open();
            writer.write(o);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterExampleOrder.json");
            o = reader.read();
            assertEquals("Test order", o.getName());
            assertEquals(2, o.getSize());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
