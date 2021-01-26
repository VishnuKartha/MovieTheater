/**
 * Seat is a class which represents a single seat in a movie theater. It keeps track whether this seat is assigned to
 * a reservation.
 */
public class Seat {
    private boolean isFilled; // whether or not the seat has been assigned to a reservation
    private final int seatNumber; // the number of the seat in its row
    private final char rowLetter; // the letter of the row of the seat

    /** Constructs a new unfilled Seat, with the rowLetter and seatNumber fields set to the user's input.
     *
     * @param rowLetter:  the letter of the row which this Seat is in .
     * @param seatNumber:  the number of this seat in its row.
     */
    public Seat(char rowLetter, int seatNumber) {
        this.seatNumber = seatNumber;
        this.rowLetter = rowLetter;
        isFilled = false;
    }

    /**
     * @return  the seat number of this seat
     */
    public int getSeatNumber() {
        return seatNumber;
    }

    /**
     * @return  the row letter of this seat.
     */
    public char getRowLetter() {
        return rowLetter;
    }

    /**
     * Fills this seat so that no other reservation can assign this seat.
     */
    public void fillSeat(){
        this.isFilled = true;
    }


    /**
     * @return  whether the seat is filled or not.
     */
    boolean isFilled() {
        return isFilled;
    }


    /**
     * @return  a String representation of this seat consisting of its row letter and it's seat number
     */
    public String toString(){
            return ("" + rowLetter + seatNumber);
        }

    /**
     * Standard equality operation.
     *
     * @param obj the object to be compared for equality
     * @return true if and only if the two seats have the same row letter, seat number, and isFilled value.
     */
    public boolean equals(Object obj) {
        if(obj instanceof Seat) {
            Seat s = (Seat) obj;
            return (s.rowLetter == this.rowLetter && s.seatNumber== this.seatNumber && s.isFilled == this.isFilled);
        }
        return false;
    }
}
