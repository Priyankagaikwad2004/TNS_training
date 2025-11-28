import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class ChangePassword {

    Scanner sc = new Scanner(System.in);

    public void changePasswordFun(int accNo) {
        System.out.print("Enter your current password: ");
        String oldPass = sc.nextLine();

        try {
            Connection con = db.DBConnection.getConnection();

            // fetch actual password
            String fetch = "SELECT password FROM credentials WHERE acc_no = ?";
            PreparedStatement psFetch = con.prepareStatement(fetch);
            psFetch.setInt(1, accNo);
            ResultSet rs = psFetch.executeQuery();

            if (!rs.next()) {
                System.out.println("Account not found.");
                return;
            }

            String actualPass = rs.getString("password");

            if (!actualPass.equals(oldPass)) {
                System.out.println("Incorrect current password!");
                return;
            }

            // enter new password
            System.out.print("Enter new password: ");
            String newPass = sc.nextLine();

            String update = "UPDATE credentials SET password = ? WHERE acc_no = ?";
            PreparedStatement ps = con.prepareStatement(update);
            ps.setString(1, newPass);
            ps.setInt(2, accNo);

            ps.executeUpdate();
            con.close();

            System.out.println("Password updated successfully!");
            Main.menu(accNo);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error changing password.");
        }
    }
}
