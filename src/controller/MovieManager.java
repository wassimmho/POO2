package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JOptionPane;
import model.*;

public class MovieManager {
    
    public static ArrayList<Movie> movies;
    public static int[] PromosId;

    public MovieManager(){
        movies = new ArrayList<>();
        loadMoviesFromDatabase();
    }

    private void loadMoviesFromDatabase() {
        String sql = "SELECT * FROM movies";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Movie movie = new Movie(
                    rs.getInt("MovieID"),
                    rs.getString("Title"),
                    Movie.MovieGenre.valueOf(rs.getString("Genre")),
                    0, // Duration not in DB
                    rs.getString("Description"),
                    rs.getString("Director"),
                    "", // Cast not in DB
                    rs.getDate("ReleaseDate").toLocalDate(),
                    rs.getFloat("Rating"),
                    Movie.MovieAgeRating.valueOf(rs.getString("AgeRating")),
                    Movie.Language.VOSTFR,
                    rs.getString("ImagePath")
                );
                movies.add(movie);
            }
            sortMoviesByName();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading movies from database: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void sortMoviesByName() {
        Collections.sort(movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie m1, Movie m2) {
                return m1.Title.compareToIgnoreCase(m2.Title);
            }
        });
    }
    
    public void AddMovie(Movie movie) {
        String sql = "INSERT INTO movies (Title, Genre, Description, Rating, AgeRating, ImagePath, TrailerURL, ReleaseDate, Director) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, movie.Title);
            pstmt.setString(2, movie.Genre.toString());
            pstmt.setString(3, movie.Description);
            pstmt.setFloat(4, movie.Rating);
            pstmt.setString(5, movie.AgeRating.toString());
            pstmt.setString(6, movie.ImagePath2);
            pstmt.setString(7, movie.Trailer != null ? movie.Trailer : "");
            pstmt.setDate(8, java.sql.Date.valueOf(movie.ReleaseDate));
            pstmt.setString(9, movie.Director);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                // Get the generated MovieID
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        movie.id = generatedKeys.getInt(1);
                    }
                }
                movies.add(movie);
                sortMoviesByName();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error adding movie to database: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void RemoveMovie(Movie movie) {
        String sql = "DELETE FROM movies WHERE MovieID = ?";
        
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, movie.id);
            int rowsDeleted = pstmt.executeUpdate();
            
            if (rowsDeleted > 0) {
                movies.remove(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error removing movie from database: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void DisplayMovie() {
        for (Movie movie : movies) {
            System.out.println("---------------------------------------------");
            System.out.println("ID: " + movie.id);
            System.out.println("Title: " + movie.Title);
            System.out.println("Genre: " + movie.Genre);
            System.out.println("Duration: " + movie.Duration + " minutes");
            System.out.println("Release Date: " + movie.ReleaseDate);
            System.out.println("Rating: " + movie.Rating);
            System.out.println("Age Rating: " + movie.AgeRating);
            System.out.println("Description: " + movie.Description);
            System.out.println("Director: " + movie.Director);
            System.out.println("Cast: " + movie.Cast);
            System.out.println("Image Path: " + movie.ImagePath2);
            System.out.println("---------------------------------------------");
        }
    }

    public void updateMovie(Movie movie) {
        String sql = "UPDATE movies SET Title = ?, Genre = ?, Description = ?, Rating = ?, " +
                    "AgeRating = ?, ImagePath = ?, ReleaseDate = ?, Director = ? WHERE MovieID = ?";
        
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, movie.Title);
            pstmt.setString(2, movie.Genre.toString());
            pstmt.setString(3, movie.Description);
            pstmt.setFloat(4, movie.Rating);
            pstmt.setString(5, movie.AgeRating.toString());
            pstmt.setString(6, movie.ImagePath2);
            pstmt.setDate(7, java.sql.Date.valueOf(movie.ReleaseDate));
            pstmt.setString(8, movie.Director);
            pstmt.setInt(9, movie.id);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                // Update the movie in the ArrayList
                for (int i = 0; i < movies.size(); i++) {
                    if (movies.get(i).id == movie.id) {
                        movies.set(i, movie);
                        break;
                    }
                }
                sortMoviesByName();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating movie in database: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static int numberofmovies() {
        String sql = "SELECT COUNT(*) FROM movies";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean addMovieToDatabase(String title, Movie.MovieGenre genre, String description, float rating, 
                                           Movie.MovieAgeRating ageRating, String imagePath, String trailerURL, 
                                           LocalDate releaseDate, String director) {
        Movie movie = new Movie(0, title, genre, 0, description, director, "", releaseDate, rating, ageRating, Movie.Language.VOSTFR, imagePath);

        if (isDuplicate(movie)) {
            JOptionPane.showMessageDialog(null, "Movie already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String sql = "INSERT INTO movies (Title, Genre, Description, Rating, AgeRating, ImagePath, TrailerURL, ReleaseDate, Director) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, title);
            pstmt.setString(2, genre.toString());
            pstmt.setString(3, description);
            pstmt.setFloat(4, rating);
            pstmt.setString(5, ageRating.toString());
            pstmt.setString(6, imagePath);
            pstmt.setString(7, trailerURL != null ? trailerURL : "");
            pstmt.setDate(8, java.sql.Date.valueOf(releaseDate));
            pstmt.setString(9, director);

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Movie added successfully!");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error adding movie to the database!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return false;
    }
    

    public static void removeMovie(int movieID) {
        String sql = "DELETE FROM movies WHERE MovieID = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, movieID);

            int rowsDeleted = pstmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Movie deleted successfully!");
            } else {
                System.out.println("Movie not found. No deletion performed.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isDuplicate(Movie movie) {
        String sql = "SELECT COUNT(*) FROM movies WHERE Title = ? AND Director = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, movie.Title);
            pstmt.setString(2, movie.Director);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Method to update only the title
    public static void updateMovieTitle(int movieID, String title) {
        String sql = "UPDATE movies SET Title = ? WHERE MovieID = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, title);
            pstmt.setInt(2, movieID);

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Movie title updated successfully!");
            } else {
                System.out.println("Movie not found. No update performed.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update only the genre
    public static void updateMovieGenre(int movieID, Movie.MovieGenre genre) {
        String sql = "UPDATE movies SET Genre = ? WHERE MovieID = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, genre.toString());
            pstmt.setInt(2, movieID);

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Movie genre updated successfully!");
            } else {
                System.out.println("Movie not found. No update performed.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update only the description
    public static void updateMovieDescription(int movieID, String description) {
        String sql = "UPDATE movies SET Description = ? WHERE MovieID = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, description);
            pstmt.setInt(2, movieID);

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Movie description updated successfully!");
            } else {
                System.out.println("Movie not found. No update performed.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update only the rating
    public static void updateMovieRating(int movieID, float rating) {
        String sql = "UPDATE movies SET Rating = ? WHERE MovieID = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setFloat(1, rating);
            pstmt.setInt(2, movieID);

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Movie rating updated successfully!");
            } else {
                System.out.println("Movie not found. No update performed.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update only the age rating
    public static void updateMovieAgeRating(int movieID, Movie.MovieAgeRating ageRating) {
        String sql = "UPDATE movies SET AgeRating = ? WHERE MovieID = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ageRating.toString());
            pstmt.setInt(2, movieID);

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Movie age rating updated successfully!");
            } else {
                System.out.println("Movie not found. No update performed.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update only the image path
    public static void updateMovieImagePath(int movieID, String imagePath) {
        String sql = "UPDATE movies SET ImagePath = ? WHERE MovieID = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, imagePath);
            pstmt.setInt(2, movieID);

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Movie image path updated successfully!");
            } else {
                System.out.println("Movie not found. No update performed.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update only the trailer URL
    public static void updateMovieTrailerURL(int movieID, String trailerURL) {
        String sql = "UPDATE movies SET TrailerURL = ? WHERE MovieID = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, trailerURL);
            pstmt.setInt(2, movieID);

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Movie trailer URL updated successfully!");
            } else {
                System.out.println("Movie not found. No update performed.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update only the release date
    public static void updateMovieReleaseDate(int movieID, LocalDate releaseDate) {
        String sql = "UPDATE movies SET ReleaseDate = ? WHERE MovieID = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, java.sql.Date.valueOf(releaseDate));
            pstmt.setInt(2, movieID);

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Movie release date updated successfully!");
            } else {
                System.out.println("Movie not found. No update performed.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update only the director
    public static void updateMovieDirector(int movieID, String director) {
        String sql = "UPDATE movies SET Director = ? WHERE MovieID = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, director);
            pstmt.setInt(2, movieID);

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Movie director updated successfully!");
            } else {
                System.out.println("Movie not found. No update performed.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet findMoviesbyTitle(String title) {
        String sql = "SELECT * FROM movies WHERE Title = ? ";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, title);

            return pstmt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ResultSet findMoviesbyGenre(String genre) {
        String sql = "SELECT * FROM movies WHERE Genre = ? ";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, genre);

            return pstmt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ResultSet findMoviesbyDirector(String director) {
        String sql = "SELECT * FROM movies WHERE Director = ? ";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, director);

            return pstmt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getMovieTitle(int movieID) throws SQLException {
        String sql = "SELECT Title FROM movies WHERE MovieID = ?";
        try(Connection conn = DatabaseConnection.connect();
            PreparedStatement pstm = conn.prepareStatement(sql)){
            pstm.setInt(1, movieID);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                return rs.getString(1);
            }
        }
        return null;
    }

    public static String getMovieGenre(int movieID) throws SQLException {
        String sql = "SELECT Genre FROM movies WHERE MovieID = ?";
        try(Connection conn = DatabaseConnection.connect();
            PreparedStatement pstm = conn.prepareStatement(sql)){
            pstm.setInt(1, movieID);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                return rs.getString(1);
            }
        }
        return null;
    }

    public static String getMovieDescription(int movieID) throws SQLException {
        String sql = "SELECT Description FROM movies WHERE MovieID = ?";
        try(Connection conn = DatabaseConnection.connect();
            PreparedStatement pstm = conn.prepareStatement(sql)){
            pstm.setInt(1, movieID);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                return rs.getString(1);
            }
        }
        return null;
    }

    public static float getMovieRating(int movieID) throws SQLException {
        String sql = "SELECT Rating FROM movies WHERE MovieID = ?";
        try(Connection conn = DatabaseConnection.connect();
            PreparedStatement pstm = conn.prepareStatement(sql)){
            pstm.setInt(1, movieID);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                return rs.getFloat(1);
            }
        }
        return 0;
    }

    public static String getMovieAgeRating(int movieID) throws SQLException {
        String sql = "SELECT AgeRating FROM movies WHERE MovieID = ?";
        try(Connection conn = DatabaseConnection.connect();
            PreparedStatement pstm = conn.prepareStatement(sql)){
            pstm.setInt(1, movieID);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                return rs.getString(1);
            }
        }
        return null;
    }

    public static String getMovieImagePath(int movieID) throws SQLException {
        String sql = "SELECT ImagePath FROM movies WHERE MovieID = ?";
        try(Connection conn = DatabaseConnection.connect();
            PreparedStatement pstm = conn.prepareStatement(sql)){
            pstm.setInt(1, movieID);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                return rs.getString(1);
            }
        }
        return null;
    }

    public static String getMovieTrailerURL(int movieID) throws SQLException {
        String sql = "SELECT TrailerURL FROM movies WHERE MovieID = ?";
        try(Connection conn = DatabaseConnection.connect();
            PreparedStatement pstm = conn.prepareStatement(sql)){
            pstm.setInt(1, movieID);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                return rs.getString(1);
            }
        }
        return null;
    }

    public static LocalDate getMovieReleaseDate(int movieID) throws SQLException {
        String sql = "SELECT ReleaseDate FROM movies WHERE MovieID = ?";
        try(Connection conn = DatabaseConnection.connect();
            PreparedStatement pstm = conn.prepareStatement(sql)){
            pstm.setInt(1, movieID);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                return rs.getDate(1).toLocalDate();
            }
        }
        return null;
    }

    public static String getMovieDirector(int movieID) throws SQLException {
        String sql = "SELECT Director FROM movies WHERE MovieID = ?";
        try(Connection conn = DatabaseConnection.connect();
            PreparedStatement pstm = conn.prepareStatement(sql)){
            pstm.setInt(1, movieID);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                return rs.getString(1);
            }
        }
        return null;
    }

    public void reloadMoviesFromDatabase() {
        movies.clear();
        String sql = "SELECT * FROM movies";
        try (Connection conn = DatabaseConnection.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Movie movie = new Movie(
                    rs.getInt("MovieID"),
                    rs.getString("Title"),
                    Movie.MovieGenre.valueOf(rs.getString("Genre")),
                    0, // Duration: add if you have it in DB
                    rs.getString("Description"),
                    rs.getString("Director"),
                    "", // Cast: add if you have it in DB
                    rs.getDate("ReleaseDate").toLocalDate(),
                    rs.getFloat("Rating"),
                    Movie.MovieAgeRating.valueOf(rs.getString("AgeRating")),
                    Movie.Language.VOSTFR, // Adjust if you store language
                    rs.getString("ImagePath")
                );
                movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //get the Titles of movies
    public static ArrayList<String> getAllMoviesNames() {
            String sql = "SELECT titles FROM movies";
            ArrayList<String> moviesNames = new ArrayList<>();

            try (Connection conn = DatabaseConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    moviesNames.add(rs.getString("titles"));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return moviesNames;
    }

    //get movie id by name
    public int getMovieIdByName(String title) {
        String sql = "SELECT MovieID FROM movies WHERE Title = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("MovieID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Not found
    }

    public int[] setRandomPromotion(){
        //choose two random numbers 
        int random1 = (int) (Math.random() * movies.size());
        int random2 = (int) (Math.random() * movies.size());
        //set the promotion to true for the two random movies

        //return the two numbers
        return new int[]{random1, random2};
    }

    public void setPromotion(int Id1, int Id2){
        this.PromosId = new int[]{Id1, Id2};
    }

    public Movie GetBestRated() {
        Movie bestRated = movies.get(0);
        for (Movie movie : movies) {
            if (movie.Rating > bestRated.Rating) {
                bestRated = movie;
            }
        }
        return bestRated;
    }

    public int GetMovieByName(String name) {
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).Title.equals(name)) {
                return i;
            }
        }
        return -1; // Movie not found
    }

    
}

