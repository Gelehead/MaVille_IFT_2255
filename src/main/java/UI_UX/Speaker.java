package UI_UX;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Users.*;
import Utils.Language;

// TODO : handle invalid input 

public class Speaker {
    private static final Scanner s = new Scanner(System.in);
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final String PASSWORD_REGEX = 
    "^(?=.*[0-9])" +         // at least one digit
    "(?=.*[a-z])" +          // at least one lowercase letter
    "(?=.*[A-Z])" +          // at least one uppercase letter
    "(?=.*[@#$%^&+=?_])" +     // at least one special character
    "(?=\\S+$).{8,}$";       // no whitespace, at least 8 characters
    private static final Pattern pattern = Pattern.compile(PASSWORD_REGEX);

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
    // TODO: change INITIAL to be able to choose between login and register 
    public static Dialog.STATE menu(Dialog.STATE STATE, User user){
        switch (STATE) {
            case INITIAL:
                String fname = ask(Language.Qfname(Dialog.choice_language));
                String lname = ask(Language.Qlname(Dialog.choice_language));

                // main menu (mail address, pw and user type)
                String mail = ask(Language.Qmail(Dialog.choice_language));
                while (!isValidEmail(mail)){mail = ask(Language.EnterValidEmail(Dialog.choice_language));}

                String pw = ask("Entrer votre mot de passe : ");
                while (!isSecurePassword(pw)){pw = ask("Entrer un mot de passe valide :" +
                                                                "\n(>=1 number, >= 1 lowercase and upercase character, >= 1 special character (@#$%^&+=), 8+ characters, no whitespaces)");}
                
                user.setFname(fname);
                user.setLname(lname);
                user.setMail(mail);
                user.setPw(pw);

                String userType = Speaker.ask(Utils.Language.QUserType(Dialog.choice_language));
                switch (userType) {
                    case "1":
                        return Dialog.STATE.RESIDENT_MAIN;
                    
                    case "2":
                        return Dialog.STATE.MAIN_INTERVENANT;
                
                    default:
                        System.out.println("Choix invalide ,veuillez entrer 1 (résident) ou 2 (intervenant");
                        userType = Speaker.ask(Utils.Language.QUserType(Dialog.choice_language));
                }

            case RESIDENT_MAIN:
                String choixResident = ask(Utils.Language.Main_menu_resident(Dialog.choice_language));

                switch (choixResident) {
                    // Retourner au menu des résidents
                    case "1":
                        System.out.println(Language.NotImplemented_ConsulterTravaux(Dialog.choice_language));
                        return Dialog.STATE.RESIDENT_MAIN;
                    case "2":
                        System.out.println(Language.NotImplemented_RechercherProjet(Dialog.choice_language));
                        return Dialog.STATE.RESIDENT_MAIN;
                    case "3":
                        System.out.println(Language.NotImplemented_ActiverNotifications(Dialog.choice_language));
                        return Dialog.STATE.RESIDENT_MAIN;
                    case "4":
                        System.out.println(Language.NotImplemented_PlanifierProjet(Dialog.choice_language));
                        return Dialog.STATE.RESIDENT_MAIN;
                    case "5":
                        System.out.println(Language.NotImplemented_RequeteTravail(Dialog.choice_language));
                        return Dialog.STATE.RESIDENT_MAIN;
                    case "6":
                        System.out.println(Language.NotImplemented_AccepterRefuserCandidature(Dialog.choice_language));
                        return Dialog.STATE.RESIDENT_MAIN;
                    case "7":
                        System.out.println(Language.NotImplemented_SignalerProbleme(Dialog.choice_language));
                        return Dialog.STATE.RESIDENT_MAIN;
                    case "8":
                        return Dialog.STATE.INITIAL;
                    case "9":
                        return Dialog.STATE.QUIT;
                }

            case MAIN_INTERVENANT:
                String choixIntervenant = ask(Utils.Language.Main_menu_intervenant(Dialog.choice_language));

                switch (choixIntervenant) {
                    case "1":
                           System.out.println("La fonctionnalité pour soumettre un nouveau projet n'est pas encore implémentée");
                            return Dialog.STATE.MAIN_INTERVENANT;
                    case "2":
                        System.out.println("La fonctionnalité pour soumettre une mise à jour du projet n'est pas encore implémentée");
                        return Dialog.STATE.MAIN_INTERVENANT;
                    case "3":
                        System.out.println("La fonctionnalité pour soumettre une candidature à une requête de travail n'est pas encore implémentée");
                        return Dialog.STATE.MAIN_INTERVENANT;
                    case "4":
                        return Dialog.STATE.INITIAL;
                    case "5":
                        return Dialog.STATE.QUIT;
                }
        
            case QUIT:
                // handle quit
                return null;
    
            default:
                return Dialog.STATE.PLACEHOLDER;
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
