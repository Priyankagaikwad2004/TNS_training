package com.java.inputClass;

import java.util.Scanner;

public class InputClass {
	
	public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter your name:");
        String fullName = input.nextLine();

        System.out.println("Enter your roll number:");
        String rollNumber = input.nextLine();

        System.out.println("Enter your Grade:");
        String grade = input.nextLine();

        System.out.println("Enter your percentage:");
        String percentage = input.nextLine();

        System.out.println("\n--- Your Details ---");
        
        System.out.println(fullName);
        System.out.println(rollNumber);
        System.out.println(grade);
        System.out.println(percentage);
	}

}
