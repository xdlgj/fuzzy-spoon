import java.util.Arrays;

public class InsertionSort {
    public static void main(String[] args) {
        int[] array = {8, 0, 2, 1, 4, 5, 8};
        System.out.println(Arrays.toString(array));
        System.out.println();
        // 1、假设需要确定位置的元素是数组的最后一个元素
        int j = array.length;
        while (j > 0) {
            // 2、依次与前面的元素进行比较，如果比前面的元素大则进行换位，否则终止该次循环（因为前面的元素已经排好序了）
            if (array[j] > array[j - 1]) {
                int temp = array[j];
                array[j] = array[j - 1];
                array[j - 1] = temp;
                j--;
                System.out.println(Arrays.toString(array));
            } else {
                break;
            }
        }
    }
}
