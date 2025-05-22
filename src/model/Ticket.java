package model;

public class Ticket {
    public Seats Seat;
    public TicketType Type; // VIP, Normal
    public Broadcast broadcast;

    public Client Buyer;
    public int TicketID;
    public int Price;

    public enum TicketType{
        VIP, Normal
    }

    public Ticket(Seats Seat, TicketType Type, Broadcast broadcast) {
        this.Seat = Seat;
        this.Type = Type;
        this.broadcast = broadcast;
    }
}
