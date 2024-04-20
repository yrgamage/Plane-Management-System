public class Ticket {
    private String row;
    private int seat;
    private int price;
    public Ticket(String row, int seat, int price){
        this.row = row;
        this.seat = seat;
        this.price = price;

    }
    public String getRow(){
        return this.row;
    }
    public int getSeat(){
        return this.seat;
    }
    public int getPrice(){
        return this.price;
    }
    public void setRow(String row){
        this.row = row;
    }
    public void setSeat(int seat){
        this.seat=seat;
    }
    public void setPrice(int price){
        this.price=price;
    }

}
