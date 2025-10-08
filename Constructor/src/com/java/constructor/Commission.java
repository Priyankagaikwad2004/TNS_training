package com.java.constructor;

import java.util.Scanner;

public class Commission {
    String name;
    String address;
    String phone;
    double salesAmount;

    void Details() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter Name: ");
        name = scanner.nextLine();
        
        System.out.print("Enter Address: ");
        address = scanner.nextLine();
        
        System.out.print("Enter Phone: ");
        phone = scanner.nextLine();
        
        System.out.print("Enter Sales Amount: ");
        salesAmount = scanner.nextDouble();
    }

    void Commission() {
        double commission = 0;

        if (salesAmount >= 100000) {
            System.out.println("Commission is 10%");
        } else if (salesAmount >= 50000) {
            System.out.println("Commission is 5%");
        } else if (salesAmount >= 30000) {
            System.out.println("Commission is 3%");
        } else {
            System.out.println("No commission");
        }

    }
}