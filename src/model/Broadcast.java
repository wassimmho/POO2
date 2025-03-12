package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Broadcast {
    public Movie movie;
    public Theater Room;
    public LocalDate Date;
    
    public ArrayList<Ticket> tickets;

    public Broadcast(Movie movie, Theater Room, LocalDate Date) {
        this.movie = movie;
        this.Room = Room;
        this.Date = Date;

        tickets = new ArrayList<>();
    }
}
