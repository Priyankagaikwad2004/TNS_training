import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class AccountDetails {
	void accountDetailsFun(int accNo) {
		
		Scanner sc = new Scanner(System.in);
        try {
        	Connection con = db.DBConnection.getConnection();

            if (con == null) {
                System.out.println("Database connection failed");
                return;
            }

            String sql = "SELECT * FROM users WHERE acc_no = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, accNo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Account Details: ");
                System.out.println("┌────────────────────────────────┐");
                System.out.println("  Full Name: " + rs.getString("first_name") + " " + rs.getString("last_name"));
                System.out.println("  Account Number: " + rs.getInt("acc_no"));
                System.out.println("  Gender: " + rs.getString("gender"));
                System.out.println("  Address: " + rs.getString("address"));
                System.out.println("  Date of Birth: " + rs.getString("dob"));
                System.out.println("  Phone number: " + rs.getString("phone"));
                System.out.println("  Email: " + rs.getString("email"));
                System.out.println("  Citizenship : " + rs.getString("citizenship"));
                System.out.println("└────────────────────────────────┘");
            } else {
                System.out.println("No account found for: " + accNo);
            }

            con.close();

            System.out.println("\nPress Enter key to continue...");
            sc.nextLine();
            Main.menu(accNo);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error while fetching account details");
        }
    }
}
