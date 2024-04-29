package contactManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    @Test
    public void testTask() {
        assertEquals(20, Task.numbers(new int[]{-1}));
        try {
            Task.numbers(new int[]{-1});
        } catch (IllegalArgumentException ex) {
            assertEquals(ex.getMessage(), "Array must not have one element");
        }
    }
}