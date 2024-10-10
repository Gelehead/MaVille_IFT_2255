package Utils;
import java.util.Scanner;

// TODO : low priority but we could try implementing regex

public class Speaker {
    private static final Scanner s = new Scanner(System.in);

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
                String pw = Speaker.ask("Entrer votre mot de passe : ");
                String userType = Speaker.ask("Etes vous un resident ou un intervenant?"
                + "\n Tapez [1] pour resident" +
                  "\n Tapez [2] pour intervenant");
                // À completer : Prendre les infos de l'utilisateur
                if (userType.equals("1")) {
                    return Speaker.STATE.RESIDENT_MAIN;
                } else if (userType.equals("2")) {
                    return Speaker.STATE.MAIN_INTERVENANT;
                } else {
                    System.out.println("Choix invalide ,veuillez entrer 1 (résident) ou 2 (intervenant");
                    return Speaker.STATE.INITIAL;
                }

            case RESIDENT_MAIN:
                // menu de navigation pour les residents
                System.out.println(" \n Menu principal pour les résidents : ");
                System.out.println(" [1] Consulter les travaux en cours ");
                System.out.println(" [2] Rechercher un projets en particulier ");
                System.out.println(" [3] Activer les notifications ");
                System.out.println(" [4] Planifier un projet ");
                System.out.println(" [5] Faire une requete de travail ");
                System.out.println(" [6] Accepter ou refuser la candidature d'un travail ");
                System.out.println(" [7] Signaler un problème ");
                System.out.println(" [8] Retourner au menu principal ");
                System.out.println(" [9] Quitter ");

                String choixResident = ask("Choisissez une option : ");

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
                // menu de navigation pour les intervenants
                System.out.println(" \n Menu principal pour les intervenants : ");
                System.out.println(" [1] Soumettre un nouveau projet ");
                System.out.println(" [2] Soumettre une mise à jour du projet ");
                System.out.println(" [3] Soumettre une candidature à une requête de travail ");
                System.out.println(" [4] Retourner au menu principal ");
                System.out.println(" [5] Quitter ");

                String choixIntervenant = ask("Choisissez une option : ");

                switch (choixIntervenant) {
                    // Retourner au menu des intervenants
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


          // case RESIDENT_ABCD:
                // handle corresponding menu
             //   return Speaker.STATE.PLACEHOLDER;
        
            case QUIT:
                // handle quit
                return null;
    
            default:
                return Speaker.STATE.PLACEHOLDER;
        }
    }
}
