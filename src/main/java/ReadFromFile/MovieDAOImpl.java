package ReadFromFile;

import ReadFromFile.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class MovieDAOImpl implements MovieDAO{
    private final Connection conn;

    public MovieDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void createTable() {
        try(Statement stmt = conn.createStatement()) {
            String query = "CREATE TABLE IF NOT EXISTS Movie(\n" +
                    "id SERIAL,\n" +
                    "title varchar(255),\n" +
                    "genre varchar(255),\n" +
                    "year integer);";
            stmt.execute(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void deleteTable() {
        try(Statement stmt = conn.createStatement()){
            String query = "DROP TABLE Movie;";
            stmt.execute(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    @Override
    public void createMovie(List<Movie> movies) {
        String query = "INSERT INTO Movie (title, genre, year) VALUES (?, ?, ?);";

        try(PreparedStatement pstmt = conn.prepareStatement(query)){
            for(Movie element : movies){
                pstmt.setString(1, element.getTitle());
                pstmt.setString(2,element.getGenre());
                pstmt.setInt(3, element.getYear());
                pstmt.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

}
