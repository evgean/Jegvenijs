package ru.geekbrain.s3.e4;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MFU {
    public static ArrayList<String> content = new ArrayList<>();
    private static Lock lock = new ReentrantLock();
    private static Scanner sc = new Scanner(System.in);
    static int docNum = 0;

    public static void main(String[] args) {
        //app will wait until some of doc will be scanned and then will print it
        boolean loop = true;
        PrintSearch ps = new PrintSearch();
        ps.start();

        System.out.println("You start printer app");
        System.out.println("If you want to exit type : 'exit'");

        while(loop) {
            System.out.println("Enter count of doc you want to scan and print: ");
            try{
                if (sc.hasNextInt()) docNum = sc.nextInt();
                else {
                    if (sc.next().equals("exit")) {
                        loop = false;
                        ps.stopSearch();
                        docNum = 0;
                        System.out.println("Thank you");
                    } else {
                        System.out.println("This is not a number or word 'exit'");
                        docNum = 0;
                    }
                }
            } catch (Exception ex) {
                System.out.println("this is not a number");
                docNum = 0;
            }

            // lets scan (and print)
            for (int i = 0; i < docNum; i++) new Scann(lock, String.valueOf(i)).start();
        }



    }
}

class PrintSearch extends Thread {
    private boolean isContent = true;
    private int printcount = 0;
    private Lock lock = new ReentrantLock();

    @Override
    public void run() {
        while(isContent) {
            if (MFU.content.size() > printcount) {
                new Print(lock, MFU.content.get(printcount)).start();
                printcount++;
            }
            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopSearch() {
        isContent = false;
    }
}

class Print extends Thread {
    String text;
    Lock lock;

    public Print(Lock lock, String text) {
        this.text = text;
        this.lock = lock;
    }

    @Override
    public void run() {
        lock.lock();
        try{
            // simulation of different work time
            try {
                sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Printing " + text);
        }finally {
            lock.unlock();
        }

    }
}

class Scann extends Thread {
    String doc;
    String text;
    Lock lock;

    public Scann(Lock lock, String doc) {
        this.doc = doc;
        this.lock = lock;
    }

    @Override
    public void run() {
        lock.lock();
        try {
            // simulation of different work time
            try {
                sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            text = doc;
            MFU.content.add(text);
            System.out.println("scanned " + doc);
        } finally {
            lock.unlock();
        }
    }


}
