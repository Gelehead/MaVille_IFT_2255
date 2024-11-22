package UI_UX;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.DeserializationFeature;

import Instances.*;
import Instances.Project.Progress;
import Utils.Language;
import Utils.Parser.Impediment;
import backend.Database;
import metrics.Date;

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

    public static String ask_inline(String question){
        System.out.print(question);
        String a = s.next();

        return a;
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
            case INITIAL: 
                String a = ask(Language.Qinitial(Dialog.choice_language));
                switch (a) {
                    case "1":
                        return Dialog.STATE.REGISTER;
                    case "2":
                        return Dialog.STATE.LOGIN;
                    case "3":
                        System.out.println("You are an admin");
                        return Dialog.STATE.MAIN_ADMIN;
                    default:
                        return Dialog.STATE.INITIAL;
                }


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

                String s = ask(Utils.Language.QUserType(Dialog.choice_language));
                User.Type userType = s.equals("1") ? User.Type.RESIDENT : s.equals("2") ? User.Type.INTERVENANT : User.Type.ADMIN;

                String fname = ask(Language.Qfname(Dialog.choice_language));
                String lname = ask(Language.Qlname(Dialog.choice_language));

                // main menu (mail address, pw and user type)
                String mail = ask(Language.Qmail(Dialog.choice_language));
                while (!isValidEmail(mail)){mail = ask(Language.EnterValidEmail(Dialog.choice_language));}

                String pw = ask("Entrer votre mot de passe : ");
                while (!isSecurePassword(pw)){pw = ask("Entrer un mot de passe valide :" +
                                                                "\n(>=1 number, >= 1 lowercase and upercase character, >= 1 special character (@#$%^&+=), 8+ characters, no whitespaces)");}

                Resident resident = new Resident();
                Intervenant intervenant = new Intervenant();

                switch (userType) {
                    case RESIDENT:
                        
                        String address = ask(Language.Qaddress(Dialog.choice_language));
                        int birthDay = Integer.parseInt(ask(Language.Qbirthday(Dialog.choice_language)));

                        resident.setFname(fname);
                        resident.setLname(lname);
                        resident.setMail(mail);
                        resident.setPw(pw);

                        resident.setDistrict(null);
                        resident.setAddress(address);
                        resident.setBirthDay(birthDay);

                        database.addResident(resident);
                        database.setActiveUser(resident);

                        System.out.println("alskca");

                        return Dialog.STATE.MAIN_RESIDENT;
                    case INTERVENANT:
                        String enterprise = ask(Language.Qenterprise(Dialog.choice_language));

                        intervenant.setFname(fname);
                        resident.setLname(lname);
                        resident.setMail(mail);
                        resident.setPw(pw);

                        intervenant.setEnterprise(enterprise);

                        database.addIntervenant(intervenant);
                        database.setActiveUser(intervenant);
                        
                        return Dialog.STATE.MAIN_INTERVENANT;
                    case ADMIN:
                        System.out.println("You are an admin");
                        return Dialog.STATE.MAIN_ADMIN;

                    default:
                        System.out.println("Choix invalide ,veuillez entrer 1 (résident) ou 2 (intervenant");
                        userType = User.Type.ADMIN;
                        switch(Speaker.ask(Utils.Language.QUserType(Dialog.choice_language)))  {
                            case "1" : userType = User.Type.RESIDENT;
                            case "2" : userType = User.Type.INTERVENANT;
                            case "3" : userType = User.Type.ADMIN;
                        }
                        return Dialog.STATE.INITIAL;
                }

            // TODO: bookmark
            case MAIN_RESIDENT:
                String choixResident = ask(Utils.Language.Main_menu_resident(Dialog.choice_language));

                switch (choixResident) {
                    // Retourner au menu des résidents
                    case "1":
                        return Dialog.STATE.TRAVAUX_RESIDENT;
                    case "2":
                        return Dialog.STATE.RECHERCHER_PROJET_RESIDENT;
                    case "3":
                        return Dialog.STATE.NOTIFS_RESIDENT;
                    case "4":
                        System.out.println(Language.NotImplemented_PlanifierProjet(Dialog.choice_language));
                        return Dialog.STATE.PLANIF_RESIDENT;
                    case "5":
                        System.out.println(Language.NotImplemented_RequeteTravail(Dialog.choice_language));
                        return Dialog.STATE.REQUEST_RESIDENT;
                    case "6":
                        System.out.println(Language.NotImplemented_AccepterRefuserCandidature(Dialog.choice_language));
                        return Dialog.STATE.VOTE_RESIDENT;
                    case "7":
                        System.out.println(Language.NotImplemented_SignalerProbleme(Dialog.choice_language));
                        return Dialog.STATE.SIGNAL_PRB_RESIDENT;
                    case "8":
                        return Dialog.STATE.IMPEDIMENT_RESIDENT;
                    case "9" :
                        database.setActiveUser(null);
                        return Dialog.STATE.INITIAL;
                    case "10":
                        return Dialog.STATE.QUIT;
            }

            // travaux en cours
            case TRAVAUX_RESIDENT:
                for (Project p : database.getProjectList()) {
                    if (p.getStatus() == Project.Progress.IN_PROGRESS){
                        System.out.println(p.toString());
                    }
                }
                return Dialog.STATE.MAIN_RESIDENT;

            // any project 
            case RECHERCHER_PROJET_RESIDENT:
                String query = ask(Language.ask_for_project_search_query(Dialog.choice_language));
                switch (query) {
                    case "1":
                        String title = ask_inline(Language.ask_for_title(Dialog.choice_language));
                        boolean none = true;
                        for (Project p : database.getProjectList()) {
                            if (p.getTitle().equals(title)){
                                System.out.println(p.toString());
                                none = false;
                            }
                        }
                        if (none) {System.out.println(Language.no_project_found(Dialog.choice_language));}
                        break;
                    case "2":
                        Project.Reason chosen_reason;
                        String r = ask(Language.reasonMenu(Dialog.choice_language));
                        switch (r) {
                            case "1":
                                chosen_reason = Project.Reason.Travaux_routiers;
                                break;
                            case "2":
                                chosen_reason = Project.Reason.Travaux_de_gaz_ou_électricité;
                                break;
                            case "3":
                                chosen_reason = Project.Reason.Construction_ou_rénovation;
                                break;
                            case "4":
                                chosen_reason = Project.Reason.Entretien_paysager;
                                break;
                            case "5":
                                chosen_reason = Project.Reason.Travaux_liés_aux_transports_en_commun;
                                break;
                            case "6":
                                chosen_reason = Project.Reason.Travaux_de_signalisation_et_éclairage;
                                break;
                            case "7":
                                chosen_reason = Project.Reason.Travaux_souterrains;
                                break;
                            case "8":
                                chosen_reason = Project.Reason.Travaux_résidentiel;
                                break;
                            case "9":
                                chosen_reason = Project.Reason.Entretien_urbain;
                                break;
                            case "10":
                                chosen_reason = Project.Reason.Entretien_des_réseaux_de_télécommunication;
                                break;
                            case "11":
                                chosen_reason = Project.Reason.UNHANDLED_REASON;
                                break;
                            case "12":
                                chosen_reason = Project.Reason.Autre;
                                break;
                            default:
                                chosen_reason = Project.Reason.UNHANDLED_REASON;
                                throw new IllegalArgumentException("Invalid choice: " + r);
                        }
                        boolean none2 = true;
                        for (Project p : database.getProjectList()) {
                            if (p.getReason() == chosen_reason){
                                System.out.println(p.toString());
                                none2 = false;
                            }
                        }
                        if (none2) {System.out.println(Language.no_project_found(Dialog.choice_language));}
                        break;

                    case "3" :
                        Project.Progress chosen_progress; 
                        String prog = ask(Language.progressMenu(Dialog.choice_language));
                        switch (prog) {
                            case "1":
                                chosen_progress = Progress.NOT_STARTED;
                                break;
                            case "2":
                                chosen_progress = Progress.IN_PROGRESS;
                                break;
                            case "3":
                                chosen_progress = Progress.FINISHED;
                                break;
                            case "4":
                                chosen_progress = Progress.PLACEHOLDER;
                                break;
                            default:
                                throw new IllegalArgumentException("Invalid choice: " + prog);
                        }
                        boolean none3 = true;
                        for (Project p : database.getProjectList()) {
                            if (p.getStatus() == chosen_progress){
                                System.out.println(p.toString());
                                none3 = false;
                            }
                        }
                        if (none3) {System.out.println(Language.no_project_found(Dialog.choice_language));}
                
                    case "4" : 
                        District chosen_district = District.handle_district(Language.request_district(Dialog.choice_language));
                        boolean none4 = true;
                        for (Project p : database.getProjectList()) {
                            if (p.getDistrict() == chosen_district){
                                System.out.println(p.toString());
                                none4 = false;
                            }
                        }
                        if (none4) {System.out.println(Language.no_project_found(Dialog.choice_language));}
                    default:
                        throw new IllegalArgumentException("Invalid choice: " + query);
                }

                return Dialog.STATE.MAIN_RESIDENT;

            // notification system
            case NOTIFS_RESIDENT : 
                
                return Dialog.STATE.MAIN_RESIDENT;
            
            // donner des plages horaires de disponibilites
            case PLANIF_RESIDENT :
                return Dialog.STATE.MAIN_RESIDENT;
            
            // plan a request
            case REQUEST_RESIDENT : 
                System.out.println(Language.request_hi_UwU(Dialog.choice_language));
                Request request = new Request(
                    Request.handle_reason(ask_inline(Language.request_reason(Dialog.choice_language))), 
                    Request.handle_district(ask_inline(Language.request_district(Dialog.choice_language))), 
                    Date.parse(ask_inline(Language.request_start(Dialog.choice_language))), 
                    //Date.parse(ask_inline(Language.request_end(Dialog.choice_language))), 
                    ask_inline(Language.request_streetid(Dialog.choice_language)), 
                    ask_inline(Language.request_fromname(Dialog.choice_language)), 
                    ask_inline(Language.request_toname(Dialog.choice_language)), 
                    Double.parseDouble(ask_inline(Language.request_length(Dialog.choice_language))),
                    ask(Language.request_description(Dialog.choice_language)),
                    ask(Language.request_title(Dialog.choice_language)),
                    database.getActiveUser()
                );
                database.addRequest(request);
                return Dialog.STATE.MAIN_RESIDENT;

            // vote for an existing request
            case VOTE_RESIDENT : 
                ArrayList<String> schedule = new ArrayList<>();
                    
                System.out.println(Language.ask_for_schedule(Dialog.choice_language));
                System.out.println(Date.week_schedule());
                schedule.add(ask(Language.ask_for_correct_format_schedule(Dialog.choice_language)));
                schedule.add(ask(Language.anything_else(Dialog.choice_language)));
                return Dialog.STATE.MAIN_RESIDENT;

            // signal a problem
            case SIGNAL_PRB_RESIDENT : 
                return Dialog.STATE.MAIN_RESIDENT;

            // search for a specific impediment
            // TODO:test 
            case IMPEDIMENT_RESIDENT :
                
                boolean byroad = ask(Language.search_for_specific_impediment(Dialog.choice_language)) == "1";
                String info = ask(byroad ? Language.impediment_by_road(Dialog.choice_language) : Language.impediment_by_id(Dialog.choice_language));

                boolean none_road = true;
                for (Impediment i : database.getImpedimentList()) {
                    // if the user said they want to search by road, test the road, else test the id
                    if (byroad ? i.affects(info) : i.idRequest.equals(info)){
                        System.out.println(i.toString());
                        none_road = false;
                    }
                }
                if (none_road) {System.out.println(Language.no_impediment_found(Dialog.choice_language));}
                return Dialog.STATE.MAIN_RESIDENT;


            case MAIN_INTERVENANT:
                String choixIntervenant = ask(Utils.Language.Main_menu_intervenant(Dialog.choice_language));

                switch (choixIntervenant) {
                    case "1":
                           System.out.println("La fonctionnalité pour soumettre un nouveau projet n'est pas encore implémentée");
                            return Dialog.STATE.NEW_PROJECT_INTERVENANT;
                    case "2":
                        System.out.println("La fonctionnalité pour soumettre une mise à jour du projet n'est pas encore implémentée");
                        return Dialog.STATE.UPDATE_INTERVENANT;
                    case "3":
                        System.out.println("La fonctionnalité pour soumettre une candidature à une requête de travail n'est pas encore implémentée");
                        return Dialog.STATE.REQUEST_INTERVENANT;
                    case "4":
                        database.setActiveUser(null);
                        return Dialog.STATE.INITIAL;
                    case "5":
                        return Dialog.STATE.QUIT;
            }

            case NEW_PROJECT_INTERVENANT:
                return null;

            case UPDATE_INTERVENANT :
                return null;

            case REQUEST_INTERVENANT :
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
                        database.printAll(User.Type.PROJECT);
                        return Dialog.STATE.MAIN_ADMIN;
                    case "3" : 
                        return Dialog.STATE.INITIAL;
                    case "4" : return null;
                    case "5" :
                    
                        return null;
                    default:
                        return Dialog.STATE.INITIAL;
                }

        

            case QUIT:
                // handle quit
                return Dialog.STATE.QUIT;
    
                
            default:
                return Dialog.STATE.QUIT;
        }
    }

    @SuppressWarnings("unused")
    public static boolean isValidEmail(String email) {
        // to test easily
        // TODO: remove
        if (email.equals("a")){return true;}

        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        if (email == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // warnings arent real
    @SuppressWarnings("unused")
    public static boolean isSecurePassword(String password) {
        if (password.equals("a")){return true;}
        if (password == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
