import java.util.ArrayList;
import java.util.Collections;

public class Sorts {

    public static void InsertionSort(ArrayList<Integer> inputList, String fileName) {
        int i;
        int j;
        int temp;      // Temporary variable for swap

        SortStats ss = new SortStats("Insertion", fileName);
        long start = System.nanoTime();
        ArrayList<Integer> numbers = new ArrayList<>(inputList);

        //System.out.println("The original list is: " + numbers);
        
        for (i = 1; i < numbers.size(); ++i) {
            j = i;
            ss.loops ++;
            while (j > 0 && numbers.get(j) < numbers.get(j-1)){
                //or use: Collections.swap(numbers, j, j-1);
                ss.loops ++;
                ss.swaps ++;
                ss.comparisons ++;
                temp = numbers.get(j-1);
                numbers.set(j-1, numbers.get(j));
                numbers.set(j, temp);
                j--;
            }
        }
        //System.out.println("The list sorted is: " + numbers);
        ss.timeNano = System.nanoTime() - start;
        Main.sortData.add(ss);

    }

    public static void MergeSortWrapper(ArrayList<Integer> inputList, String fileName){

        SortStats ss = new SortStats("MergeSort", fileName);
        long start = System.nanoTime();
        ArrayList<Integer> numbers = new ArrayList<>(inputList);

        MergeSort(numbers, ss);
        
        ss.timeNano = System.nanoTime() - start;
        Main.sortData.add(ss);     

    }

    public static ArrayList<Integer> MergeSort(ArrayList<Integer> numbers, SortStats ss) {

        ss.comparisons++;
        if (numbers.size() <= 1)
            return numbers;

        int mid = numbers.size()/2;

        ArrayList<Integer> left = new ArrayList<>(numbers.subList(0,mid));
        ArrayList<Integer> right = new ArrayList<>(numbers.subList(mid, numbers.size()));
        left = MergeSort(left, ss);
        right = MergeSort(right, ss);

        return merge(left, right, ss);
    }

     public static ArrayList<Integer> merge(ArrayList<Integer> left, ArrayList<Integer> right, SortStats ss) {
        ArrayList<Integer> result = new ArrayList<>();
        int i = 0, j = 0;

        // Compare and merge elements
        while (i < left.size() && j < right.size()) {
            ss.loops ++;
            ss.comparisons++;
            if (left.get(i) <= right.get(j)) {
                result.add(left.get(i));
                i++;
            } else {
                result.add(right.get(j));
                j++;
            }
        }

        // Add remaining elements from left
        ss.comparisons++;
        while (i < left.size()) {
            ss.loops ++;
            result.add(left.get(i));
            i++;
        }

        // Add remaining elements from right
        ss.comparisons++;
        while (j < right.size()) {
            ss.loops ++;
            result.add(right.get(j));
            j++;
        }

        return result;
    }
    public static void RadixSort(ArrayList<Integer> inputList, String fileName, int maxValue) {

    SortStats ss = new SortStats("RadixSort", fileName);
    long start = System.nanoTime();

    // Work on a copy, not the original list
    ArrayList<Integer> numbers = new ArrayList<>(inputList);

    int exp = 1; // 1, 10, 100, 1000...

    // Loop through each digit position
    while (maxValue / exp > 0) {
        ss.loops++;

        // Create 10 buckets for digits 0–9
        ArrayList<ArrayList<Integer>> buckets = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ss.loops++;
            buckets.add(new ArrayList<>());
        }

        // Place numbers into buckets
        for (int i = 0; i < numbers.size(); i++) {
            ss.loops++;
            int digit = (numbers.get(i) / exp) % 10;
            buckets.get(digit).add(numbers.get(i));
            ss.swaps++; // counting data movement
        }

        // Rebuild numbers list from buckets
        int index = 0;
        for (int i = 0; i < 10; i++) {
            ss.loops++;
            for (int j = 0; j < buckets.get(i).size(); j++) {
                ss.loops++;
                numbers.set(index++, buckets.get(i).get(j));
                ss.swaps++;
            }
        }

        exp *= 10;
    }

    ss.timeNano = System.nanoTime() - start;
    Main.sortData.add(ss);
}

}
