package ru.geekbrain.s3.e3;

import javafx.beans.binding.IntegerExpression;

import java.io.*;
import java.text.ParseException;
import java.util.Scanner;

public class TextReader {
    private static final String FILE_PATH = "text/text.txt";
    private static final String FILE_PATH1 = "text/text_01.txt";
    private static final String FILE_PATH2 = "text/text_02.txt";
    private static final String FILE_PATH3 = "text/text_03.txt";
    private static final String FILE_PATH4 = "text/text_04.txt";
    private static final String FILE_PATH5 = "text/text_05.txt";
    private static final String FILE_MARGE_PATH = "text/textMarge.txt";
    private static final String FILE_BOOK_PATH = "text/LordOfTheRings.txt";
    private static File file = new File(FILE_PATH);
    private static FileInputStream fis;
    private static byte[] readedFile;
    private static BufferedWriter bw;
    private static Scanner scanner = new Scanner(System.in);
    private static Boolean endReading = false;

    private static int pageNum = 0;
    private static String answer;

    public static void main(String[] args) {
//        homeWorkOne();

//        homeWorkTwo();

        homeWorkThree();

    }

    public static void homeWorkOne() {

        try {
            fis = new FileInputStream(file);
            int fisLength = fis.available();
            readedFile = new byte[fisLength];
            fis.read(readedFile, 0, fisLength);
            System.out.println(new String(readedFile));
            fis.close();
        } catch (FileNotFoundException e) {
            System.out.println("Не удалось связаться с файлом: " + FILE_PATH);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Не удалось прочить фаил: " + FILE_PATH);
            e.printStackTrace();
        }
    }

    public static void homeWorkTwo() {
        // можно было бы вывести все и в метод homeWorkTwo, но т.к. в реальности делал бы все в другом классе оставил так
        mergeFiles(createFileArr(FILE_PATH1, FILE_PATH2, FILE_PATH3, FILE_PATH4, FILE_PATH5), new File(FILE_MARGE_PATH));

    }

    public static void homeWorkThree() {
        System.out.println("This is app for reading book 'Lord Of The Rings'.");
        System.out.println("Enter the number of page you want to read or type next to see next page");
        System.out.println("if you want to exit - press 'exit'");
        System.out.println();

        while (!endReading) {
            if (scanner.hasNextInt()) {
                pageNum = scanner.nextInt();
                    try {
                        // -1 т.к. 0 страницы не существует
                        readPage(pageNum - 1);
                    } catch (Exception ex) {
                        System.out.println("     (bot) there is no such page");
                        pageNum = 0;
                    }
            }
            else {
                answer = scanner.nextLine();
                if (answer.equals("exit")) endReading = true;
                else if (answer.equals("next")) readNextPage();
                else System.out.println("     (bot) this is not a number");
            }
        }
    }

    private static File[] createFileArr(String ... arr) {
        int arrLength = arr.length;
        File[] files = new File[arrLength];
        for (int i = 0; i < arrLength; i++) {
            files[i] = new File(arr[i]);
        }
        return files;
    }

    private static void mergeFiles(File[] fileArr, File endPath) {
        try {
            FileWriter fw = new FileWriter(endPath, true);
            bw = new BufferedWriter(fw);
        } catch (IOException e) {
            System.out.println("Траблы с создание потока записи");
            e.printStackTrace();
        }

        int fileArrLength = fileArr.length;
        String textLine;
        for (int i = 0; i < fileArrLength; i++) {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileArr[i])));
                while ((textLine = br.readLine()) != null) {
                    bw.write(textLine);
                    bw.newLine();
                }
                bw.flush();
                br.close();
            } catch (FileNotFoundException e) {
                System.out.println("Траблы с потоком чтения");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("Траблы с чтением текста в файле");
                e.printStackTrace();
            }
        }

        try {
            bw.close();
        } catch (IOException e) {
            System.out.println("траблы с закрытием потока записи");
            e.printStackTrace();
        }
    }


    public static void readPage(int pageNum) {
        long curTume = System.currentTimeMillis();
        File file = new File(FILE_BOOK_PATH);

        try {
            fis = new FileInputStream(file);
            int fisLength = fis.available();
            readedFile = new byte[fisLength];
            fis.read(readedFile, 0, fisLength);

            int symbolCount = pageNum * 1800;
            int readTill = symbolCount + 1800;
            byte[] pageSymbol = new byte[1800];
            for (int i = symbolCount, j = 0; i < readTill; i++, j++) {
                pageSymbol[j] = readedFile[i];
            }
            System.out.println("------------------------------");
            System.out.println(new String(pageSymbol));
            System.out.println("      (bot) Time to print page: " + (System.currentTimeMillis() - curTume) + " ms");
            fis.close();
        } catch (FileNotFoundException e) {
            System.out.println("Не удалось связаться с файлом: " + FILE_BOOK_PATH);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Не удалось прочить фаил: " + FILE_BOOK_PATH);
            e.printStackTrace();
        }
    }

    public static void readNextPage() {
        readPage(pageNum);
        pageNum++;
    }

}
