package Ex2;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieDAOImpl implements MovieDAO{

    private final Connection conn;
    public MovieDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void createTable() {
        try(Statement stmt = conn.createStatement()) {
            String query = "CREATE TABLE IF NOT EXISTS Movie(\n" +
                    "id integer,\n" +
                    "title varchar(255),\n" +
                    "genre varchar(255),\n" +
                    "yearOfRelease integer);";
            stmt.execute(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void deleteTable() {
        try(Statement stmt = conn.createStatement()){
            String query = "DROP TABLE Movie";
            stmt.execute(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void createMovie(Movie movie) {
        String query = "INSERT INTO Movie (id,title, genre, yearOfRelease) VALUES (? ,?, ?, ?)";

        try(PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setInt(1, movie.getId());
            pstmt.setString(2, movie.getTitle());
            pstmt.setString(3, movie.getGenre());
            pstmt.setInt(4, movie.getYearOfRelease());
            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void deleteMovie(int id) {
        String query = "DELETE FROM Movie Where id = ?;";
        try(PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void updateMoviesTitle(int id, String newTitle) {
        String query = "UPDATE movie SET title = ? WHERE id = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setString(1, newTitle);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void findMovieById(int id) {
        String query = "SELECT*FROM Movie where id = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setInt(1,id);
            ResultSet result = pstmt.executeQuery();
            while(result.next()){
                System.out.println(result.getInt(1)+ " "+ result.getString(2)+" "+
                        result.getString(3)+" "+ result.getInt(4));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Optional<Movie> findMovieByIdOptional(int id) {
        String query = "SELECT*FROM Movie where id = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setInt(1, id);
            final boolean result = pstmt.execute();
            if(result){
                ResultSet finder = pstmt.getResultSet();
                if(finder.next()){

                    return Optional.of(new Movie(finder.getInt(1), finder.getString(2),
                            finder.getString(3), finder.getInt(4)));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
           throw new IllegalActionException(e);
        }
    }

    @Override
    public List<Movie> findAll() {
        List <Movie> moviesList = new ArrayList<>();
        try(Statement stmt = conn.createStatement()){
            String query = "SELECT * FROM Movie";
            ResultSet result = stmt.executeQuery(query);
            while(result.next()){
                System.out.println(result.getInt("id")+ " "+ result.getString("title")+" "+
                        result.getString("genre")+" "+ result.getInt("yearOfRelease"));

                moviesList.add(new Movie(result.getInt(1),result.getString(2), result.getString(3),
                        result.getInt(4)));
            }
            return moviesList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return moviesList;
    }
}
