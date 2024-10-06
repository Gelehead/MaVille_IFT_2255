public class NouveauComptesIntervenant {
    private String nomComplet ,adresseCourriel,motDePasse , typeEntreprise,identifiantVille ;

    public   NouveauComptesIntervenant(String nomComplet ,String adresseCourriel , String motDePasse ,
                                       String typeEntreprise , String identifiantVille){
         this.nomComplet =nomComplet;
         this.adresseCourriel=adresseCourriel;
         this.motDePasse =motDePasse;
         this.typeEntreprise=typeEntreprise;
         this.identifiantVille=identifiantVille;

    }
    public String getNomComplet(){
        return this.nomComplet;
    }
    public String getTypeEntreprise(){
        return this.typeEntreprise;
    }
    public String getAdresseCourriel(){
        return this.adresseCourriel;
    }
    public String getMotDePasse(){
        return this.motDePasse;
    }
    public  String getIdentifiantVille(){return this.identifiantVille;};

    public void setNomComplet(String nomComplet){
         this.nomComplet=nomComplet;
    }
    public void setTypeEntreprise(String typeEntreprise){
         this.typeEntreprise=typeEntreprise;
    }
    public void setAdresseCourriel(String adresseCourriel){
         this.adresseCourriel=adresseCourriel;
    }
    public void setMotDePasse(String motDePasse){
         this.motDePasse= motDePasse;
    }
    public  void setIdentifiantVille(String identifiantVille){
        this.identifiantVille=identifiantVille;}

    public String toString() {

        return "Nomcomplet:" +nomComplet+

                ", adresseCourriel:"+adresseCourriel+
                ", motDePasse:"+ motDePasse+
                ", identifiant de la ville:"+ identifiantVille+
                ", type de l'entreprise:"+typeEntreprise;
    }
}
