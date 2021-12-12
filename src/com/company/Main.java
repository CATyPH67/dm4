package com.company;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("enter size of array: ");
        int size = sc.nextInt();
        int[] arr = new int[size];
        int key = -1;

        while (key != 0) {
            System.out.println("print 1 to array in order, 2 to array in reverse order, 3 to random array: ");
            key = sc.nextInt();
            switch (key) {
                case (1):
                    for (int i = 0; i < arr.length; i++) {
                        arr[i] = i + 1;
                    }
                    break;

                case (2):
                    for (int i = 0, j = size; i < arr.length; i++, j--) {
                        arr[i] = j;
                    }
                    break;

                case (3):
                    Random r = new Random();
                    for (int i = 0; i < arr.length; i++) {
                        arr[i] = r.nextInt(size);
                    }
                    break;

                default:
                    break;
            }

            System.out.printf("array: %s%n%n", Arrays.toString(arr));
            ResultSort resultMerge = sortMerge(arr.clone(), arr.length);
            System.out.printf("for Merge sort count comparison  = %d, sorted array = %s%n%n",
                    resultMerge.getCountComparison(), Arrays.toString(resultMerge.getArr()));
            ResultSort resultQuick = sortQuick(arr.clone(), 0, arr.length - 1);
            System.out.printf("for Quick count comparison  = %d, sorted array = %s%n%n",
                    resultQuick.getCountComparison(), Arrays.toString(resultQuick.getArr()));
            System.out.println("--------------------------------------");
        }
    }

    public static ResultSort sortMerge(int[] a, int n) {
        if (n < 2) {
            return new ResultSort(a, 0);
        }
        int countComparison = 0;
        int mid = n / 2;
        int[] l = new int[mid];
        int[] r = new int[n - mid];

        for (int i = 0; i < mid; i++) {
            l[i] = a[i];
        }
        for (int i = mid; i < n; i++) {
            r[i - mid] = a[i];
        }
        ResultSort leftSort = sortMerge(l, mid);
        countComparison += leftSort.getCountComparison();

        ResultSort rightSort = sortMerge(r, n - mid);
        countComparison += rightSort.getCountComparison();

        countComparison += merge(a, l, r, mid, n - mid).getCountComparison();

        return new ResultSort(a, countComparison);
    }

    public static ResultSort merge(
            int[] a, int[] l, int[] r, int left, int right) {

        int countComparison = 0;
        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            countComparison++;
            if (l[i] <= r[j]) {
                a[k++] = l[i++];
            }
            else {
                a[k++] = r[j++];
            }
        }
        while (i < left) {
            a[k++] = l[i++];
        }
        while (j < right) {
            a[k++] = r[j++];
        }
        return new ResultSort(a, countComparison);
    }

    public static ResultSort sortQuick(int[] arr, int low, int high) {

        int countComparison = 0;
        if (low < high) {
            int pivot = arr[high];
            int i = (low - 1);

            for (int j = low; j <= high - 1; j++) {
                countComparison++;
                if (arr[j] < pivot) {
                    i++;
                    swap(arr, i, j);
                }
            }
            swap(arr, i + 1, high);
            int pi = (i + 1);

            countComparison += sortQuick(arr, low, pi - 1).getCountComparison();
            countComparison += sortQuick(arr, pi + 1, high).getCountComparison();
        }
        return new ResultSort(arr, countComparison);
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
