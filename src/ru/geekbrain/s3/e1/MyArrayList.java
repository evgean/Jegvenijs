package ru.geekbrain.s3.e1;

import java.util.Arrays;

public class MyArrayList<T> {
    T[] arr;
    int count = 0;

    public MyArrayList() {
        arr = (T[]) new Object[8];
    }

    public MyArrayList(int size) {
        try {
            if (size < 1) throw new MyArrayListSizeException();
            else arr = (T[]) new Object[size];
        } catch (MyArrayListSizeException ex) {
            System.out.println(ex.msg);
            ex.printStackTrace();
        }

    }

    public void add(T obj) {
        if (arr[arr.length - 1] != null)  {
            T[] tempArr = (T[]) new Object[arr.length * 2];
            System.arraycopy(arr, 0, tempArr, 0, arr.length);
            arr = tempArr;
        }
        arr[count++] = obj;
    }

    public T get(int index) {
        return arr[index];
    }

    public void remove(int index) {
        int arrLength = arr.length;
        for (int i = 0; i < arrLength; i++) {
            if (i == arrLength - 1) arr[i] = null;
            else if (i >= index) arr[i] = arr[i + 1];
        }
    }
}

class MyArrayListSizeException extends Exception {
    String msg;

    MyArrayListSizeException()  {
        msg = new String("Size of the MyArrayList should be larger then 0");
    }

}
