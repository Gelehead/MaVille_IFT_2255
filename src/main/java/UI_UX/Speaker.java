package UI_UX;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Utils.Language.language;

// TODO : handle invalid input 

public class Speaker {
    private static final Scanner s = new Scanner(System.in);
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final String PASSWORD_REGEX = 
    "^(?=.*[0-9])" +         // at least one digit
    "(?=.*[a-z])" +          // at least one lowercase letter
    "(?=.*[A-Z])" +          // at least one uppercase letter
    "(?=.*[@#$%^&+=])" +     // at least one special character
    "(?=\\S+$).{8,}$";       // no whitespace, at least 8 characters
    private static final Pattern pattern = Pattern.compile(PASSWORD_REGEX);

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
                String mail = Speaker.ask("Entrer votre adresse mail : ");
                while (!isValidEmail(mail)){mail = Speaker.ask("Entrer une adresse mail valide : ");}

                String pw = Speaker.ask("Entrer votre mot de passe : ");
                while (!isSecurePassword(pw)){pw = Speaker.ask("Entrer un mot de passe valide :" +
                                                                "\n(>=1 number, >= 1 lowercase and upercase character, >= 1 special character (@#$%^&+=), 8+ characters, no whitespaces)");}
                
                String userType = Speaker.ask(Utils.Language.QUserType(language.FRENCH));
                switch (userType) {
                    case "1":
                        return Speaker.STATE.RESIDENT_MAIN;
                    
                    case "2":
                        return Speaker.STATE.MAIN_INTERVENANT;
                
                    default:
                        System.out.println("Choix invalide ,veuillez entrer 1 (résident) ou 2 (intervenant");
                        return Speaker.STATE.INITIAL;
                }

            case RESIDENT_MAIN:
                String choixResident = ask(Utils.Language.Main_menu_resident(language.FRENCH));

                switch (choixResident) {
                    // Retourner au menu des résidents
                    case "1":
                        System.out.println("La fonctionnalité pour consulter les travaux en cours n'est pas encore implémentée");
                        return Speaker.STATE.RESIDENT_MAIN;
                    case "2":
                        System.out.println("La fonctionnalité pour rechercher un projet en particulier n'est pas encore implémentée");
                        return Speaker.STATE.RESIDENT_MAIN;
                    case "3":
                        System.out.println("La fonctionnalité pour activer les notifications n'est pas encore implémentée");
                        return Speaker.STATE.RESIDENT_MAIN;
                    case "4":
                        System.out.println("La fonctionnalité pour planifier un projet n'est pas encore implémentée");
                        return Speaker.STATE.RESIDENT_MAIN;
                    case "5":
                        System.out.println("La fonctionnalité pour faire une requête de travail n'est pas encore implémentée");
                        return Speaker.STATE.RESIDENT_MAIN;
                    case "6":
                        System.out.println("La fonctionnalité pour accepter ou refuser la candidature d'un travail n'est pas encore implémentée");
                        return Speaker.STATE.RESIDENT_MAIN;
                    case "7":
                        System.out.println("La fonctionnalité pour signaler un problème n'est pas encore implémentée");
                        return Speaker.STATE.RESIDENT_MAIN;
                    case "8":
                        return Speaker.STATE.INITIAL;
                    case "9":
                        return Speaker.STATE.QUIT;
                }

            case MAIN_INTERVENANT:
                String choixIntervenant = ask(Utils.Language.Main_menu_intervenant(language.FRENCH));

                switch (choixIntervenant) {
                    case "1":
                           System.out.println("La fonctionnalité pour soumettre un nouveau projet n'est pas encore implémentée");
                            return Speaker.STATE.MAIN_INTERVENANT;
                    case "2":
                        System.out.println("La fonctionnalité pour soumettre une mise à jour du projet n'est pas encore implémentée");
                        return Speaker.STATE.MAIN_INTERVENANT;
                    case "3":
                        System.out.println("La fonctionnalité pour soumettre une candidature à une requête de travail n'est pas encore implémentée");
                        return Speaker.STATE.MAIN_INTERVENANT;
                    case "4":
                        return Speaker.STATE.INITIAL;
                    case "5":
                        return Speaker.STATE.QUIT;
                }
        
            case QUIT:
                // handle quit
                return null;
    
            default:
                return Speaker.STATE.PLACEHOLDER;
        }
    }

    public static boolean isValidEmail(String email) {
        // Compile the regex pattern
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        // If the email is null, it's not valid
        if (email == null) {
            return false;
        }
        // Match the email with the regex
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isSecurePassword(String password) {
        if (password == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
