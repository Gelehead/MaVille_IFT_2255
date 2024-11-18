package UI_UX;
import java.lang.ProcessBuilder.Redirect.Type;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Instances.*;
import Utils.Language;
import backend.Database;

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
    public static Dialog.STATE menu(Dialog.STATE STATE, Database database){
        switch (STATE) {
            case INITIAL: return Integer.parseInt(ask(Language.Qinitial(Dialog.choice_language))) == 1 ? Dialog.STATE.LOGIN : Dialog.STATE.REGISTER;

            case LOGIN:

                String login_mail = ask(Language.Qmail(Dialog.choice_language));
                while (!database.exists(login_mail)) {
                    login_mail = ask(Language.MailNotInDatabase(Dialog.choice_language));
                }
                String password = ask(Language.Qpassword(Dialog.choice_language));
                int tries = 0;
                while (!database.authentify(login_mail, password) && (tries < 4)) {
                    tries += 1;
                    password = ask(Language.IncorrectPassword(Dialog.choice_language));
                }
                if ( tries > 3 ){
                    System.out.println(Language.tooMuchIncorrectTries(Dialog.choice_language));
                    return Dialog.STATE.INITIAL;
                }
                return database.userType(database.getUser(login_mail)).equals(User.Type.INTERVENANT) ? 
                    Dialog.STATE.MAIN_INTERVENANT
                    : Dialog.STATE.MAIN_RESIDENT;


            case REGISTER:

                User user = new User();

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
                        
                        String address = ask(Language.Qaddress(Dialog.choice_language));
                        int birthDay = Integer.parseInt(ask(Language.Qbirthday(Dialog.choice_language)));

                        Resident resident = (Resident) user;

                        resident.setDistrict(null);
                        resident.setAddress(address);
                        resident.setBirthDay(birthDay);

                        database.addResident(resident);
                        database.setActiveUser(resident);

                        return Dialog.STATE.MAIN_RESIDENT;
                    case "2":
                        String enterprise = ask(Language.Qenterprise(Dialog.choice_language));

                        Intervenant intervenant = (Intervenant) user;
                        intervenant.setEnterprise(enterprise);

                        database.addIntervenant(intervenant);
                        database.setActiveUser(intervenant);
                        
                        return Dialog.STATE.MAIN_INTERVENANT;
                    default:
                        System.out.println("Choix invalide ,veuillez entrer 1 (résident) ou 2 (intervenant");
                        userType = Speaker.ask(Utils.Language.QUserType(Dialog.choice_language));
                }


            case MAIN_RESIDENT:
                String choixResident = ask(Utils.Language.Main_menu_resident(Dialog.choice_language));

                switch (choixResident) {
                    // Retourner au menu des résidents
                    case "1":
                        System.out.println(Language.NotImplemented_ConsulterTravaux(Dialog.choice_language));
                        return Dialog.STATE.MAIN_RESIDENT;
                    case "2":
                        System.out.println(Language.NotImplemented_RechercherProjet(Dialog.choice_language));
                        return Dialog.STATE.MAIN_RESIDENT;
                    case "3":
                        System.out.println(Language.NotImplemented_ActiverNotifications(Dialog.choice_language));
                        return Dialog.STATE.MAIN_RESIDENT;
                    case "4":
                        System.out.println(Language.NotImplemented_PlanifierProjet(Dialog.choice_language));
                        return Dialog.STATE.MAIN_RESIDENT;
                    case "5":
                        System.out.println(Language.NotImplemented_RequeteTravail(Dialog.choice_language));
                        return Dialog.STATE.MAIN_RESIDENT;
                    case "6":
                        System.out.println(Language.NotImplemented_AccepterRefuserCandidature(Dialog.choice_language));
                        return Dialog.STATE.MAIN_RESIDENT;
                    case "7":
                        System.out.println(Language.NotImplemented_SignalerProbleme(Dialog.choice_language));
                        return Dialog.STATE.MAIN_RESIDENT;
                    case "8":
                        database.setActiveUser(null);
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
                        database.setActiveUser(null);
                        return Dialog.STATE.INITIAL;
                    case "5":
                        return Dialog.STATE.QUIT;
            }

            case TRAVAUX_RESIDENT:
                return null;

            case MAIN_ADMIN:
                String choixAdmin = ask(Utils.Language.main_menu_admin(Dialog.choice_language));
                switch (choixAdmin) {
                    case "1":
                        System.out.println("Residents : ");
                        database.printAll(User.Type.RESIDENT);

                        System.out.println("Intervenants : ");
                        database.printAll(User.Type.INTERVENANT);

                        System.out.println("Admins : ");
                        database.printAll(User.Type.ADMIN);
                        return Dialog.STATE.MAIN_ADMIN;
                    case "2" : 
                        System.out.println("projects idk");
                        database.printAll(null);
                        return Dialog.STATE.MAIN_ADMIN;
                    case "3" : 
                        return Dialog.STATE.INITIAL;
                    case "4" : return null;
                    default:
                        return Dialog.STATE.INITIAL;
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
