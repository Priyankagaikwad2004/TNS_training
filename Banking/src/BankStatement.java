import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class BankStatement {

    Scanner sc = new Scanner(System.in);

    void bankStatementFun(int accNo) throws IOException {

    	System.out.println("\n");
        System.out.println("                           | Bank Statement |");
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.printf("%-21s | %-6s | %-6s | %-7s | %-10s | %-8s%n", "Description", "Type", "Amount", "Remarks", "Date", "Time");
        System.out.println("-----------------------------------------------------------------------------------");
        
        try {
            Connection con = db.DBConnection.getConnection();

            if (con == null) {
                System.out.println("Database error. Try again.");
                exit(accNo);
                return;
            }

            String query = "SELECT description, type, amount, remarks, date, time "
                         + "FROM transactions WHERE acc_no = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, accNo);

            ResultSet rs = ps.executeQuery();

            boolean recordFound = false;

            while (rs.next()) {
                recordFound = true;

                String description = rs.getString("description");
                String type = rs.getString("type");
                String amount = "$" + rs.getInt("amount");
                String remarks = rs.getString("remarks");
                String date = rs.getString("date");
                String time = rs.getString("time");

                System.out.printf(
                    "%-21s | %-6s | %-6s | %-7s | %-10s | %-8s%n",
                    description, type, amount, remarks, date, time
                );
            }

            con.close();

            if (!recordFound) {
                System.out.println("No transactions found!");
            }

            System.out.println("-----------------------------------------------------------------------------------");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading statement.");
        }

        exit(accNo);
    }

    void exit(int accNo) throws IOException {
        System.out.println("\nPress Enter key to continue...");
        sc.nextLine();
        Main.menu(accNo);
    }
}
