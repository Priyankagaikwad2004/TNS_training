import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;
import java.util.Scanner;

public class Creation {

    Scanner sc = new Scanner(System.in);

    void createAccFun() {
        try {
            int accNo = accNoCreation();
            String[] accLineInfo = getUserInfoFromUser();

            insertCredentials(accNo, accLineInfo[8]);
            insertInitialBalance(accNo);
            insertUserInfo(accNo, accLineInfo);

            System.out.println("\nAccount created successfully!\n");
            System.out.println("Your account number is: " + accNo);
            System.out.println("Your password is: " + accLineInfo[8] + "\n");

            Main.menu(accNo);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to create account.");
        }
    }

    // USER INPUT
    String[] getUserInfoFromUser() {
        String[] data = new String[9];

        System.out.println("Enter your Name: ");
        String fullName = sc.nextLine().trim();
        String[] nameArr = fullName.split(" ");

        if (nameArr.length != 2) {
            System.out.println("Please enter first and last name.");
            return getUserInfoFromUser();
        }

        data[0] = nameArr[0];
        data[1] = nameArr[1];

        System.out.println("Enter your Date of Birth (YYYY-MM-DD): ");
        data[2] = sc.nextLine();
        System.out.println("Enter your Gender: ");
        data[3] = sc.nextLine();
        System.out.println("Enter your Address: ");
        data[4] = sc.nextLine();
        System.out.println("Enter your Phone Number: ");
        data[5] = sc.nextLine();
        System.out.println("Enter your Email: ");
        data[6] = sc.nextLine();
        System.out.println("Enter your Citizenship Number: ");
        data[7] = sc.nextLine();
        System.out.println("Create a Password for your Account: ");
        data[8] = sc.nextLine();

        return data;
    }

    // ACCOUNT NUMBER CREATION
    int accNoCreation() throws Exception {
        Connection con = db.DBConnection.getConnection();

        String sql = "SELECT acc_no FROM credentials ORDER BY acc_no DESC LIMIT 1";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getInt("acc_no") + 1;
        }

        return 1; // First account
    }

    // INSERT CREDENTIALS
    void insertCredentials(int accNo, String password) throws Exception {
        Connection con = db.DBConnection.getConnection();

        String sql = "INSERT INTO credentials VALUES (?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, accNo);
        ps.setString(2, password);

        ps.executeUpdate();
    }

    // INSERT INITIAL BALANCE
    void insertInitialBalance(int accNo) throws Exception {
        Connection con = db.DBConnection.getConnection();

        String sql = "INSERT INTO balance VALUES (?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, accNo);
        ps.setInt(2, 00); // initial balance

        ps.executeUpdate();
    }

    // INSERT USER DETAILS
    void insertUserInfo(int accNo, String[] info) throws Exception {
        Connection con = db.DBConnection.getConnection();

        String sql = "INSERT INTO users VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, accNo);
        ps.setString(2, info[0]);
        ps.setString(3, info[1]);
        ps.setString(4, info[2]);
        ps.setString(5, info[3]);
        ps.setString(6, info[4]);
        ps.setString(7, info[5]);
        ps.setString(8, info[6]);
        ps.setString(9, info[7]);

        ps.executeUpdate();
    }
}
