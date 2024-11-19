package Utils;

/* Note : can be expended to search in a database for max efficiency 
 * plus very ugly like that
 */

public class Language {

    public enum language {
        FRENCH,
        ENGLISH
    }

    public static String main_menu_admin(language language){
        switch (language) {
            case FRENCH: return """
                [1] Tous les utilisateurs
                [2] Tous les projects
                [3] Menu principal
                [4] quit
                    """;
            case ENGLISH: return """
                [1] Get all Users
                [2] get all projects
                [3] Main menu
                [4] quit 
                    """;
            default: return "";
        }
    }

    public static String noSuchUser(language language, String mail){
        switch (language) {
            case FRENCH: return "L'utilisateur avec l'email" + mail + " n'existe pas dans la base de données." ;
            case ENGLISH: return "User with email " + mail + " does not exist in the database."; 
            default: return "";
        }
    }

    public static String tooMuchIncorrectTries(language language){
        switch (language) {
            case FRENCH: return "Trop d'échecs. Veuillez réessayer dans un moment";
            case ENGLISH: return "Too much incorrect tries. Please try again in a moment";
            default: return "";
        }
    }

    public static String Qinitial(language language){
        switch (language) {
            case FRENCH: return """
                Choisissez une option
                    [1] Connexion
                    [2] Inscription
                    [3] Admin (sera caché pour le produit final)
                    """;
        
            case ENGLISH: return """
                        Choose an option:
                        [1] Login
                        [2] Register 
                    """;
            default: return "";
        }
    }

    public static String MailNotInDatabase(language language){
        switch (language) {
            case FRENCH: return "L'addresse mail entrée n'existe pas dans notre base de donnees, veuillez en entrer une nouvelle";
            case ENGLISH: return "The adress you entered does not exist in our database, please enter a new one";
            default: return "";
        }
    }

    public static String IncorrectPassword(language language){
        switch (language) {
            case FRENCH: return "mot de passe incorrect, essayez à nouveau.";
            case ENGLISH: return "incorrect password, try again";
            default: return "";
        }
    }

    public static String Qfname(language language){
        switch (language) {
            case FRENCH: return "Quel est votre prenom?";
            case ENGLISH: return "What's your first name?";
            default: return "";
        }
    }

    public static String Qlname(language language){
        switch (language) {
            case FRENCH: return "Quel est votre nom de famille?";
            case ENGLISH: return "What's your last name?";
            default: return "";
        }
    }

    public static String Qmail(language language){
        switch (language) {
            case FRENCH: return "Quel est votre adresse mail?";
            case ENGLISH: return "What's your email address?";
            default: return "";
        }
    }

    public static String Qpassword(language language){
        switch (language) {
            case FRENCH: return "Quel est votre mot de passe?";
            case ENGLISH: return "What's your password?";
            default: return "";
        }
    }

    public static String Qid(language language){
        switch (language) {
            case FRENCH: return "Quel est votre numéro d'identification?";
            case ENGLISH: return "What's your ID number?";
            default: return "";
        }
    }

    public static String Qenterprise(language language){
        switch (language) {
            case FRENCH: return "Quel est votre entreprise?";
            case ENGLISH: return "What's your enterprise name?";
            default: return "";
        }
    }

    public static String Qphone(language language){
        switch (language) {
            case FRENCH: return "Quel est votre numéro de téléphone?";
            case ENGLISH: return "What's your phone number?";
            default: return "";
        }
    }

    public static String Qaddress(language language){
        switch (language) {
            case FRENCH: return "Quel est votre adresse?";
            case ENGLISH: return "What's your address?";
            default: return "";
        }
    }

    public static String Qbirthday(language language){
        switch (language) {
            case FRENCH: return "Quelle est votre date de naissance?";
            case ENGLISH: return "What's your birth date?";
            default: return "";
        }
    }

    public static String notAnEnterprise(language language){
        switch (language) {
            case FRENCH: return "Cet utilisateur n'est pas une entreprise.";
            case ENGLISH: return "This user is not an enterprise.";
            default: return "";
        }
    }

    public static String QUserType(language language){
        switch (language) {
            case FRENCH: return "Êtes-vous un résident ou un intervenant?\n" +
                                "Tapez [1] pour résident\n" +
                                "Tapez [2] pour intervenant";
            case ENGLISH: return "Are you a resident or a contractor?\n" +
                                "Press [1] for resident\n" +
                                "Press [2] for contractor";
            default: return "";
        }
    }

    public static String Main_menu_resident(language language){
        switch (language) {
            case FRENCH: return "Menu principal pour les résidents :\n" +
                                "[1] Consulter les travaux en cours\n" +
                                "[2] Rechercher un projet en particulier\n" +
                                "[3] Activer les notifications\n" +
                                "[4] Planifier un projet\n" +
                                "[5] Faire une requête de travail\n" +
                                "[6] Accepter ou refuser la candidature d'un travail\n" +
                                "[7] Signaler un problème\n" +
                                "[8] Quitter\n" + 
                                "Choisissez une option : ";
            case ENGLISH: return "Resident main menu:\n" +
                                    "[1] View ongoing projects\n" +
                                    "[2] Search for a specific project\n" +
                                    "[3] Activate notifications\n" +
                                    "[4] Plan a project\n" +
                                    "[5] Submit a work request\n" +
                                    "[6] Accept or reject a job application\n" +
                                    "[7] Report a problem\n" +
                                    "[8] Quit\n" +
                                    "Choose an option: ";
            default: return "";
        }
    }

    public static String Main_menu_intervenant(language language){
        switch (language) {
            case FRENCH: return "Menu principal pour les intervenants :\n" +
                                "[1] Soumettre un nouveau projet\n" +
                                "[2] Soumettre une mise à jour du projet\n" +
                                "[3] Soumettre une candidature à une requête de travail\n" +
                                "[4] Quitter\n" +
                                "Choisissez une option : ";
            case ENGLISH: return "Contractor main menu:\n" +
                                    "[1] Submit a new project\n" +
                                    "[2] Submit a project update\n" +
                                    "[3] Apply for a work request\n" +
                                    "[4] Quit\n" +
                                    "Choose an option: ";
            default: return "";
        }
    }

    public static String EnterValidEmail(language language){
        switch (language) {
            case FRENCH: return "Entrer une adresse mail valide : ";
            case ENGLISH: return "Please enter a valid mail adress";
            default: return "";
        }
    }

    public static String NotImplemented_ConsulterTravaux(language language) {
        switch (language) {
            case FRENCH: return "La fonctionnalité pour consulter les travaux en cours n'est pas encore implémentée";
            case ENGLISH: return "The feature to view ongoing projects is not yet implemented";
            default: return "";
        }
    }

    public static String NotImplemented_RechercherProjet(language language) {
        switch (language) {
            case FRENCH: return "La fonctionnalité pour rechercher un projet en particulier n'est pas encore implémentée";
            case ENGLISH: return "The feature to search for a specific project is not yet implemented";
            default: return "";
        }
    }

    public static String NotImplemented_ActiverNotifications(language language) {
        switch (language) {
            case FRENCH: return "La fonctionnalité pour activer les notifications n'est pas encore implémentée";
            case ENGLISH: return "The feature to activate notifications is not yet implemented";
            default: return "";
        }
    }

    public static String NotImplemented_PlanifierProjet(language language) {
        switch (language) {
            case FRENCH: return "La fonctionnalité pour planifier un projet n'est pas encore implémentée";
            case ENGLISH: return "The feature to plan a project is not yet implemented";
            default: return "";
        }
    }

    public static String NotImplemented_RequeteTravail(language language) {
        switch (language) {
            case FRENCH: return "La fonctionnalité pour faire une requête de travail n'est pas encore implémentée";
            case ENGLISH: return "The feature to submit a work request is not yet implemented";
            default: return "";
        }
    }

    public static String NotImplemented_AccepterRefuserCandidature(language language) {
        switch (language) {
            case FRENCH: return "La fonctionnalité pour accepter ou refuser la candidature d'un travail n'est pas encore implémentée";
            case ENGLISH: return "The feature to accept or reject a job application is not yet implemented";
            default: return "";
        }
    }

    public static String NotImplemented_SignalerProbleme(language language) {
        switch (language) {
            case FRENCH: return "La fonctionnalité pour signaler un problème n'est pas encore implémentée";
            case ENGLISH: return "The feature to report a problem is not yet implemented";
            default: return "";
        }
    }


}
