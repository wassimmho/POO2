package model;

public class Seats {
    public int seatNumber;
    public boolean isAvailable;
    public SeatType Type;
    public double price;

    public enum SeatType {
        REGULAR,
        VIP;
    }

    public Seats(int seatNumber, SeatType type) {
        this.seatNumber = seatNumber;
        this.Type = type;
        this.isAvailable = true;

        if(this.Type == SeatType.REGULAR){
            this.price = 800;
        }else{
            this.price = 1000;
        }
    

    }
}
