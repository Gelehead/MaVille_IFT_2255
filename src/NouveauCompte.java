public class NouveauCompte {
    private String Nomcomplet, dateNaissance, adresseCourriel,
                    motDePasse, telePhone, adresseResidentiel;
    public   NouveauCompte(){}
    public   NouveauCompte(String Nomcomplet, String dateNaissance, String adresseCourriel,
                                     String motDePasse, String telePhone, String adresseResidentiel){
        this.Nomcomplet=Nomcomplet;
        this.dateNaissance=dateNaissance;
        this.adresseCourriel=adresseCourriel;
        this.motDePasse=motDePasse;
        this.telePhone=telePhone;
        this.adresseResidentiel=adresseResidentiel;

    }
    public String getNomcomplet(){
        return this.Nomcomplet;
    }
    public String getDateNaissance(){
        return this.dateNaissance;
    }
    public String getAdresseCourriel(){
        return this.adresseCourriel;
    }
    public String getMotDePasse(){
        return this.motDePasse;
    }
    public String getTelePhone(){
        return this.telePhone;
    }
    public String getAdresseResidentiel(){return this.adresseResidentiel;}



    //public  void ajouterCompte(){}

    public String toString() {

        return "Nomcomplet:" +Nomcomplet+
        ", dateNaissance:"+dateNaissance+
       ", adresseCourriel:"+adresseCourriel+
        ", motDePasse:"+ motDePasse+
        ", telePhone:"+ telePhone+
        ", adresseResidentiel:"+adresseResidentiel;
    }


}
