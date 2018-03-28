package ru.geekbrain.s1.e6;

import ru.geekbrain.s1.e6.animals.Animals;
import ru.geekbrain.s1.e6.animals.animal.Cat;
import ru.geekbrain.s1.e6.animals.animal.Dog;

public class Main {

    public static void main(String[] args) {
        Animals[] animalArr = {new Dog(), new Cat()};

        for (int i = 0; i < animalArr.length; i++) {
            System.out.println(animalArr[i].getName() + " c лимитом: " +
                            animalArr[i].getLengthLimit() + " бегает - " + animalArr[i].getIsRunning(250));
            System.out.println(animalArr[i].getName() + " c лимитом: " +
                    animalArr[i].getHeightLimit() + " пригает - " + animalArr[i].getIsJumping(1));
            System.out.println(animalArr[i].getName() + " c лимитом: " +
                    animalArr[i].getSwimLimit() + " плавает - " + animalArr[i].getIsSwimming(1));
        }
    }
}
