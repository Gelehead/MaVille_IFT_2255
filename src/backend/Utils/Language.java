package Utils;

/* Note : can be expended to search in a database for max efficiency */

public class Language {
    public static String Qfname(String language){       return language == "french" ? "Quel est votre prenom?" : "What's your first name?";}
    public static String Qlname(String language){       return language == "french" ? "Quel est votre nom de famille?" : "What's your last name?";}
    public static String Qmail(String language){        return language == "french" ? "Quel est votre addresse mail?" : "What's your mail address?";}
    public static String Qpassword(String language){    return language == "french" ? "Quel est votre mot de passe?" : "What's your password?";}
    public static String Qid(String language){          return language == "french" ? "Quel est votre numero d'identification?" : "What's your ID number?";}
    public static String Qenterprise(String language){  return language == "french" ? "Quel est votre entreprise?" : "What's your first name?";}
    public static String notAnEnterprise(String language){return language == "french" ? "Cet utilisateur n'est pas une entreprise?" : "this user is not an enterprise"; }
}
