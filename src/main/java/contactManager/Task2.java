package contactManager;

import java.util.ArrayList;

public class Task2 {
    public static int[] numbers(int[] array) {
        int[] result = new int[array.length];
        int counter = 0;
        for (int count : array) if (count != 0) result[counter++] = count;
        return result;
    }

    public static int[] number(int[] numbers) {
        ArrayList<Integer> list = new ArrayList<>();
        int counter = 0;
        for (int number : numbers) {
            list.add(number);
            if (number == 0) {
                list.remove((Integer) 0);
                counter++;
            }
            for (int i = 0; i < counter; i++) {
                list.add(0);
            }
        }
        return list.stream().mapToInt(i -> i).toArray();
    }
}
