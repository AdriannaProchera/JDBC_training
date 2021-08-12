package Ex2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainMovie {

    private static final String URL = "jdbc:mysql://localhost:3306/test_base";
    private static final String USER = "root";
    private static final String PASSWORD = "";
//jdbc driver manager w niewidoczny sposob zapisany w javie na bazy danych
    public static void main(String[] args) throws SQLException {
        try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)){
            MovieDAO movie = new MovieDAOImpl(conn);
            movie.createTable();
            movie.createMovie(new Movie(1,"aaa", "horror", 1998));
            movie.createMovie(new Movie(2, "bbb", "Fantasy", 1999));
            movie.createMovie(new Movie(3, "ccc", "horror", 2000));
            movie.updateMoviesTitle(1, "XXXX");
            movie.deleteMovie(2);
            movie.findAll();
            System.out.println("Find movie by id:");
            //movie.findMovieById(3);
            movie.findMovieById(4);
            System.out.println(movie.findMovieByIdOptional(3));
            //movie.deleteTable();
        }
    }
}
