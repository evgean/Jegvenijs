package ru.geekbrain.s3.e1;

import java.util.ArrayList;

public class Box<T extends Fruit> {
    private String clazName;
    private ArrayList<T> box;
    private static final float APPLE_WEIGHT = 1.0f;
    private static final float ORANGE_WEIGHT = 1.5f;

    public String getClazName() {
        return clazName;
    }

    public void add(T obj) {
        if (clazName == null) {
            box = new ArrayList<>();
            box.add(obj);
            clazName = obj.getClass().getName();
        } else {
            if (obj.getClass().getName().equals(clazName)) box.add(obj);
            else System.out.println("Wrong type");
        }


    }

    public float getWeight() {
        if (clazName == null) {
            return 0;
        }
        else {
            if (clazName.contains("Apple")) return box.size() * APPLE_WEIGHT;
            else return box.size() * ORANGE_WEIGHT;
        }

    }

    public boolean compare(Box box2) {
        return getWeight() == box2.getWeight();
    }

    public void addFromBox(Box box2) {
        if (box.getClass().getName() != null) {
            if (box2.getClazName().equals(this.clazName) || box2.getClazName() == null) {
                int ArrListSize = box.size();
                for (int i = 0; i < ArrListSize; i++) {
                    box2.add(box.get(i));
                }
                clazName = null;
                box = null;
            } else System.out.println("Wrong type");
        } else System.out.println("have no fruits in the box");
    }
}
