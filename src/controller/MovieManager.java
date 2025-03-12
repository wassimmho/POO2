package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import model.*;

public class MovieManager {
    
    public static ArrayList<Movie> movies;

    public MovieManager(){
        
        movies = new ArrayList<>();

        
        // Create movies and broadcast
        Movie Movie1 = new Movie(0, "Avengers: Endgame", Movie.MovieGenre.Action, 3.02f,
                "After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos' actions and restore balance to the universe.",
                "Anthony Russo, Joe Russo", "Robert Downey Jr., Chris Evans, Mark Ruffalo",
                LocalDate.of(2019, 4, 26),
                8.4f, Movie.MovieAgeRating.PG13, Movie.Language.VOSTFR, "Avengers_Endgame.png");

        Movie Movie2 = new Movie(1, "Joker", Movie.MovieGenre.Crime, 2.02f,
                "In Gotham City, mentally troubled comedian Arthur Fleck is disregarded and mistreated by society. He then embarks on a downward spiral of revolution and bloody crime. This path brings him face-to-face with his alter-ego: the Joker.",
                "Todd Phillips", "Joaquin Phoenix, Robert De Niro, Zazie Beetz",
                LocalDate.of(2019, 10, 4),
                8.5f, Movie.MovieAgeRating.PG18, Movie.Language.VOSTFR, "Joker.png");

        Movie Movie3 = new Movie(2, "Parasite", Movie.MovieGenre.Thriller, 2.12f,
                "Greed and class discrimination threaten the newly formed symbiotic relationship between the wealthy Park family and the destitute Kim clan.",
                "Bong Joon Ho", "Kang-ho Song, Sun-kyun Lee, Yeo-jeong Jo",
                LocalDate.of(2019, 5, 30),
                8.6f, Movie.MovieAgeRating.PG16, Movie.Language.VOSTFR, "Parasite.png");

        Movie Movie4 = new Movie(3, "Blair Witch", Movie.MovieGenre.Horror, 1.29f,
                "After discovering a video showing what he believes to be his vanished sister Heather, James and a group of friends head to the forest believed to be inhabited by the Blair Witch.",
                "Adam Wingard", "James Allen McCune, Callie Hernandez, Corbin Reid",
                LocalDate.of(2016, 9, 16),
                5.0f, Movie.MovieAgeRating.PG18, Movie.Language.VOSTFR, "Blair_Witch.png");

        Movie Movie5 = new Movie(4, "Batman: Arkham Knight", Movie.MovieGenre.Action, 1.75f,
                "Batman faces the ultimate threat against the city he is sworn to protect. The Scarecrow returns to unite an impressive roster of super villains, including Penguin, Two-Face, and Harley Quinn, to destroy The Dark Knight forever.",
                "Sefton Hill", "Kevin Conroy, John Noble, Jonathan Banks",
                LocalDate.of(2015, 6, 23),
                9.0f, Movie.MovieAgeRating.PG16, Movie.Language.VOSTFR, "Batman_Arkham_Knight.png");

        Movie Movie6 = new Movie(5, "The Gorge", Movie.MovieGenre.Thriller, 2.10f,
                "A thrilling story set in a remote gorge where a group of friends must survive against all odds.",
                "Scott Derrickson", "Ethan Hawke, Jeremy Renner, Elizabeth Olsen",
                LocalDate.of(2023, 10, 13),
                7.5f, Movie.MovieAgeRating.PG16, Movie.Language.VOSTFR, "The_Gorge.png");

        Movie Movie7 = new Movie(6, "The Batman", Movie.MovieGenre.Action, 2.55f,
                "When a sadistic serial killer begins murdering key political figures in Gotham, Batman is forced to investigate the city's hidden corruption and question his family's involvement.",
                "Matt Reeves", "Robert Pattinson, Zoë Kravitz, Jeffrey Wright",
                LocalDate.of(2022, 3, 4),
                8.2f, Movie.MovieAgeRating.PG16, Movie.Language.VOSTFR, "The_Batman.png");

        Movie Movie8 = new Movie(7, "Deadpool 2", Movie.MovieGenre.Action, 2.00f,
                "Foul-mouthed mutant mercenary Wade Wilson (a.k.a. Deadpool), brings together a team of fellow mutant rogues to protect a young boy with supernatural abilities from the brutal, time-traveling cyborg Cable.",
                "David Leitch", "Ryan Reynolds, Josh Brolin, Morena Baccarin",
                LocalDate.of(2018, 5, 18),
                7.7f, Movie.MovieAgeRating.PG18, Movie.Language.VOSTFR, "Deadpool_2.png");

        Movie Movie9 = new Movie(8, "Vice Versa 2", Movie.MovieGenre.Animation, 1.45f,
                "The sequel to the beloved animated film, following the adventures of Riley and her emotions as they navigate new challenges.",
                "Pete Docter", "Amy Poehler, Bill Hader, Lewis Black",
                LocalDate.of(2023, 11, 22),
                8.0f, Movie.MovieAgeRating.G, Movie.Language.VOSTFR, "Vice_Versa_2.png");

        Movie Movie10 = new Movie(9, "The Hangover", Movie.MovieGenre.Comedy, 1.40f,
                "Three buddies wake up from a bachelor party in Las Vegas, with no memory of the previous night and the bachelor missing.",
                "Todd Phillips", "Bradley Cooper, Ed Helms, Zach Galifianakis",
                LocalDate.of(2009, 6, 5),
                7.7f, Movie.MovieAgeRating.PG18, Movie.Language.VOSTFR, "The_Hangover.png");

        Movie Movie11 = new Movie(10, "The Shawshank Redemption", Movie.MovieGenre.Drama, 2.22f,
                "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
                "Frank Darabont", "Tim Robbins, Morgan Freeman, Bob Gunton",
                LocalDate.of(1994, 9, 23),
                9.3f, Movie.MovieAgeRating.PG16, Movie.Language.VOSTFR, "The_Shawshank_Redemption.png");

        Movie Movie12 = new Movie(11, "Interstellar", Movie.MovieGenre.Science_Fiction, 2.49f,
                "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.",
                "Christopher Nolan", "Matthew McConaughey, Anne Hathaway, Jessica Chastain",
                LocalDate.of(2014, 11, 7),
                8.6f, Movie.MovieAgeRating.PG13, Movie.Language.VOSTFR, "Interstellar.png");

        Movie Movie13 = new Movie(12, "The Lord of the Rings: The Fellowship of the Ring", Movie.MovieGenre.Adventure, 2.58f,
                "A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron.",
                "Peter Jackson", "Elijah Wood, Ian McKellen, Orlando Bloom",
                LocalDate.of(2001, 12, 19),
                8.8f, Movie.MovieAgeRating.PG13, Movie.Language.VOSTFR, "The_Lord_of_the_Rings_The_Fellowship_of_the_Ring.png");

        Movie Movie14 = new Movie(13, "Harry Potter and the Sorcerer's Stone", Movie.MovieGenre.Fantasy, 2.32f,
                "An orphaned boy enrolls in a school of wizardry, where he learns the truth about himself, his family and the terrible evil that haunts the magical world.",
                "Chris Columbus", "Daniel Radcliffe, Rupert Grint, Emma Watson",
                LocalDate.of(2001, 11, 16),
                7.6f, Movie.MovieAgeRating.PG13, Movie.Language.VOSTFR, "Harry_Potter_and_the_Sorcerers_Stone.png");

        Movie Movie15 = new Movie(14, "Knives Out", Movie.MovieGenre.Mystery, 2.10f,
                "A detective investigates the death of a patriarch of an eccentric, combative family.",
                "Rian Johnson", "Daniel Craig, Chris Evans, Ana de Armas",
                LocalDate.of(2019, 11, 27),
                7.9f, Movie.MovieAgeRating.PG13, Movie.Language.VOSTFR, "Knives_Out.png");

        Movie Movie16 = new Movie(15, "Titanic", Movie.MovieGenre.Romance, 3.14f,
                "A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.",
                "James Cameron", "Leonardo DiCaprio, Kate Winslet, Billy Zane",
                LocalDate.of(1997, 12, 19),
                7.8f, Movie.MovieAgeRating.PG13, Movie.Language.VOSTFR, "Titanic.png");

        Movie Movie17 = new Movie(16, "Free Solo", Movie.MovieGenre.Documentary, 1.40f,
                "Follow Alex Honnold as he becomes the first person to ever free solo climb Yosemite's 3,000ft high El Capitan wall.",
                "Elizabeth Chai Vasarhelyi, Jimmy Chin", "Alex Honnold, Tommy Caldwell, Jimmy Chin",
                LocalDate.of(2018, 9, 28),
                8.2f, Movie.MovieAgeRating.PG13, Movie.Language.VOSTFR, "Free_Solo.png");

        Movie Movie18 = new Movie(17, "The Incredibles", Movie.MovieGenre.Family, 1.55f,
                "A family of undercover superheroes, while trying to live the quiet suburban life, are forced into action to save the world.",
                "Brad Bird", "Craig T. Nelson, Samuel L. Jackson, Holly Hunter",
                LocalDate.of(2004, 11, 5),
                8.0f, Movie.MovieAgeRating.PG13, Movie.Language.VOSTFR, "The_Incredibles.png");

        Movie Movie19 = new Movie(18, "Rocky", Movie.MovieGenre.Sport, 2.00f,
                "A small-time boxer gets a supremely rare chance to fight a heavyweight champion in a bout in which he strives to go the distance for his self-respect.",
                "John G. Avildsen", "Sylvester Stallone, Talia Shire, Burt Young",
                LocalDate.of(1976, 12, 3),
                8.1f, Movie.MovieAgeRating.PG16, Movie.Language.VOSTFR, "Rocky.png");

        Movie Movie20 = new Movie(19, "The Social Network", Movie.MovieGenre.History, 2.00f,
                "The story of the founding of Facebook and the lawsuits that followed.",
                "David Fincher", "Jesse Eisenberg, Andrew Garfield, Justin Timberlake",
                LocalDate.of(2010, 10, 1),
                7.7f, Movie.MovieAgeRating.PG13, Movie.Language.VOSTFR, "The_Social_Network.png");

        Movie Movie21 = new Movie(20, "Bohemian Rhapsody", Movie.MovieGenre.Music, 2.14f,
                "The story of the legendary British rock band Queen and lead singer Freddie Mercury, leading up to their famous performance at Live Aid.",
                "Bryan Singer", "Rami Malek, Lucy Boynton, Gwilym Lee",
                LocalDate.of(2018, 11, 2),
                8.0f, Movie.MovieAgeRating.PG13, Movie.Language.VOSTFR, "Bohemian_Rhapsody.png");

        Movie Movie22 = new Movie(21, "Anchorman: The Legend of Ron Burgundy", Movie.MovieGenre.News, 1.34f,
                "Ron Burgundy is San Diego's top-rated newsman in the male-dominated broadcasting of the 1970s, but that's all about to change for Ron and his cronies when an ambitious woman is hired as a new anchor.",
                "Adam McKay", "Will Ferrell, Christina Applegate, Steve Carell",
                LocalDate.of(2004, 7, 9),
                7.2f, Movie.MovieAgeRating.PG13, Movie.Language.VOSTFR, "Anchorman_The_Legend_of_Ron_Burgundy.png");

        Movie Movie23 = new Movie(22, "The Secret Life of Walter Mitty", Movie.MovieGenre.Travel, 1.54f,
                "When his job along with that of his co-worker are threatened, Walter takes action in the real world embarking on a global journey that turns into an adventure more extraordinary than anything he could have ever imagined.",
                "Ben Stiller", "Ben Stiller, Kristen Wiig, Jon Daly",
                LocalDate.of(2013, 12, 25),
                7.3f, Movie.MovieAgeRating.PG13, Movie.Language.VOSTFR, "The_Secret_Life_of_Walter_Mitty.png");

        Movie Movie24 = new Movie(23, "Jumanji: Welcome to the Jungle", Movie.MovieGenre.Game, 1.59f,
                "Four teenagers are sucked into a magical video game, and the only way they can escape is to work together to finish the game.",
                "Jake Kasdan", "Dwayne Johnson, Karen Gillan, Kevin Hart",
                LocalDate.of(2017, 12, 20),
                6.9f, Movie.MovieAgeRating.PG13, Movie.Language.VOSTFR, "Jumanji_Welcome_to_the_Jungle.png");
        
                
        AddMovie(Movie1);
        AddMovie(Movie2);
        AddMovie(Movie3);
        AddMovie(Movie4);
        AddMovie(Movie5);
        AddMovie(Movie6);
        AddMovie(Movie7);
        AddMovie(Movie8);
        AddMovie(Movie9);
        AddMovie(Movie10);
        AddMovie(Movie11);
        AddMovie(Movie12);
        AddMovie(Movie13);
        AddMovie(Movie14);
        AddMovie(Movie15);
        AddMovie(Movie16);
        AddMovie(Movie17);
        AddMovie(Movie18);
        AddMovie(Movie19);
        AddMovie(Movie20);
        AddMovie(Movie21);
        AddMovie(Movie22);
        AddMovie(Movie23);
        AddMovie(Movie24);


    }
    
    public void AddMovie(Movie movie) {
        movies.add(movie);
    }

    public void RemoveMovie(Movie movie) {
        movies.remove(movie);
    }

    
    public void DisplayMovie() {
        for (Movie movie : movies) {
            System.out.println("---------------------------------------------");
            System.out.println("Title: " + movie.Title);
            System.out.println("Genre: " + movie.Genre);
            System.out.println("Duration: " + movie.Duration + " minutes");
            System.out.println("Release Date: " + movie.ReleaseDate);
            System.out.println("Rating: " + movie.Rating);
            System.out.println("Age Rating: " + movie.AgeRating);
            System.out.println("Description: " + movie.Description);
            System.out.println("Director: " + movie.Director);
            System.out.println("Cast: " + movie.Cast);
            System.out.println("---------------------------------------------");
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

}
