import java.util.ArrayList;
import java.util.Scanner;

public class ListDesComptesIntervenant {
    private ArrayList<NouveauComptesIntervenant> listdescomptesIntervenant;
    //private String nomComplet;
    public  ListDesComptesIntervenant(){
        listdescomptesIntervenant= new ArrayList<>();
    }


    public String demandeAdresseCourriel(){
        Scanner nom = new Scanner(System.in);
        System.out.println("entrez votre adresse courriel professionnel(example: philipetoto@gmail.com)");
        String adresseCourriel = nom.nextLine();
        return  adresseCourriel;}
    public String demandeMotDePasse(){
        Scanner nom = new Scanner(System.in);
        System.out.println("entrez votre mot de passe (8 caracteres minimum)");
        String motDePasse = nom.nextLine();
        return  motDePasse;}
    public String demandeIdentifiant(){
        Scanner nom = new Scanner(System.in);
        System.out.println("entrez votre identifiant de la ville (example: 12345677)");
        String identifiant = nom.nextLine();
        return  identifiant;}
    public String demandeTypeEntreprise(){
        Scanner nom = new Scanner(System.in);
        System.out.println("entrez votre type d'entreprise (example: prive ou public)");
        String type= nom.nextLine();
        return  type;
    }
    public String demandeNomComplet(){
        Scanner nom = new Scanner(System.in);
        System.out.println("entrez votre nom complet (example: philipe toto)");
        String nomComplet = nom.nextLine();
        return  nomComplet;}
    public void ajouterCompte( String nomComplet, String adresseCourriel,
                               String motDePasse, String identification, String TypeEntreprise) {
        NouveauComptesIntervenant nouveauCompte = new NouveauComptesIntervenant(nomComplet, adresseCourriel,
                                                  motDePasse, identification, TypeEntreprise);
        listdescomptesIntervenant.add(nouveauCompte);
}}
