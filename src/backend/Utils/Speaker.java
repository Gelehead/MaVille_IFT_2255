package Utils;
import java.util.Scanner;

public class Speaker {
    // put menu states here
    public enum STATE {
        INITIAL,

        RESIDENT_MAIN,
        RESIDENT_TRAVAUX,
        RESIDENT_NOTIFS,
        RESIDENT_PLANIF,
        RESIDENT_ABCD,

        MAIN_INTERVENANT,

        QUIT,

        PLACEHOLDER,
    }
    private static final Scanner s = new Scanner(System.in);
    // prints in the command line the question and returns as a string the answer of the user
    public static String ask(String question){
        System.out.println(question);
        String answer = s.nextLine();
        
        return answer;
    }

    public static void welcome(){
        String introduction = 
        """ 
        note : best enjoyed with a wide window
        ---------------------------------------------------------

        ███╗░░░███╗░█████╗░  ██╗░░░██╗██╗██╗░░░░░██╗░░░░░███████╗
        ████╗░████║██╔══██╗  ██║░░░██║██║██║░░░░░██║░░░░░██╔════╝
        ██╔████╔██║███████║  ╚██╗░██╔╝██║██║░░░░░██║░░░░░█████╗░░
        ██║╚██╔╝██║██╔══██║  ░╚████╔╝░██║██║░░░░░██║░░░░░██╔══╝░░
        ██║░╚═╝░██║██║░░██║  ░░╚██╔╝░░██║███████╗███████╗███████╗
        ╚═╝░░░░░╚═╝╚═╝░░╚═╝  ░░░╚═╝░░░╚═╝╚══════╝╚══════╝╚══════╝
        ----------------------------------------------------------
        """;
        System.out.println(introduction);
    }

    // STATE is part of an enum list
    // must return the next state after asking which way the user wants to go
    public static Speaker.STATE menu(Speaker.STATE STATE){
        switch (STATE) {
            case INITIAL:
                // main menu (mail address, pw and user type)
                // return RESIDENT_INITIAL
                // or
                // return INTERVENANT_INITIAL
                return Speaker.STATE.PLACEHOLDER;

            case RESIDENT_ABCD:
                // handle corresponding menu
                return Speaker.STATE.PLACEHOLDER;
        
            case QUIT:
                // handle quit
                return null;
    
            default:
                return Speaker.STATE.PLACEHOLDER;
        }
    }
}
