package ReadFromFile;

import ReadFromFile.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieDAO {
        void createTable();
        void deleteTable();
        void createMovie(List<Movie> movies);
}
