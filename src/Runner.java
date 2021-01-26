import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.io.IOException;
/**
 * Runner is class which reads in input files reflecting reservation requests for the movie theater and outputs
 * the theater seating assignments to a file called theaterSeatingAssignments.txt. Runner also prints the file path
 * to this file.
 */
public class Runner {
    public static void main(String[] args) throws IOException {
        MovieTheater m = new MovieTheater(true);
        List<String> lines = Files.readAllLines(Paths.get(args[0]));
        // change pathName if a different output file is wanted.
        String pathName = "C:\\Users\\Vishnu\\IdeaProjects\\MovieTheater\\src\\theaterSeatingAssignments.txt";
        PrintStream output = new PrintStream(new File(pathName));
        for(String line: lines) {
            String[] words = line.split("\\s+");
            output.print(words[0]+ " ");
            String seatsAssigned= "";
            int request = Integer.parseInt(words[1]);
            boolean noComma = true;
            for(Seat s: m.fulfillRequest(request)){
                if(noComma) {
                    output.print(s.toString());
                    noComma = false; // only the first seat should not have a comma preceding it in the output.
                } else {
                    output.print("," + s.toString());
                }
            }
            output.println();
        }
        System.out.println("OutputFilePath: " + pathName);
    }

}
