package model;

import java.time.LocalDate;

public class Movie {
    public int id =0;
    public String Title;
    public MovieGenre Genre;
    public float Duration; // in hours
    public String Description;
    public String Director;
    public String Cast;
    public LocalDate ReleaseDate;
    public float Rating; // out of 10
    public MovieAgeRating AgeRating; // G, PG13, PG16, PG18
    public Language MovieLanguage;
    public String ImagePath2;
    public String Trailer;

    public enum MovieGenre {
        None, Action, Comedy, Drama, Horror, Science_Fiction, Thriller, Adventure, Fantasy, Mystery, Romance, Crime,
        Animation, Music, Documentary, Family, Sport, News, Travel, Game, History

    }

    public enum MovieAgeRating {
        G, PG6, PG8, PG13, PG16, PG18
    }

    public enum Language{
        VOSTFR,
        VF,
        VOSTEN,
        VO,
    }

    public Movie(int Id, String Title, MovieGenre Genre, float Duration, String Description, String Director,
            String Cast, LocalDate ReleaseDate, float Rating, MovieAgeRating AgeRating, Language MovieLanguage, String ImagePath) {

        this.id = Id;
        this.Title = Title;
        this.Genre = Genre;
        this.Duration = Duration;
        this.Description = Description;
        this.Director = Director;
        this.Cast = Cast;
        this.ReleaseDate = ReleaseDate;
        this.Rating = Rating;
        this.AgeRating = AgeRating;
        this.MovieLanguage = MovieLanguage;
        this.ImagePath2 = ImagePath;
        
    }
}
