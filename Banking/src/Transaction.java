import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Transaction {

    Scanner sc = new Scanner(System.in);

    void transactionFun(int accNo) {
        System.out.println("Receiver's Account Number: ");
        int rAccNo = sc.nextInt();
        sc.nextLine();

        System.out.println("Amount: ");
        int tAmount = sc.nextInt();
        sc.nextLine();

        System.out.println("Remarks: ");
        String tRemarks = sc.nextLine();

        System.out.println("\n");

        allTransaction(accNo, rAccNo, tAmount, tRemarks);
    }

    void allTransaction(int accNo, int rAccNo, int tAmount, String tRemarks) {

        try {
            if (!receiverExists(rAccNo)) {
                System.out.println("Incorrect Account Number!...Try Again");
                Main.menu(accNo);
            }

            if (!senderHasBalance(accNo, tAmount)) {
                System.out.println("Insufficient Balance!.....Try Again");
                Main.menu(accNo);
            }

            doTransfer(accNo, rAccNo, tAmount);
            writeTransaction(accNo, rAccNo, tAmount, tRemarks);

            System.out.println("Transaction Successful!");
            System.out.println("Press Enter to continue...");
            sc.nextLine();

            Main.menu(accNo);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error processing transaction.");
        }
    }

    // CHECK RECEIVER EXISTS
    boolean receiverExists(int rAccNo) throws Exception {
        Connection con = db.DBConnection.getConnection();
        String sql = "SELECT acc_no FROM balance WHERE acc_no = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, rAccNo);

        ResultSet rs = ps.executeQuery();
        boolean exists = rs.next();

        con.close();
        return exists;
    }

    // CHECK SENDER BALANCE
    boolean senderHasBalance(int accNo, int tAmount) throws Exception {
        Connection con = db.DBConnection.getConnection();
        String sql = "SELECT balance FROM balance WHERE acc_no = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, accNo);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int bal = rs.getInt("balance");
            con.close();
            return tAmount <= bal;
        }

        con.close();
        return false;
    }

    // ACTUAL TRANSFER
    void doTransfer(int accNo, int rAccNo, int tAmount) throws Exception {
        Connection con = db.DBConnection.getConnection();

        // Deduct from sender
        String q1 = "UPDATE balance SET balance = balance - ? WHERE acc_no = ?";
        PreparedStatement ps1 = con.prepareStatement(q1);
        ps1.setInt(1, tAmount);
        ps1.setInt(2, accNo);
        ps1.executeUpdate();

        // Add to receiver
        String q2 = "UPDATE balance SET balance = balance + ? WHERE acc_no = ?";
        PreparedStatement ps2 = con.prepareStatement(q2);
        ps2.setInt(1, tAmount);
        ps2.setInt(2, rAccNo);
        ps2.executeUpdate();

        con.close();
    }

    // WRITE DEBIT + CREDIT IN TRANSACTIONS TABLE
    void writeTransaction(int accNo, int rAccNo, int tAmount, String tRemarks) throws Exception {
        debitEntry(accNo, rAccNo, tAmount, tRemarks);
        creditEntry(accNo, rAccNo, tAmount, tRemarks);
    }

    void debitEntry(int accNo, int rAccNo, int tAmount, String tRemarks) throws Exception {
        Connection con = db.DBConnection.getConnection();

        String description = "Transfer to " + rAccNo;
        String type = "Debit";
        String date = java.time.LocalDate.now().toString();
        LocalDateTime now = LocalDateTime.now();
        String time = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        String sql = "INSERT INTO transactions (acc_no, description, type, amount, remarks, date, time) VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, accNo);
        ps.setString(2, description);
        ps.setString(3, type);
        ps.setInt(4, tAmount);
        ps.setString(5, tRemarks);
        ps.setString(6, date);
        ps.setString(7, time);

        ps.executeUpdate();
        con.close();
    }

    void creditEntry(int accNo, int rAccNo, int tAmount, String tRemarks) throws Exception {
        Connection con = db.DBConnection.getConnection();

        String description = "Transfer from " + accNo;
        String type = "Credit";
        String date = java.time.LocalDate.now().toString();
        LocalDateTime now = LocalDateTime.now();
        String time = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        String sql = "INSERT INTO transactions (acc_no, description, type, amount, remarks, date, time) VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, rAccNo);
        ps.setString(2, description);
        ps.setString(3, type);
        ps.setInt(4, tAmount);
        ps.setString(5, tRemarks);
        ps.setString(6, date);
        ps.setString(7, time);

        ps.executeUpdate();
        con.close();
    }
}
