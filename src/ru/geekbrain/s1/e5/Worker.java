package ru.geekbrain.s1.e5;

public class Worker {
    private String fullName;
    private String position;
    private String email;
    private int phone;
    private double salary;
    private int age;

    public Worker(String fullName, String position, String email, int phone, double salary, int age) {
        this.fullName = fullName;
        this.position = position;
        this.email = email;
        this.phone = phone;
        this.salary = salary;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void printWorker() {
        System.out.println("----Информация о сотрудника----");
        System.out.println("ИФО: " + fullName);
        System.out.println("Должность: " + position);
        System.out.println("Email: " + email);
        System.out.println("Телефон: " + phone);
        System.out.println("Зарплата: " + salary);
        System.out.println("Возраст: " + age);
    }
}
