/**
 * Class Seat
 * Each Seat is in a row(int) and column(char)
 * Each seat also has a Seat Class and a Seat Type
 * Each seat has a name.
 * Each seat can be assigned into a group. Group will be null if not in a group
 *
 */

public class Seat {
    private String passengerName;
    private int row;
    private char column;
    private SeatClass seatClass;
    private SeatType seatType;

    private String group = null;

    /**
     * Seat public constructor for seat
     * Used by plane in generateSeats
     * @param newRow Row for the new seat
     * @param newColumn Column for the new seat
     * @param st Seat type of the new seat
     * @param sc Seat Class of the new seat
     * @param pn Passenger name of the new seat
     */
    public Seat(int newRow, char newColumn, SeatType st, SeatClass sc, String pn){
        this.row = newRow;
        this.column = newColumn;
        this.seatType = st;
        this.seatClass = sc;
        this.passengerName = pn;
    }


    /**
     * Returns the row of the seat
     * @return the row of the seat
     */
    public int getRow(){
        return row;
    }

    /**
     * Returns the column of the seat
     * @return the column of the seat
     */
    public char getColumn(){
        return column;
    }

    /**
     * Returns the Seat Type of the seat
     * @return the seat Type of the seat
     */
    public SeatType getSeatType(){
        return seatType;
    }

    /**
     * Returns the Seat Class of the seat
     * @return the Seat Class of the seat
     */
    public SeatClass getSeatClass(){
        return seatClass;
    }

    /**
     * Returns the Passenger name of the seat
     * @return
     */
    public String getName(){
        return passengerName;
    }

    /**
     * Sets the name of the seat to the input
     * @param newName The name to set to the seat
     */
    public void setName(String newName){
        passengerName = newName;
    }

    /**
     * Sets the Group of the seat to the input
     * @param s The group to set to the seat
     */
    public void setGroup(String s){
        this.group = s;
    }

    /**
     * Returns the group of the seat
     * @return the group of the seat
     */

    public String getGroup(){
        return this.group;
    }

    /**
     * Sets the seat to empty
     * The group and the passenger name will be set to null
     */
    public void setEmpty(){
        this.group = null;
        this.passengerName = null;
    }

}
