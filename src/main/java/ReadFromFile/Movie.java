package ReadFromFile;

import java.time.LocalDate;
import java.util.Arrays;

public class Movie {
    private final String title;
    private final String genre;
    private final int year;

    public Movie(String title, String genre, int year) {
        this.title = title;
        this.genre = genre;
        this.year = year;
    }
    public static Movie parse(String input){
        String[] tab = input.split(",");
        String title = tab[0];
        String genre = tab[1];
        System.out.println(Arrays.toString(tab));
        int year = Integer.parseInt(tab[7]);
        return new Movie(title,genre,year);
    }


    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getYear() {
        return year;
    }
}
