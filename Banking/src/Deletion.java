import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Deletion {

    Scanner sc = new Scanner(System.in);

    void accCloseFun(int accNo) throws IOException {
        System.out.println("Are you sure you want to delete your account?");
        System.out.println("Type 1: Yes");
        System.out.println("Type 2: No");

        int conf = sc.nextInt();
        sc.nextLine();

        if (conf == 2) {
            Main.menu(accNo);
            return;
        } else if (conf != 1) {
            System.out.println("Incorrect option. Try again.\n");
            accCloseFun(accNo);
            return;
        }

        deleteAccount(accNo);
    }

    void deleteAccount(int accNo) {

        try {
            Connection con = db.DBConnection.getConnection();

            if (con == null) {
                System.out.println("Database error.");
                return;
            }

            // delete from transactions first (if exists)
            String q1 = "DELETE FROM transactions WHERE acc_no = ?";
            PreparedStatement ps1 = con.prepareStatement(q1);
            ps1.setInt(1, accNo);
            ps1.executeUpdate();

            // delete from balance
            String q2 = "DELETE FROM balance WHERE acc_no = ?";
            PreparedStatement ps2 = con.prepareStatement(q2);
            ps2.setInt(1, accNo);
            ps2.executeUpdate();

            // delete from users
            String q3 = "DELETE FROM users WHERE acc_no = ?";
            PreparedStatement ps3 = con.prepareStatement(q3);
            ps3.setInt(1, accNo);
            ps3.executeUpdate();

            // delete from credentials
            String q4 = "DELETE FROM credentials WHERE acc_no = ?";
            PreparedStatement ps4 = con.prepareStatement(q4);
            ps4.setInt(1, accNo);
            ps4.executeUpdate();

            con.close();

            System.out.println("\nAccount deleted successfully.");
            System.out.println("Thank you for using our service.\n");

            Main.menu(0); // or exit your program

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error while deleting account.");
        }
    }
}
