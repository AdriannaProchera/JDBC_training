package Ex1;

import java.sql.*;

public class Zad1a {
        public static void main(String[] args) {
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels",
                    "root", "")) {
                String query = "create table if not exists movies(" +
                        "id INTEGER AUTO_INCREMENT Primary key, " +
                        "title VARCHAR(255), " +
                        "genre VARCHAR(255), " +
                        "yearOfRelease INTEGER)";
                String query2 = "insert into movies (title, genre, yearOfRelease) " +
                        "values (?, ?, ?);";
                String query3 = "Select * from movies";


                Statement stmt = conn.createStatement();
                PreparedStatement stmt2 = conn.prepareStatement(query2);
                stmt2.setString(1,"aaa");
                stmt2.setString(2, "horror");
                stmt2.setInt(3,1982);


                Integer result = stmt.executeUpdate(query);
                Integer result2 = stmt2.executeUpdate();
                ResultSet result3 = stmt.executeQuery(query3);
//            while(result.next()){
//                System.out.println(result.getString(1) + " " + result.getString("productName"));
//            }
                while (result3.next()) {
                    System.out.println(result3.getString(1) + " " + result3.getString("title"));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

