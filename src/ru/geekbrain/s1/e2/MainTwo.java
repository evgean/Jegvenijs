package ru.geekbrain.s1.e2;

/*
 * Homework for lesson 2
 *
 * @author Jevgenijs A.
 */

public class MainTwo {
    public static void main(String[] args) {

        //1.
        int[] byteArray = { 1, 1, 0, 0, 1, 0, 1, 1, 0, 0};
        printArr(byteArray);
        byteArray = flippedArr(byteArray);
        printArr(byteArray);

        //2.
        int[] arr = new int[8];
        fillWithThree(arr);
        printArr(arr);

        //3.
        int[] arr2 = {1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1};
        doubleMultipleSix(arr2);
        printArr(arr2);

        //4.
        int[][] arr3 = new int[5][5];
        fillDiagonal(arr3);
        printArr(arr3);

        //5.
        int[] arr4 = {1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, -1};
        minAndMax(arr4);

        //6.
        int[] arr5 = {1, 1, 1, 2, 1};
        int[] arr6 = {2, 1, 1, 2, 1};
        int[] arr7 = {10, 10};
        System.out.println(findSumSymmetry(arr5));
        System.out.println(findSumSymmetry(arr6));
        System.out.println(findSumSymmetry(arr7));

        //7.
        int[] arr8 = {1, 5, 3, 2};
        int[] arr10 = {1, 5, 3, 2};
        int[] arr9 = {1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, -1};
        arr8 = moveArrStep(arr8, 10);
        printArr(arr8);
        arr9 = moveArrStep(arr9, -2);
        printArr(arr9);

    }
    public static void printArr(int[] arr) {
        int arrLength = arr.length;
        for (int i = 0; i < arrLength; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void printArr(int[][] arr) {
        int arrLength = arr.length;
        for (int i = 0; i < arrLength; i++) {
            System.out.print("[");
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.print("]");
        }
        System.out.println();
    }

    public static int[] flippedArr(int[] arr) {
        int byteArrayLength = arr.length;
        for (int i = 0; i < byteArrayLength; i++) {
            arr[i] = (arr[i] == 0 ? 1 : 0);
        }
        return arr;
    }

    public static int[] fillWithThree(int[] arr) {
        int arrLength = arr.length;
        for (int i = 0, j = 0; i < arrLength; i++, j+= 3) {
            arr[i] = j;
        }
        return arr;
    }

    public static int[] doubleMultipleSix(int[] arr) {
        int arrLength = arr.length;
        for (int i = 0; i < arrLength; i++) {
            if (arr[i] < 6) {
                arr[i] *= 2;
            }
        }
        return arr;
    }

    public static int[][] fillDiagonal(int[][] arr) {
        int arrLength = arr.length;
        for (int i = 0; i < arrLength; i++) {
            for (int j = 0; j < arrLength; j++) {
                if (i == j) arr[i][j] = 1;
                else arr[i][j] = 0;
            }
        }
        return arr;
    }

    public static void minAndMax(int[] arr) {
        int arrLength = arr.length;
        int max = arr[0];
        int min = arr[0];
        for (int i = 0; i < arrLength; i++) {
            if (max < arr[i]) max = arr[i];
            if (min > arr[i]) min = arr[i];
        }
        System.out.println("Максимальный элемент: " + max + ", Минимальный элемент: " + min);
    }

    public static boolean findSumSymmetry(int[] arr) {
        int arrLength = arr.length;
        int sum = 0;
        int rightSum = 0;
        boolean answer = false;
        for (int i = 0; i < arrLength; i++) {
            sum += arr[i];
        }
        for (int j = arrLength - 1; j >= 0; j--) {
            rightSum += arr[j];
            if (sum - rightSum == rightSum) answer = true;
        }
        return answer;
    }

    public static int[] moveArrStep(int[] arr, int step) {
        int arrLength = arr.length;
        //если шаг будет равен или больше чем длина массива
        if (Math.abs(step) >= arrLength) step = step % arrLength;

        //если шаг полодительный
        if (step >= 0) {
            //определяем сколько раз посторить цикл сдвига на 1
            for (int j = 0; j < step; j++) {
                //фиксируем последнее значение, что бы не потерять его при сдвиге
                int endValue = arr[arrLength - 1];
                //собственно сам цикл сдвига на 1
                for (int i = arrLength - 1; i > 0; i--) {
                    arr[i] = arr[i - 1];
                }
                arr[0] = endValue;
            }
        } else {
            //определяем сколько раз посторить цикл сдвига на 1
            for (int j = 0; j < Math.abs(step); j++) {
                //фиксируем первое значение, что бы не потерять его при сдвиге
                int endValue = arr[0];
                //цикл сдвига на 1
                for (int i = 0; i < arrLength - 1; i++) {
                    arr[i] = arr[i + 1];
                }
                arr[arrLength - 1] = endValue;
            }
        }

        return arr;
    }
}
