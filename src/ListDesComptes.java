import java.util.ArrayList;
import java.util.Scanner;

public class ListDesComptes {
    private static ListDesComptes instance;
    private   ArrayList<NouveauCompte> listdescomptes;
    //private String nomComplet;
    public  ListDesComptes(){
        listdescomptes= new ArrayList<>();
    }
    public static ListDesComptes getInstance() {
        if (instance == null) {
            instance = new ListDesComptes();
        }
        return instance;
    }

    public ArrayList<NouveauCompte> getListdescomptes() {
        return listdescomptes;
    }

    public String demandeDateNaissance(){
        Scanner nom = new Scanner(System.in);
        System.out.println("entrez votre date de naissance (example: jj-mm-aaaa)");
         String dateNaissance = nom.nextLine();
        return  dateNaissance;
      }
    public String demandeAdresseCourriel(){
        Scanner nom = new Scanner(System.in);
        System.out.println("entrez votre adresse courriel (example: philipetoto@gmail.com)");
        String adresseCourriel = nom.nextLine();
        return  adresseCourriel;}
    public String demandeMotDePasse(){
        Scanner nom = new Scanner(System.in);
        System.out.println("entrez votre mot de passe (8 caracteres minimum)");
        String motDePasse = nom.nextLine();
        return  motDePasse;}
    public String demandeTelephone(){
        Scanner nom = new Scanner(System.in);
        System.out.println("entrez votre numero telephone (example: 514-222-2222)");
        String telePhone = nom.nextLine();
        return  telePhone;}
    public String demandeAdresseResidentiel(){
        Scanner nom = new Scanner(System.in);
        System.out.println("entrez votre adresse courriel (example: 1234 rue saint denis)");
        String adresseResidentiel= nom.nextLine();
        return  adresseResidentiel;
    }
    public String demandeNomComplet(){
        Scanner nom = new Scanner(System.in);
        System.out.println("entrez votre nom complet (example: philipe toto)");
        String nomComplet = nom.nextLine();
        return  nomComplet;}
    public void ajouterCompte( String nomComplet, String dateNaissance, String adresseCourriel,
                              String motDePasse, String telePhone, String adresseResidentiel) {
        NouveauCompte nouveauCompte = new NouveauCompte(nomComplet, dateNaissance, adresseCourriel,
                motDePasse, telePhone, adresseResidentiel);
        listdescomptes.add(nouveauCompte);
    }

    public static void main(String[] args) {
        //String email;
        // String rr= "zizo@gmail.com";
        ListDesComptes cc = ListDesComptes.getInstance();
        String NOM = cc.demandeNomComplet();
        String DATE = cc.demandeDateNaissance();
        String COURRIEL = cc.demandeAdresseCourriel();
        String MOTPASSE = cc.demandeMotDePasse();
        String TELEPHONE = cc.demandeTelephone();
        String RESIDENCE = cc.demandeAdresseResidentiel();

        cc.ajouterCompte(NOM, DATE, COURRIEL
                , MOTPASSE, TELEPHONE, RESIDENCE);
        cc.ajouterCompte("zizo zo", "01-01-1999", "zizo@gmail.com"
                , "124", "5143498333", "4444 sodoku");
        System.out.println(cc.listdescomptes.get(0));
        System.out.println(cc.getListdescomptes().get(1));
        System.out.println(cc.listdescomptes.size());
        ListDesComptes DD= ListDesComptes.getInstance();
        DD.ajouterCompte("GF","JHGF","NB","NBV","NBV","JHGF");
        System.out.println(DD.getListdescomptes().size());
       // LogIn FF = new LogIn();
        //FF.entrerAdresseCourriel();
        //FF.entrerMotPasse();
        //FF.verifierMotDePasse();

    }
}