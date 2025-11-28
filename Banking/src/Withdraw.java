import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Withdraw {

    Scanner sc = new Scanner(System.in);

    public void withdrawFun(int accNo) {
        System.out.print("Enter amount to withdraw: ");
        int amount = sc.nextInt();
        sc.nextLine();

        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return;
        }

        try {
            Connection con = db.DBConnection.getConnection();

            // check balance
            String check = "SELECT balance FROM balance WHERE acc_no = ?";
            PreparedStatement psCheck = con.prepareStatement(check);
            psCheck.setInt(1, accNo);
            ResultSet rs = psCheck.executeQuery();

            if (!rs.next()) {
                System.out.println("Account not found.");
                return;
            }

            int bal = rs.getInt("balance");
            if (bal < amount) {
                System.out.println("Insufficient balance!");
                return;
            }

            // update balance
            String update = "UPDATE balance SET balance = balance - ? WHERE acc_no = ?";
            PreparedStatement ps = con.prepareStatement(update);
            ps.setInt(1, amount);
            ps.setInt(2, accNo);
            ps.executeUpdate();

            // statement entry
            String desc = "Cash Withdrawal";
            recordTransaction(accNo, desc, "Debit", amount, "Withdraw");

            con.close();

            System.out.println("Withdrawal successful!");
            Main.menu(accNo);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error while withdrawing.");
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
