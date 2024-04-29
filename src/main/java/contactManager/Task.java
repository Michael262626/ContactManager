package contactManager;

import java.util.ArrayList;

public class Task {
    public static int numbers(int[] array){
        if(array.length < 1){
            throw new IllegalArgumentException("Array must have at least one element");
        }
        int max = Integer.MIN_VALUE;
        ArrayList<Integer> list = new ArrayList<>();
        for (int num = 0; num <  array.length; num++){
           for(int num2 = 0; num2 < array.length; num2++){
               if(num == num2)
                   continue;
               list.add(array[num] * array[num2]);
           }
        }
        for (int counter : list) {
            if (counter > max) max = counter;
        }
        if (max == Integer.MIN_VALUE){
            throw new IllegalArgumentException("Array must not have least one element");
        }
        return max;
    }
}
