import java.util.ArrayList;
import java.util.Scanner;

public class LogIn {
    private String AdresseCourriel;
    private String motDePasse;
    private ArrayList<NouveauCompte> trouverCourriel;
    public LogIn(){
        ListDesComptes tt = ListDesComptes.getInstance();
        trouverCourriel=tt.getListdescomptes();
    }


    public void entrerAdresseCourriel(){
        Scanner nom = new Scanner(System.in);
        System.out.println("entrez votre adresse courriel");
        String courriel= nom.nextLine();
        this.AdresseCourriel=courriel;
    }
    public void entrerMotPasse(){
        Scanner nom = new Scanner(System.in);
        System.out.println("entrez votre mot de passe");
        String motdepasse= nom.nextLine();
        this.motDePasse=motdepasse;
    }
    public  void verifierMotDePasse(){

        System.out.println(trouverCourriel.size());
        for(NouveauCompte listcourriel:trouverCourriel){
            if (AdresseCourriel.equals(listcourriel.getAdresseCourriel()) &&
                listcourriel.getMotDePasse().equals(motDePasse)){
                System.out.println("t'as ouvert une session, vous pouvez voir le menu");

            }
        else System.out.println("nada");
        }


    }


    public static void main(String[] args) {


        LogIn FF = new LogIn();
        FF.entrerAdresseCourriel();
        FF.entrerMotPasse();
        FF.verifierMotDePasse();
    }

}
