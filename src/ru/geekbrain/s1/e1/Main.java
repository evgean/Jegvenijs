package ru.geekbrain.s1.e1;

public class Main {
    public static void main(String[] args) {

        byte variableByte = 127;
        short variableShort = 1234;
        int variableInt = 1234;
        long variableLong = 200000001L;

        float variableFloat = 1.5f;
        double variableDouble = 1.6;

        char variableChar = '#';

        boolean variableBool = true;
    }

    private static float expression(float a, float b, float c, float d) {
        return a * (b + c / d);
    }

    private static boolean sumRange(float a, float b) {
        boolean inRange = false;
        if (a + b >= 10 & a + b <= 20) inRange = true;
        return inRange;
    }

    private static void positiveness(int a) {
        String answer = "Положительно число";
        if (a < 0) answer = "Отрицательное число";
        System.out.println(answer);
    }

    private static boolean negativeNumber(int a) {
        boolean answer = false;
        if (a < 0) answer = true;
        return answer;
    }

    private static void printName(String name) {
        System.out.println("Привет, " + name);
    }

    private static void leapYear(int year) {
        String answer = "Не является высокосным";
        if (year % 100 == 0) {
            if (year % 400 == 0) answer = "Является высокосным";
        } else if (year % 4 == 0) answer = "Является высокосным";

        System.out.println(answer);
    }

}
