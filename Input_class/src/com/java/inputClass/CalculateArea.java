package com.java.inputClass;

import java.util.Scanner;

public class CalculateArea {

		public static double radius;
	    public static String colour;
	
	    public void getInput() {
	        Scanner scanner = new Scanner(System.in);
	
	        System.out.print("Enter the radius of the circle: ");
	        radius = scanner.nextDouble();
	        scanner.nextLine(); 
	
	        System.out.print("Enter the colour of the circle: ");
	        colour = scanner.nextLine();
	    }
	
	    public void calcArea() {
	        double area = Math.PI * radius * radius;
	        System.out.println("The area of the " + colour + " circle is: " + area);
	    }

}
