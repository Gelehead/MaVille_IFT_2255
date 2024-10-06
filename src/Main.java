import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
      Scanner utilisaTeur = new Scanner(System.in);
      while(true){
         System.out.println("vous etes resident ou intervenant");
         String choixUtilisateur = utilisaTeur.nextLine();
         if (choixUtilisateur.equals("resident")){
             System.out.println("liste des travaux \n requete de travail \n signaler un probleme");
             break;
         }
         else if (choixUtilisateur.equals("intervenant")){
             System.out.println("liste des projets \n liste des requetes   ");
             break;
         }
         else{System.out.println("il faut choisir resident ou interveneant");}
    }
}}