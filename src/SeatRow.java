/**
 * SeatRow is a class which represents a row of seats in a movie theater. SeatRow keeps track of the
 * seats which are already assigned reservations and fulfills requests to assign seats in a row to a reservation.
 * The seats in a row are always filled from left to right(0th index to rowSize - 1th index).
 */

import java.util.ArrayList;
import java.util.List;

public class SeatRow {
    public static final int  rowSize = 20; // the number of seats in this SeatRow
    private  static final int  buffer  = 3;// the number of seats between groups of people who are seated
    private final Seat[] seats; // the seats in this SeatRow
    private final char rowLetter; // the assigned letter of this SeatRow
    private int availableSeatsLeftInRow; // holds the number of seats which new people are able to be seated in.
                                         // ** seats in buffers are not included in this number **
    private int lastFilledSeatIndex; // No seats are filled to the right of the seat at lastFilledSeatIndex

    /** Constructs a SeatRow object, with the rowLetter field set to the user's input.
     *
     * @param rowLetter:  the letter of the row which this SeatRow represents.
     */
    public SeatRow(char rowLetter) {
        this.seats = new Seat[rowSize];
        this.rowLetter = rowLetter;
        for(int i =0; i < this.seats.length;i++){
            seats[i] = new Seat(this.rowLetter,i+1);
        }
        this.lastFilledSeatIndex = -1;
        this.availableSeatsLeftInRow = rowSize;
    }
    /**
     * @return  the rowLetter of this SeatRow
     */
    public char getRowLetter() {
        return rowLetter;
    }

    /**
     * @return  the number of seats available for new people to be assigned to.
     */
    public int getAvailableSeatsLeftInRow() {
        return availableSeatsLeftInRow;
    }

    /** Returns whether this row can fill the given request.
     *
     * @return  whether or not this row can fill the given request of the given group of people sitting next to each
     *          other.
     * @throws IllegalArgumentException an exception if the requested number of people is less than 0.
     */
    public boolean canFulfillRequest(int request) throws IllegalArgumentException{
        if(request < 0) {
            throw new IllegalArgumentException();
        }
        return availableSeatsLeftInRow >= request;
    }

    /** Assigns seats of this row to people if given a valid request. Seats are assigned from left to right.
     * Outputs an error message if the request is invalid.
     *
     * @param request: the number of seats which are requested to be together in the same row.
     * @return : the seats which have been assigned to the request.
     */
    public List<Seat> fulfillRequest(int request)  {
        if(!canFulfillRequest(request)) {
            System.out.println("ERROR: Invalid Request - Not Enough Seats to Seat Group");
            return new ArrayList<Seat>();
        } else { // request is able to be filled, change availableSeatsLeft appropriately.
            List<Seat> seatsAssigned = new ArrayList<>();
            int requestsRemaining = request;
            int index;
            if(lastFilledSeatIndex == -1) {
                index = 0;
            } else {
                 index = lastFilledSeatIndex + buffer + 1;
            }
            while(index < seats.length && requestsRemaining > 0){
                seats[index].fillSeat();
                seatsAssigned.add(seats[index]);
                requestsRemaining--;
                index++;
            }

            //update the fields after assigning the seats requested
            this.availableSeatsLeftInRow -= (SeatRow.buffer +request);
            //cannot have a negative number of available seats left
            this.availableSeatsLeftInRow = Math.max(this.availableSeatsLeftInRow,0);
            this.lastFilledSeatIndex = index-1;
            return seatsAssigned;
        }
    }
    /**
     * Standard equality operation.
     *
     * @param obj the object to be compared for equality
     * @return true if and only if the same seats are filled in the two rows.
     */
    public boolean equals(Object obj) {
        if(obj instanceof SeatRow) {
            SeatRow r = (SeatRow) obj;
            for(int i = 0; i < r.seats.length;i++) {
                if(!r.seats[i].equals(this.seats[i])){
                    return false;
                }
            }
            return true;

        }
        return false;
    }

}
