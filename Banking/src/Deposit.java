import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Deposit {

    Scanner sc = new Scanner(System.in);

    public void depositFun(int accNo) {
        System.out.print("Enter amount to deposit: ");
        int amount = sc.nextInt();
        sc.nextLine();

        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return;
        }

        try {
            Connection con = db.DBConnection.getConnection();

            String update = "UPDATE balance SET balance = balance + ? WHERE acc_no = ?";
            PreparedStatement ps = con.prepareStatement(update);
            ps.setInt(1, amount);
            ps.setInt(2, accNo);
            ps.executeUpdate();

            // record statement
            String desc = "Cash Deposit";
            recordTransaction(accNo, desc, "Credit", amount, "Deposit");

            con.close();

            System.out.println("Amount deposited successfully!");
            Main.menu(accNo);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error while depositing.");
        }
    }

    private void recordTransaction(int accNo, String desc, String type, int amount, String remarks) throws Exception {
        Connection con = db.DBConnection.getConnection();

        String date = java.time.LocalDate.now().toString();
        String time = java.time.LocalTime.now().toString();

        String sql = "INSERT INTO transactions (acc_no, description, type, amount, remarks, date, time) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, accNo);
        ps.setString(2, desc);
        ps.setString(3, type);
        ps.setInt(4, amount);
        ps.setString(5, remarks);
        ps.setString(6, date);
        ps.setString(7, time);

        ps.executeUpdate();
        con.close();
    }
}
