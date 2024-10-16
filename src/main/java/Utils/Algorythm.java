package Utils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;

public class Algorythm {
    public static void mergeSort(int[] arr, int left, int right ){
        if (left >= right) {return;}
        int mid = (left + right) / 2;
        
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }

    public static void merge(int[] arr, int left, int mid, int right){
        int n1 = mid - left + 1;
        int n2 = right - mid;
        int[] L = new int[n1];
        int[] R = new int[n2];
        System.arraycopy(arr, left, L, 0, n1);
        System.arraycopy(arr, mid + 1, R, 0, n2);
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
        }
        while (i < n1) {
            arr[k++] = L[i++];
        }
        while (j < n2) {
            arr[k++] = R[j++];
        }
    }

    public static int[][] SortByIndex(int index, int[][] arr){
        Arrays.sort(arr, new Comparator<int[]>() {
            @Override 
            public int compare(int[] a, int[] b){
                return a[index] - b[index];
            }
        });
        return arr;
    }

    // ccw is for counter clockwise
    // returns int > 0 if 
    public static double ccw(Vec2 p1, Vec2 p2, Vec2 p3){
        Line2 v12 = new Line2(p1, p2);
        Line2 v23 = new Line2(p2, p3);

        return Math.acos((dot(v12, v23)) / (v12.norm() * v23.norm()));

    }

    public static void Graham_scan(Vec2[] points){}

    public static int dot(Line2 v1, Line2 v2){
        return (v1.p2.x - v1.p1.x)*(v2.p2.x - v2.p1.x) + (v1.p2.y - v1.p1.y)*(v2.p2.y - v2.p1.y) ;
    }
    public static int dot(Vec2 v1, Vec2 v2){
        return 1;
    }

}
