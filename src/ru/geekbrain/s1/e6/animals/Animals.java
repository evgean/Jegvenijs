package ru.geekbrain.s1.e6.animals;

public class Animals {

    private int lengthLimit;
    private float heightLimit;
    private int swimLimit;
    private String name;

    public Animals(int lengthLimit, float heightLimit, int swimLimit, String name) {
        this.lengthLimit = randomRange(lengthLimit, 200);
        this.heightLimit = randomRange(heightLimit, 3);
        this.swimLimit = randomRange(swimLimit, 10);
        this.name = name;
    }

    public boolean getIsRunning(int length) {return this.isRunning(length);}
    public boolean getIsSwimming(int length) {return this.isSwimming(length);}
    public boolean getIsJumping(float height) {return this.isJumping(height);}
    public String getName() {return this.name;}
    public int getLengthLimit() {return lengthLimit;}
    public float getHeightLimit() {return heightLimit; }
    public int getSwimLimit() {return swimLimit;}

    private boolean isRunning(int length) {
        return length <= lengthLimit;
    }

    private boolean isSwimming(int length) {
        return length <= swimLimit;
    }

    private boolean isJumping(float height) {
        return height <= heightLimit;
    }

    //указываешь число и разброс(т.е. + - значение от указанного числа).
    private int randomRange(int number, int scatter) {
        if (number <= 0) return 0;
        return (int)((number - scatter) + (Math.random() * (scatter * 2)));
    }

    private float randomRange(float number, int scatter) {
        if (number <= 0) return 0;
        return (float)((number - scatter) + (Math.random() * (scatter * 2)));
    }

}
