package ReadFromFile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

    public static BufferedReader bufferedReader;
    public static List<Movie> reader(String filename){
         List<Movie> movies = new ArrayList<>();
        try{
            String str;
            bufferedReader = new BufferedReader(new java.io.FileReader(filename));
            while((str = bufferedReader.readLine()) != null){
                if(str.contains("Film,Genre,Lead Studio,Audience score %,Profitability,Rotten Tomatoes %,Worldwide Gross,Year")){
                    continue;
                } else {
                    Movie movie = Movie.parse(str);
                    movies.add(movie);
                }
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movies;
    }
}

