package src;

import org.junit.Before;
import org.junit.Test;

import java.util.Vector;

import static org.junit.Assert.assertEquals;

public class TestMain {
    StudentJDBC studentJDBC = null;

    @Before
    public void init() {
        studentJDBC = StudentJDBC.getInstance();
    }

    @Test
    public void testProductSave() {
        // Test inserting a product into the database
        int i = StudentJDBC.getInstance().insert("11", "Simon", "Male", 24, "Spain", "Math");
        assertEquals(1, i);
    }

    @Test
    public void testProductQuery() {
        // Test querying a product by name
        Vector select = StudentJDBC.getInstance().select("Simon");
        assertEquals("11", ((Vector) (select.get(0))).get(0).toString());
    }

    @Test
    public void testProductQueryNotExist() {
        // Test querying a product that does not exist
        Vector select = StudentJDBC.getInstance().select("Steven");
        assertEquals(0, select.size());
    }

    @Test
    public void testProductUpdate() {
        // Test updating a product's information
        Vector select = StudentJDBC.getInstance().select("Simon");

        Vector vector = (Vector) select.get(0);
        int i = StudentJDBC.getInstance().update(
                vector.get(0).toString(),
                vector.get(1).toString(),
                vector.get(2).toString(),
                Integer.parseInt(vector.get(3).toString()) + 1,
                vector.get(4).toString(),
                vector.get(5).toString()
        );
        assertEquals(1, i);
    }

    @Test
    public void testProductDelete() {
        // Test deleting a product by name
        int i = studentJDBC.delete("Simon");
        assertEquals(1, i);
    }
}
