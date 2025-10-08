package com.java.overloading;

import java.util.Scanner;

class TicketBooking {
    private String eventName;
    private String customerName;
    private int seatCount;

    public TicketBooking(String eventName, String customerName, int seatCount) {
        this.eventName = eventName;
        this.customerName = customerName;
        this.seatCount = seatCount;
    }

    private void printDetails() {
        System.out.println("\n--- Booking Receipt ---");
        System.out.println("Event: " + eventName);
        System.out.println("Customer: " + customerName);
        System.out.println("Seats Booked: " + seatCount);
    }

    // Payment by cash
    public void makePayment(double amount) {
        printDetails();
        System.out.println("Payment Type: Cash");
        System.out.println("Amount Paid: " + amount);
        System.out.println("----------------------------");
    }

    // Payment by wallet
    public void makePayment(String walletNumber, double amount) {
        printDetails();
        System.out.println("Payment Type: Wallet");
        System.out.println("Wallet Number: " + walletNumber);
        System.out.println("Amount Paid: " + amount);
        System.out.println("----------------------------");
    }

    // Payment by card
    public void makePayment(String cardNumber, String ccv, String holderName, double amount) {
        printDetails();
        System.out.println("Payment Type: Credit Card");
        System.out.println("Card Holder: " + holderName);
        System.out.println("Card Number: " + cardNumber);
        System.out.println("Amount Paid: " + amount);
        System.out.println("----------------------------");
    }
}

public class TicketBookingSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Event Name: ");
        String event = sc.nextLine();

        System.out.print("Enter Your Name: ");
        String customer = sc.nextLine();

        System.out.print("Enter Number of Seats: ");
        int seats = sc.nextInt();
        sc.nextLine(); // clear buffer

        TicketBooking booking = new TicketBooking(event, customer, seats);

        System.out.println("\nSelect Payment Method:");
        System.out.println("1. Cash");
        System.out.println("2. Wallet");
        System.out.println("3. Credit Card");
        System.out.print("Your choice: ");
        int choice = sc.nextInt();
        sc.nextLine(); 

        System.out.print("Enter Amount to Pay: ");
        double amount = sc.nextDouble();
        sc.nextLine(); // clear buffer

        switch (choice) {
            case 1:
                booking.makePayment(amount);
                break;
            case 2:
                System.out.print("Enter Wallet Number: ");
                String wallet = sc.nextLine();
                booking.makePayment(wallet, amount);
                break;
            case 3:
                System.out.print("Enter Card Number: ");
                String card = sc.nextLine();
                System.out.print("Enter CCV: ");
                String ccv = sc.nextLine();
                System.out.print("Enter Card Holder Name: ");
                String holder = sc.nextLine();
                booking.makePayment(card, ccv, holder, amount);
                break;
            default:
                System.out.println("Invalid Option! Payment Failed.");
        }

        sc.close();
    }
}
