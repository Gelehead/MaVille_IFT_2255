package Utils;
import java.util.Scanner;

public class Speaker {
    // prints in the command line the question and returns as a string the answer of the user
    public static String ask(String question){
        Scanner s = new Scanner(System.in);

        System.out.println(question);
        String answer = s.nextLine();

        s.close();
        
        return answer;
    }
}
