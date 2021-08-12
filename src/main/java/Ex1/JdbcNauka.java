package Ex1;

import java.sql.*;

public class JdbcNauka {
    public static void main(String[] args) {
        try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels", "root", "")){
            String query = "SELECT * FROM products;";
            //PreparedStatement stmt = conn.prepareStatement(query);
            //stmt.execute();
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);
            while(result.next()){
                System.out.println(result.getString(1)+" "+
                        result.getString("productName"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
