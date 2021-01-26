/**
 * MovieTheater is a class which represents a movie theater. MovieTheater keeps track of the seats who are
 * already assigned to people and fulfills group seat reservation requests.
 */
import java.util.ArrayList;
import java.util.List;

public class MovieTheater {

    private  final int  numRows = 10; // the number of Rows in this movie theater.
    private  final SeatRow[] rows;  // represents the rows of seats in the movie theater
    private boolean canSplitGroups; // whether groups can be split across multiple rows
    private int availableSeatsLeft; // number of seats left in the movie theater that can be assigned to new requests.

    /** Constructs a MovieTheater object, with the canSplitGroups field set to the user's input.
     *
     * @param canSplitGroups:  whether or not the user allows groups to be split if there is no room for the
     *                        group in a single row.
     */
    public MovieTheater(boolean canSplitGroups){
        this.rows = new SeatRow[numRows];
        for(int i =0; i < numRows; i++) {
            rows[i] = new SeatRow((char)(i + 'A'));
        }
        this.canSplitGroups = canSplitGroups;
        this.availableSeatsLeft= this.numRows * SeatRow.rowSize;
    }

    /**
     * @return  whether the user has allowed groups to be split if there is no room for the
     *          group in a single row.
     */
    public boolean canSplitGroups() {
        return canSplitGroups;
    }

    /**  Sets the canSplitGroups field to the user's input.
     *
     * @param canSplitGroups:  whether or not the user allows groups to be split if there is no room for the
     *                        group in a single row.
     */
    public void setCanSplitGroups(boolean canSplitGroups){
        this.canSplitGroups = canSplitGroups;
    }

    /** Tries to seat the requested group of people in a single row. Mutates the inputted result list.
     *
     * @param result:  a list of the seats which are assigned to the people from the request. Mutates this list.
     * @param request: the number of seats which are requested to be together in the same row.
     * @return whether or not you were able to seat the requested people together in a row.
     */
    private boolean seatGroupTogether(List<Seat> result,int request){
        int index = rows.length-1;
        while (index >= 0) {
            if (rows[index].canFulfillRequest(request)) {
                this.availableSeatsLeft -= rows[index].getAvailableSeatsLeftInRow();
                result.addAll(rows[index].fulfillRequest(request));
                this.availableSeatsLeft += rows[index].getAvailableSeatsLeftInRow();
                return true;
            } else {
                index--;
            }
        }
        return false;
    }
    /** Seats the people in rows as close to each other as possible. Mutates the inputted result list and outputs
     * an error message if the user has not allowed for groups of people to be split up.
     *
     * @param result:  a list of the seats which are assigned to the people from the request. Mutates this list.
     * @param request: the number of seats which are requested to be together in the same row.
     *
     */
    private void seatGroupBySplitting(List<Seat> result, int request) {
        int index = rows.length-1;
        if(!this.canSplitGroups){
            System.out.println("ERROR: Cannot Fulfill Request because Group is not Allowed to Be Split");
        }
        while (this.canSplitGroups &&index >= 0 && request > 0) {
            if (rows[index].getAvailableSeatsLeftInRow() > 0) {
                int prevAvailableSeats = rows[index].getAvailableSeatsLeftInRow();
                this.availableSeatsLeft -= prevAvailableSeats;
                result.addAll(rows[index].fulfillRequest(Math.min(prevAvailableSeats, request)));
                request -= Math.min(prevAvailableSeats, request); // request is lowered by the amount of seats we have filled
                this.availableSeatsLeft += rows[index].getAvailableSeatsLeftInRow();
            }
            index--;
        }
    }
    /** Assigns seats to people if given a valid request. Outputs an error message if the request is invalid.
     *
     * @param request: the number of seats which are requested to be together in the same row.
     * @return : the seats which have been assigned to the request.
     *
     */
    public List<Seat> fulfillRequest(int request) {
        List<Seat> result = new ArrayList<>();
        if(this.availableSeatsLeft >= request) { //there are enough seats in the to fulfill this request.
           if (!seatGroupTogether(result,request)){ // no single row could fulfill the Request
               seatGroupBySplitting(result,request);
            }
            return result;
        }
       System.out.println("ERROR: Invalid Request - Not Enough Seats to Seat Group");
       return result;
    }

    /**
     * Standard equality operation.
     *
     * @param obj the object to be compared for equality
     * @return true if and only if the same seats are filled in the same rows of a movie theater.
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof MovieTheater) {
            MovieTheater t = (MovieTheater) obj;
            for(int i = 0; i < t.rows.length;i++) {
                if(!t.rows[i].equals(this.rows[i])){
                    return false;
                }
            }

        }
        return false;
    }

}
