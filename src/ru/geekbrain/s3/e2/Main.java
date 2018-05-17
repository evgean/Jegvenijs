package ru.geekbrain.s3.e2;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static final String CONN_URL = "jdbc:sqlite:product.db";
    private static CreateDBTables cdbt = new CreateDBTables(CONN_URL);
    private static ProductRepository pr = new ProductRepository(CONN_URL);

    private static Scanner sc = new Scanner(System.in);
    private static boolean exit = false;
    private static String command;

    public static void main(String[] args) {
        //I create table using method below
        //cdbt.createProductTable();

        //clear the table and add 50(take some time) rows
        pr.clearAll();
        pr.createPackOfProduct(50);

        //You can print rows using method below
        //pr.showAll();

        //Working in console
            //for exit press = 0 || "exit"
            //check the price = "/cost " + product name
            //show all available products = "/show " + product name
            //show product in cost range = "/priceFrom " + cost from + cost till
            //change cost using name = "/changeCost " + product name + new cost
        while(!exit) {
            System.out.println("Enter your command or type 'exit' for finishing the program");
            command = sc.nextLine();
            if (command.contains("/cost")) {
                try {
                    pr.getCostByTitle(command.substring(6));
                } catch (SQLException e) {
                    System.out.println("There is no such product");
                }
            } else if(command.contains("/changeCost")) {
                if (command.contains(" ")) {
                    String[] arr = command.split(" ");
                    if (arr.length > 2) {
                        try {
                            int cost = Integer.parseInt(arr[2]);
                            pr.setNewCostByTitle(arr[1], cost);
                        } catch (Exception ex) {
                            System.out.println("you enter wrong value");
                        }
                    } else System.out.println("Wrong command");
                } else System.out.println("Wrong commend");
            } else if(command.contains("/priceFrom")) {
                if (command.contains(" ")) {
                    String[] arr = command.split(" ");
                    if ( arr.length > 2 ) {
                        try {
                            int cost1 = Integer.parseInt(arr[1]);
                            int cost2 = Integer.parseInt(arr[2]);
                            pr.getCostFrom(cost1, cost2);
                        } catch (Exception ex) {
                            System.out.println("values need to be a numbers");
                        }
                    } else System.out.println("Wrong commad");
                } else System.out.println("Wrong command");
            } else if(command.contains("/show")) {pr.showAll();
            } else if(command.equals("exit") || command.equals("0")) {
                System.out.println("Bye-Bye!");
                exit = true;
            } else System.out.println("I do not know this command.");
        }

    }

}
