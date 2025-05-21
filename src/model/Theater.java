package model;

import java.util.ArrayList;

public class Theater {
    public int TheaterId;
    public String Name;
    
    public int NormalCapacity;
    public int VipCapacity;

    public Boolean isAvailable;
    public int TotalPlaces;
    
    public int NormalReservedPlaces = 0;
    public int VipReservedPlaces = 0;

    public ArrayList<Seats> NormalSeats = new ArrayList<Seats>();
    public ArrayList<Seats> VipSeats = new ArrayList<Seats>();

    public Theater(int Id, String Name, int NormalCapacity, int VipCapacity, int TheaterId, Boolean Available) {
        this.TheaterId = Id;
        this.Name = Name;
        this.NormalCapacity = NormalCapacity;
        this.TheaterId = TheaterId;
        this.VipCapacity = VipCapacity;
        this.isAvailable = Available;
        this.TotalPlaces = NormalCapacity + VipCapacity;
    }
    public void initialseat(){
        for(int i=0; i<NormalCapacity; i++){
            NormalSeats.add(new Seats(i+1, Seats.SeatType.REGULAR));
        }
        for(int i=0; i<VipCapacity; i++){
            VipSeats.add(new Seats(i+1, Seats.SeatType.VIP));
        }
    }

}
