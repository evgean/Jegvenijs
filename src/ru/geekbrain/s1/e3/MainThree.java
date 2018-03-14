package ru.geekbrain.s1.e3;

import java.util.Random;
import java.util.Scanner;

public class MainThree {

    private static Scanner scanner = new Scanner(System.in);
    private static String[] words = {"apple", "orange", "lemon", "banana", "apricot", "avocado",
            "broccoli", "carrot", "cherry", "garlic", "grape", "melon", "leak", "kiwi", "mango",
            "mushroom", "nut", "olive", "pea", "peanut", "pear", "pepper", "pineapple", "pumpkin", "potato"};
    private static boolean exit = false;
    private static String randWord = words[randIndex(words.length)];
    private static int tryCount = 0;


    public static void main(String[] args) {

        while (!exit) {
            char[] equalsWord = new char[]{'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'};
            int equalsWordIndex = 0;
            tryCount++;

            String answer = ask();
            //условие победы
            if (answer.equals(randWord)) {
                System.out.println("Congratulations, you won! You guessed the word with " + tryCount + " attempts");
                exit = true;
            //куда без читов
            } else if (answer.equals("cheat")) {
                System.out.println(randWord);
            //условие проверки на совподение букв
            } else {
                for (int i = 0; i < answer.length(); i++) {
                    //что бы проверяемое слово небыло больше и положение букв совпало
                    if (i < randWord.length() && answer.charAt(i) ==  randWord.charAt(i)) {
                        equalsWord[equalsWordIndex] =  answer.charAt(i);
                        equalsWordIndex++;
                    }
                }
                printArr(equalsWord);
            }
        }
    }

    private static int randIndex(int indexLast) {
        Random rand = new Random();
        return rand.nextInt(indexLast);
    }

    private static String ask() {
        System.out.println(" - Guess the word: ");
        return scanner.nextLine();
    }

    private static void printArr(char[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
        }
    }
}
