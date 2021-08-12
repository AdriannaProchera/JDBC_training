package ReadFromFile;

import ReadFromFile.MovieDAO;
import ReadFromFile.MovieDAOImpl;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class MainMovieFileReader {
    private static final String URL = "jdbc:mysql://localhost:3306/test_base";
    private static final String USER = "root";
    private static final String PASSWORD = "raskolnikov";
    private static final String FILENAME_PATH = "/Users/adriannaprochera/Downloads/moves.csv";

    public static void main(String[] args) {
        try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)){
            MovieDAO movie = new MovieDAOImpl(conn);
            List<Movie> resultMovies = FileReader.reader(FILENAME_PATH);
            movie.createTable();
            movie.createMovie(resultMovies);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
