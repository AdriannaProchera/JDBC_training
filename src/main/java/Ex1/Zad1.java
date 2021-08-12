package Ex1;

import java.sql.*;

public class Zad1 {
    private static final String URL = "jdbc:mysql://localhost:3306/test_base";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static Statement stmt = null;
    private static PreparedStatement pstmt = null;

    //execute -> create, drop
    //executeUpdate -> update, delete, insert
    //executeQuery -> pobieranie danych z bazy np.SELECT

    public static void main(String[] args) {
        try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "CREATE TABLE IF NOT EXISTS Movies(\n" +
                    "id SERIAL PRIMARY KEY,\n" +
                    "title varchar(255),\n" +
                    "genre varchar(255),\n" +
                    "yearOfRelease integer);";
            String query2 = "INSERT INTO Movies (title, genre, yearOfRelease) VALUES( 'aaa','horror',2021),('bbb','dramat',2020),(?,?,?);";
            String query3 = "UPDATE Movies SET yearOfRelease = 1996 WHERE id = 2;";
            String query4 = "DELETE FROM Movies Where id = 1;";
            String query5 = "SELECT*FROM Movies;";

            stmt = conn.createStatement();
            stmt.execute(query);

            //np gdy chcemy pobrac dane z pliku to preapred statement
            pstmt = conn.prepareStatement(query2);
            pstmt.setString(1, "ccc");
            pstmt.setString(2, "horror");
            pstmt.setInt(3, 2019);
            pstmt.executeUpdate();

            stmt.executeUpdate(query3);
            stmt.executeUpdate(query4);
            ResultSet result = stmt.executeQuery(query5);
            while(result.next()){
                System.out.println(result.getString(1)+" "+
                        result.getString("title") + " " +
                        result.getString("genre")+" "+
                        result.getInt("yearOfRelease"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if(stmt != null){
                try{
                    stmt.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if(pstmt != null){
                try{
                    pstmt.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
}
