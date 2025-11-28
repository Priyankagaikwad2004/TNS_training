import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Login {

    Scanner sc = new Scanner(System.in);

    void loginFun() {
        System.out.print("Enter your Account Number: ");
        int accNo = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter your Password: ");
        String pass = sc.nextLine();

        loginAuth(accNo, pass);
    }

    void loginAuth(int accNo, String pass) {

        try {
            Connection con = db.DBConnection.getConnection();

            if (con == null) {
                System.out.println("Database error.");
                return;
            }

            // Step 1: Check if account exists
            String checkQuery = "SELECT password FROM credentials WHERE acc_no = ?";
            PreparedStatement ps = con.prepareStatement(checkQuery);
            ps.setInt(1, accNo);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                System.out.println("\nAccount doesn't exist!");
                System.out.println("Please enter again.\n");
                loginFun();
                return;
            }

            // Step 2: Compare password
            String correctPassword = rs.getString("password");

            if (correctPassword.equals(pass)) {
                System.out.println("\nLogin Successful!!\n");
                Main.menu(accNo);
            } else {
                System.out.println("\nIncorrect Password!");
                System.out.println("Please enter again.\n");
                loginFun();
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error while logging in.");
        }
    }
}
