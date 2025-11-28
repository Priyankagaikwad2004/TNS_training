import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class BalanceInquiry {

    Scanner sc = new Scanner(System.in);   

    void balanceInquiryFun(int accNo) {
        try {
            Connection con = db.DBConnection.getConnection();

            if (con == null) {
                System.out.println("We're having some issues. Try again!");
                return;
            }

            String query = "SELECT balance FROM balance WHERE acc_no = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, accNo);

            ResultSet rs = ps.executeQuery();

            int accBalance = -1;

            if (rs.next()) {
                accBalance = rs.getInt("balance");
            }

            con.close();

            if (accBalance == -1) {
                System.out.println("We're having some issues, Try Again!");
                System.exit(0);
            } else {
                System.out.println("┌───────────────────────────────┐");
                System.out.println("  Your current balance is $" + accBalance);
                System.out.println("└───────────────────────────────┘");

                System.out.println("Press Enter key to continue...");
                sc.nextLine();

                Main.menu(accNo);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something went wrong.");
        }
    }
}
