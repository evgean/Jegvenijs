package ru.geekbrain.s3.e1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        //Array type String switch
        String[] testArrString = new String[]{"one", "two", "three", "fore"};
        testArrString = switchArrEl(testArrString, 0,2);
        printArr(testArrString);

        //Array type Integer switch
        Integer[] testArrInteger = new Integer[]{1, 2, 3, 4};
        testArrInteger = switchArrEl(testArrInteger, 0,2);
        printArr(testArrInteger);

        //Array String to ArrayList
        String[] testArrString2 = new String[]{"one", "two", "three", "fore"};
        ArrayList<String> newArrListString2 = convertToArrayList(testArrString2);

        //Array Integer to ArrayList
        String[] testArrInt2 = new String[]{"one", "two", "three", "fore"};
        ArrayList<String> newArrListInt2 = convertToArrayList(testArrInt2);

        //create fruits
        Apple apple = new Apple();
        Orange orange = new Orange();

        Box<Fruit> appleBox = new Box();
        appleBox.add(apple);
        appleBox.add(apple);
        appleBox.add(apple);
        appleBox.add(orange);
        System.out.println(appleBox.getWeight());

        Box<Fruit> orangeBox = new Box();
        orangeBox.add(orange);
        orangeBox.add(orange);
        orangeBox.add(apple);
        System.out.println(orangeBox.getWeight());

        Box<Apple> appleBox1 = new Box();
        appleBox1.add(apple);
        appleBox1.add(apple);
        System.out.println(appleBox1.getWeight());

        //compare boxes by weight
        System.out.println(appleBox.compare(orangeBox));
        System.out.println(appleBox1.compare(orangeBox));

        appleBox.addFromBox(appleBox1);
        appleBox1.addFromBox(orangeBox);

        System.out.println(appleBox.getWeight());
        System.out.println(appleBox1.getWeight());
        System.out.println(orangeBox.getWeight());

        MyArrayList<String> arrList = new MyArrayList<>(1);
        arrList.add("Opa");
        arrList.add("shina");

        System.out.println(arrList.get(1));
        arrList.remove(0);

        System.out.println(arrList.get(1));
    }

    private static <T> T[] switchArrEl(T[] arr, int index, int newIndex) {
        T firstValue = arr[index];
        T secondValue = arr[newIndex];
        arr[index] = secondValue;
        arr[newIndex] = firstValue;
        return arr;
    }

    private static <T> void printArr(T[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println((i + 1) + " - " + arr[i]);
        }
        System.out.println("------");
    }

    private static <T> ArrayList<T> convertToArrayList(T[] arr) {
        ArrayList<T> arrList = new ArrayList<>();
        Collections.addAll(arrList, arr);

        return arrList;
    }
}
