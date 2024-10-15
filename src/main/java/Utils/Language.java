package Utils;

/* Note : can be expended to search in a database for max efficiency 
 * plus very ugly like that
 */

public class Language {

    // TODO: placeholder dialogue, replace when possible
    public static String Qfname(String language){       return language == "french" ? "Quel est votre prenom?" : "What's your first name?";}
    public static String Qlname(String language){       return language == "french" ? "Quel est votre nom de famille?" : "What's your last name?";}
    public static String Qmail(String language){        return language == "french" ? "Quel est votre addresse mail?" : "What's your mail address?";}
    public static String Qpassword(String language){    return language == "french" ? "Quel est votre mot de passe?" : "What's your password?";}
    public static String Qid(String language){          return language == "french" ? "Quel est votre numero d'identification?" : "What's your ID number?";}
    public static String Qenterprise(String language){  return language == "french" ? "Quel est votre entreprise?" : "What's your first name?";}
    public static String Qphone(String language){       return language == "french" ? "Quel est votre numero de telephone?" : "What's your phone number?";}
    public static String Qaddress(String language){     return language == "french" ? "Quel est votre addresse?" : "What's your address?";}
    public static String Qbirthday(String language){    return language == "french" ? "Quel est votre date de naissance?" : "What's your birth day?";}

    public static String notAnEnterprise(String language){return language == "french" ? "Cet utilisateur n'est pas une entreprise?" : "this user is not an enterprise";}

    public static String QUserType(String language){
        return language == "french" ? "Etes vous un resident ou un intervenant?"
                + "\n Tapez [1] pour resident" +
                  "\n Tapez [2] pour intervenant"
                : "not yet implemented";
                }
    public static String QUserType(){return "Etes vous un resident ou un intervenant?"
                + "\n Tapez [1] pour resident" +
                  "\n Tapez [2] pour intervenant";
    }

    // nav menu for residents    
    public static String Main_menu_resident(){
        return 
        " Menu principal pour les résidents :\n" +
        " [1] Consulter les travaux en cours\n" +
        " [2] Rechercher un projet en particulier\n" +
        " [3] Activer les notifications\n" +
        " [4] Planifier un projet\n" +
        " [5] Faire une requête de travail\n" +
        " [6] Accepter ou refuser la candidature d'un travail\n" +
        " [7] Signaler un problème\n" +
        " [8] Retourner au menu principal\n" +
        " [9] Quitter\n" + 
        "Choisissez une option : ";
    }

    public static String Main_menu_intervenant(){
        return 
        " Menu principal pour les intervenants :\n" +
        " [1] Soumettre un nouveau projet\n" +
        " [2] Soumettre une mise à jour du projet\n" +
        " [3] Soumettre une candidature à une requête de travail\n" +
        " [4] Retourner au menu principal\n" +
        " [5] Quitter\n" +
        "Choisissez une option : ";
    }
}
