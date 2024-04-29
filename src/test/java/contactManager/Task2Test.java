package contactManager;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Task2Test {
    @Test
    public void task2() {
        assertArrayEquals(new int[]{3, 2, -1, 7, 8, 0, 0}, Task2.number(new int[]{3, 2, 0, -1, 7, 0, 8}));
    }
}