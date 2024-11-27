package Utils;

/* Note : can be expended to search in a database for max efficiency 
 * plus very ugly like that
 */

public class Language {

    public enum language {
        FRENCH,
        ENGLISH
    }

    public static String  requestSearchQuery(language language){
        switch (language) {
            case FRENCH:
                return """
                Entrez une option de recherche ?
                [1] type de travaux 
                [2] Quartier
                [3] Date dans lequel le projet serait en cours 
                [4] Voir toutes les requêtes
                """;
            case ENGLISH : 
            return """
                What should we do ?
                [1] type of request
                [2] District
                [3] Date where project would be active
                [4] get all requests
                """;
            default:
                return "";
        } 
    }

    public static String planifMenu(language language){
        switch (language) {
            case FRENCH:
                return """
                Que désirez vous faire ?
                [1] Modifier mon emploi du temps
                [2] Consulter les emplois du temps d'autres personnes du quartier
                """;
            case ENGLISH : 
            return """
                What should we do ?
                [1] Modify my own schedule
                [2] See schedules of neighbours
                """;
            default:
                return "";
        } 
    }

    public static String subscribe_to_new_district(language language){
        switch (language) {
            case FRENCH:
                return "Entrez un quartier sur lequel vous voulez recevoir des nouvelles";
            case ENGLISH : 
                return "choose a new district to subscribe to";
            default:
                return "";
        } 
    }

    public static String ask_for_title(language language){
        switch (language) {
            case FRENCH:
                return "Entrez un titre";
            case ENGLISH : 
                return "placeholder ask_for_project_search_query";
            default:
                return "";
        } 
    }

    public static String ask_for_project_search_query(language language){
        switch (language) {
            case FRENCH:
                return """
                Choisissez parmis les options suivantes un critere de recherche
                [1] titre
                [2] type de travaux
                [3] progrès du projet
                [4] Rue
            """;
            case ENGLISH : 
                return "placeholder ask_for_project_search_query";
            default:
                return "";
        } 
    }

    public static String request_title(language language){
        switch (language) {
            case FRENCH:
                return "Donnez un titre à votre requete";
            case ENGLISH : 
                return "Give a title to your request";
            default:
                return "";
        }        
    }

    public static String enter_adress(language language){
        switch (language) {
            case FRENCH:
                return "Entrez une addresse";
            case ENGLISH : 
                return "Please enter an adress";
            default:
                return "";
        } 
    }

    public static String project_title(language language){
        switch (language) {
            case FRENCH:
                return "Donnez un titre à votre projet";
            case ENGLISH : 
                return "Give a title to your project";
            default:
                return "";
        }        
    }

    public static String request_hi_UwU(language language){
        switch (language) {
            case FRENCH:
                return "Entrez une nouvelle requete";
            case ENGLISH : 
                return "Enter a new request";
            default:
                return "";
        }
    }

    public static String request_reason(language language){
        switch (language) {
            case FRENCH:
                return "Donnez une raison parmis les suivantes\n" + reasonMenu(language);
            case ENGLISH :
                return "give a reason amongst the following ones\n" + reasonMenu(language);
            default:
                return "";
        }
    }

    public static String request_district(language language){
        switch (language) {
            case FRENCH:
                return "Donnez un quartier parmis les quartiers suivants\n" + districtMenu(language);
            case ENGLISH :
                return "Give a district amongst the following options \n" + districtMenu(language);
            default:
                return "";
        }
    }

    public static String districtMenu(language language) {
        switch (language) {
            case FRENCH:
                return """
                    [1] LaSalle
                    [2] Dollard-des-Ormeaux
                    [3] Côte-Saint-Luc
                    [4] Villeray-Saint-Michel-Parc-Extension
                    [5] Rosemont-La Petite-Patrie
                    [6] Hampstead
                    [7] Senneville
                    [8] Le Plateau-Mont-Royal
                    [9] Sainte-Anne-de-Bellevue
                    [10] Montréal-Ouest
                    [11] Côte-des-Neiges-Notre-Dame-de-Grâce
                    [12] L'Île-Bizard-Sainte-Geneviève
                    [13] Beaconsfield
                    [14] Anjou
                    [15] Verdun
                    [16] Le Sud-Ouest
                    [17] Mercier-Hochelaga-Maisonneuve
                    [18] Montréal-Est
                    [19] Lachine
                    [20] Saint-Léonard
                    [21] Montréal-Nord
                    [22] Outremont
                    [23] L'Île-Dorval
                    [24] Mont-Royal
                    [25] Pointe-Claire
                    [26] Dorval
                    [27] Pierrefonds-Roxboro
                    [28] Rivière-des-Prairies-Pointe-aux-Trembles
                    [29] Ahuntsic-Cartierville
                    [30] Saint-Laurent
                    [31] Ville-Marie
                    [32] Kirkland
                    [33] Baie-D'Urfé
                    [34] Westmount
                    """;
            case ENGLISH:
                return """
                    [1] LaSalle
                    [2] Dollard-des-Ormeaux
                    [3] Côte-Saint-Luc
                    [4] Villeray-Saint-Michel-Parc-Extension
                    [5] Rosemont-La Petite-Patrie
                    [6] Hampstead
                    [7] Senneville
                    [8] Le Plateau-Mont-Royal
                    [9] Sainte-Anne-de-Bellevue
                    [10] Montréal-Ouest
                    [11] Côte-des-Neiges-Notre-Dame-de-Grâce
                    [12] L'Île-Bizard-Sainte-Geneviève
                    [13] Beaconsfield
                    [14] Anjou
                    [15] Verdun
                    [16] Le Sud-Ouest
                    [17] Mercier-Hochelaga-Maisonneuve
                    [18] Montréal-Est
                    [19] Lachine
                    [20] Saint-Léonard
                    [21] Montréal-Nord
                    [22] Outremont
                    [23] L'Île-Dorval
                    [24] Mont-Royal
                    [25] Pointe-Claire
                    [26] Dorval
                    [27] Pierrefonds-Roxboro
                    [28] Rivière-des-Prairies-Pointe-aux-Trembles
                    [29] Ahuntsic-Cartierville
                    [30] Saint-Laurent
                    [31] Ville-Marie
                    [32] Kirkland
                    [33] Baie-D'Urfé
                    [34] Westmount
                    """;
            default:
                return "";
        }
    }
    

    public static String request_start(language language){
        switch (language) {
            case FRENCH:
                return "date de début (JJMMAAAA_HHMM) : ";
            case ENGLISH :
                return "starting date (DDMMYYYY_HHMM) : ";
            default:
                return "";
        }
    }

    public static String request_date(language language){
        switch (language) {
            case FRENCH:
                return "Entrez une date (JJMMAAAA_HHMM) : ";
            case ENGLISH :
                return "Please enter a date (DDMMYYYY_HHMM) : ";
            default:
                return "";
        }
    }

    public static String request_end(language language){
        switch (language) {
            case FRENCH:
                return "date de fin (JJMMAAAA_HHMM) : ";
            case ENGLISH :
                return "end date (DDMMYYYY_HHMM) : ";
            default:
                return "";
        }
    }

    public static String request_description(language language){
        switch (language) {
            case FRENCH:
                return "Donnez une brève description des travaux : ";
            case ENGLISH :
                return "Give a short description of the road work desired : ";
            default:
                return "";
        }
    }

    public static String request_streetid(language language){
        switch (language) {
            case FRENCH:
                return "rue principale des travaux : ";
            case ENGLISH :
                return "main road needing work : ";
            default:
                return "";
        }
    }

    public static String request_fromname(language language){
        switch (language) {
            case FRENCH:
                return "Route d intersection 1 :";
            case ENGLISH :
                return "Intersecting road 1 : ";
            default:
                return "";
        }
    }

    public static String request_toname(language language){
        switch (language) {
            case FRENCH:
                return "Route d intersection 2 :";
            case ENGLISH :
                return "Intersecting road 2 : ";
            default:
                return "";
        }
    }

    public static String request_length(language language){
        switch (language) {
            case FRENCH:
                return "longueur de l'entrave : ";
            case ENGLISH :
                return "length of the road work :";
            default:
                return "";
        }
    }

    public static String byebye(language language){
        switch (language) {
            case FRENCH:
                return "Merci de votre visite, au plaisir de vous revoir très bientôt.";
            case ENGLISH :
                return "Thank you for using us, we hope to see you again very soon.";
            default:
                return "";
        }
    }

    public static String no_impediment_found(language language){
        switch (language) {
            case FRENCH:
                return "Désolé, aucune entrave correspondant à vos spécification n'a été trouvé";
            case ENGLISH:
                return "Sorry, no impediment corresponding to your demands have been found";
            default:
                return "";
        }
    }

    public static String impediment_by_id(language language){
        switch (language) {
            case FRENCH:
                return "Recherche par ID de project";
            case ENGLISH : 
                return "Search by project id";
            default:
                return "";
        }
    }

    public static String impediment_by_road(language language){
        switch (language) {
            case FRENCH:
                return "Quelle rue voulez-vous rechercher?";
            case ENGLISH : 
                return "What road do you want to search for";
            default:
                return "";
        }
    }

    public static String search_for_specific_impediment(language language){
        switch (language) {
            case FRENCH:
                return  
                """ 
                Chercher par :
                [1] route
                [2] Travail associé
                """;
            case ENGLISH :
                return """
                Search by :
                [1] road 
                [2] similar project  
                """;
            default:
                return "";
        }
    }

    public static String anything_else(language language){
        switch (language) {
            case FRENCH:
                return "voulez vous entrer autre chose?";
            case ENGLISH :
                return "do you want to keep adding more?";
            default:
                return "";
        }
    }

    public static String ask_for_correct_format_schedule(language language){
        switch (language) {
            case FRENCH:
                return "Ecrivez selon le format suivant : \n    \" JOUR hhmm - hhmm\"";
            case ENGLISH : 
                return "please enter a response with the following format : \n    \" DAY hhmm - hhmm\"";
            default:
                return "";
        }
    }

    public static String ask_for_schedule(language language){
        switch (language) {
            case FRENCH:
                return "Donnez une plage horaire qui vous satisfait dans les horaires suivants : \n(--- : vide, ### : plein)";
            case ENGLISH:
                return "placeholder";
            default:
                return "";
        }
    }

    public static String fetching_impediments(language language){
        switch (language) {
            case FRENCH:
                return "Requête des entraves...";
            case ENGLISH :
                return "fetching impediments...";
            default:
                return "";
        }
    }    

    public static String fetching_records(language language){
        switch (language) {
            case FRENCH:
                return "Requête de travaux...";
            case ENGLISH :
                return "fetching records...";
            default:
                return "";
        }
    }

    public static String fetching_districts(language language){
        switch (language) {
            case FRENCH:
                return "Requête de quartiers...";
            case ENGLISH :
                return "fetching boroughs...";
            default:
                return "";
        }
    }

    public static String no_project_found(language language){
        switch (language) {
            case FRENCH:
                return "Désolé, aucun projet ne correspondant à vos spécification n'a été trouvé";
            case ENGLISH:
                return "Sorry, no project corresponding to your demands have been found";
            default:
                return "";
        }
    }

    public static String no_user_found(language language){
        switch (language) {
            case FRENCH:
                return "Désolé, aucun utilisateur correspondant à vos spécification n'a été trouvé";
            case ENGLISH:
                return "Sorry, no user corresponding to your demands have been found";
            default:
                return "";
        }
    }

    public static String not_implemented_filter_project_search(language language){
        switch (language) {
            case FRENCH:
                return "la fonction de recherche avec filtre n est pas encore implementee";
            case ENGLISH:
                return "The search function hasn't its filter function implemented yet";
            default:
                return "";
        }
    }

    public static String progressMenu(language lang) {
        switch (lang) {
            case FRENCH:
                return """
                choisissez un type de progression de Projet
                    [1] Non commencé
                    [2] En cours
                    [3] Terminé
                    [4] Placeholder
                        """;
            case ENGLISH:
                return """
                Please choose a type of project progression
                    [1] Not started
                    [2] In progress
                    [3] Finished
                    [4] Placeholder
                        """;
            default:
                return "";
        }
    }    

    public static String reasonMenu(language lang) {
        switch (lang) {
            case FRENCH:
                return """
                    [1] Travaux routiers
                    [2] Travaux de gaz ou électricité
                    [3] Construction ou rénovation
                    [4] Entretien paysager
                    [5] Travaux liés aux transports en commun
                    [6] Travaux de signalisation et éclairage
                    [7] Travaux souterrains
                    [8] Travaux résidentiels
                    [9] Entretien urbain
                    [10] Entretien des réseaux de télécommunication
                    [11] Raison non prise en charge
                    [12] Autre
                        """;
            case ENGLISH:
                return """
                    [1] Roadwork
                    [2] Gas or electricity work
                    [3] Construction or renovation
                    [4] Landscaping maintenance
                    [5] Work related to public transportation
                    [6] Signage and lighting work
                    [7] Underground work
                    [8] Residential work
                    [9] Urban maintenance
                    [10] Telecommunications network maintenance
                    [11] Unhandled reason
                    [12] Other
                        """;
            default:
                return "";
        }
    }
    
    
        

    public static String main_menu_admin(language language){
        switch (language) {
            case FRENCH: return """
                [1] Tous les utilisateurs
                [2] Tous les projects
                [3] Menu principal
                [4] quit
                [5] Tous les districts
                [6] Toutes les entraves
                [7] trouver quartier par addresse
                    """;
            case ENGLISH: return """
                [1] Get all Users
                [2] get all projects
                [3] Main menu
                [4] quit 
                [5] get all districts
                [6] get all impediments
                [7] get district by adress
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
    
    public static String Qcityid(language language){
        switch (language) {
            case FRENCH: return "Entrez votre indentifiant de ville";
            case ENGLISH: return "Please enter your city ID";
            default: return "";
        }
    }

    public static String QinType(language language) {
        switch (language) {
            case FRENCH: 
                return """
                Entrez la catégorie qui vous représente le mieux :
                [1] Entreprise publique
                [2] Entrepreneur privé
                [3] Particulier 
                """;
            case ENGLISH: 
                return """
                Enter the category that best represents you:
                [1] Public enterprise
                [2] Private entrepreneur
                [3] Individual
                """;
            default: 
                return "";
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
            case FRENCH: return "Quelle est votre date de naissance? (sous la forme \"JJMMAAA\")";
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
                                "[7] Signaler un problème ( non implemente ) \n" +
                                "[8] Consulter les entraves \n" +
                                "[9] Revenir au menu principal\n" + 
                                "[10] Quitter\n" + 
                                "Choisissez une option : ";
            case ENGLISH: return "Resident main menu:\n" +
                                    "[1] View ongoing projects\n" +
                                    "[2] Search for a specific project\n" +
                                    "[3] Activate notifications\n" +
                                    "[4] Plan a project\n" +
                                    "[5] Submit a work request\n" +
                                    "[6] Accept or reject a job application\n" +
                                    "[7] Report a problem ( not implemented )\n" +
                                    "[8] Search for a specific Impediment\n" +
                                    "[9] Go back to main menu\n" +
                                    "[10] Quit\n" +
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
                                "[4] Revenir au menu principal\n" + 
                                "[5] Quitter\n" +
                                "Choisissez une option : ";
            case ENGLISH: return "Contractor main menu:\n" +
                                    "[1] Submit a new project\n" +
                                    "[2] Submit a project update\n" +
                                    "[3] Apply for a work request\n" +
                                    "[4] Go back to main menu\n" +
                                    "[5] Quit\n" +
                                    "Choose an option: ";
            default: return "";
        }
    }

    public static String EnterValidEmail_NotAlreadyInDatabase(language language){
        switch (language) {
            case FRENCH: return "Votre addresse mail et invalide ou existe deja dans la base de donnees : ";
            case ENGLISH: return "Your mail adress is either invalid or already exists in the database";
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
