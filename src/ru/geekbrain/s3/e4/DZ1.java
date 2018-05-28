package ru.geekbrain.s3.e4;

public class DZ1 {
    static final SyncObj syncObj = new SyncObj(5);

    public static void main(String[] args) {
        syncObj.start();
    }
}

class SyncObj extends Thread{
    boolean exit = false;
    public int count;
    public byte threadNum = 1;

    public SyncObj(int count) {
        this.count = count;
    }

    Thread1 t1 = new Thread1(this);
    Thread2 t2 = new Thread2(this);
    Thread3 t3 = new Thread3(this);

    @Override
    public void run() {
        t1.start();
        t2.start();
        t3.start();
    }
}

class Thread1 extends Thread{
    SyncObj obj;

    public Thread1(SyncObj obj) {
        this.obj = obj;
    }

    @Override
    public void run() {
        try {
            synchronized (obj) {
//                System.out.println("Start A");
                while(!obj.exit){
                    if (obj.threadNum == 1) {
                        obj.threadNum = 2;
                        System.out.print("A");
                        obj.notifyAll();
                    } else obj.wait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Thread2 extends Thread{
    SyncObj obj;

    public Thread2(SyncObj obj) {
        this.obj = obj;
    }

    @Override
    public void run() {
        try {
//            System.out.println("Start B");
            synchronized (obj) {
                while(!obj.exit) {
                    if (obj.threadNum == 2) {
                        obj.threadNum = 3;
                        System.out.print("B");
                        obj.notifyAll();
                    } else obj.wait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Thread3 extends Thread{
    SyncObj obj;

    public Thread3(SyncObj obj) {
        this.obj = obj;
    }

    @Override
    public void run() {
        try {
//            System.out.println("Start C");
            synchronized (obj) {
                while(!obj.exit) {
                    if (obj.threadNum == 3) {
                            obj.threadNum = 1;
                            System.out.print("C");
                            obj.count--;
                            if (obj.count == 0) obj.exit = true;
                            obj.notifyAll();
                    } else obj.wait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
