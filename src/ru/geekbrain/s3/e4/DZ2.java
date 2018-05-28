package ru.geekbrain.s3.e4;

import java.io.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DZ2 {
    private static final String READ_PATH = "text/text.txt";
    private static final String WRITE_PATH = "text/text_DZ2.txt";

    public static void main(String[] args) {

        BufferedReader readBR = null;
        StringBuilder readedText = new StringBuilder();
        //read file
        try {
            readBR = new BufferedReader(new FileReader(READ_PATH));
            String line = readBR.readLine();

            while (line != null) {
                readedText.append(line);
                readedText.append(System.lineSeparator());
                line = readBR.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                readBR.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        //write file
        String[] textArr = readedText.toString().split(System.lineSeparator());
        try {
            int textLines = textArr.length;

            FileWriter fw = new FileWriter(WRITE_PATH);
            BufferedWriter bf = new BufferedWriter(fw);
            PrintWriter write = new PrintWriter(bf);
            Lock lock = new ReentrantLock();

            WriteFiles thread1 = new WriteFiles(lock, write);
            WriteFiles thread2 = new WriteFiles(lock, write);
            WriteFiles thread3 = new WriteFiles(lock, write);

            for (int i = 0, j = 0; i < textLines; i++, j = i%3) {
                if (j == 0) thread1.startWriting(textArr[i]);
                else if (j == 1) thread2.startWriting(textArr[i]);
                else thread3.startWriting(textArr[i]);
            }
            //closing writer
            write.close();
            bf.close();
            fw.close();

            //closing threads
            thread1.exit = true;
            thread2.exit = true;
            thread3.exit = true;

            System.out.println("writing done");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class WriteFiles extends Thread{
    boolean exit = false;
    Lock lock;
    PrintWriter write;

    public WriteFiles(Lock lock, PrintWriter write) {
        this.lock = lock;
        this.write = write;
        start();
    }

    @Override
    public void run() {
        while (!exit) {
            try {
                sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void startWriting(String text) {
        lock.lock();
        try {
            write.println(text);
        } finally {
            lock.unlock();
        }

    }
}