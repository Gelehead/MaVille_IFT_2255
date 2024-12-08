package Instances;

/**
 * Classe représentant un intervenant qui hérite de la classe User.
 */
public class Intervenant extends User{

    /**
     * Énumération des types d'intervenants.
     */
    public enum InType {
        Public_enterprise,
        Private_entrepreneur,
        Individual,

        Unhandled
    }

    /**
     * Le type de l'utilisateur, défini ici comme INTERVENANT.
     */
    public User.Type type = User.Type.INTERVENANT;
    
    /**
     * Le type d'intervenant.
     */
    private InType inType;
    
    // for now, we assume that the city id is whatever (not contained in any database)
    // and does not require checking
    
    /**
     * L'identifiant de la ville associée à l'intervenant.
     */
    private int cityId;

    /**
     * Constructeur par défaut de la classe Intervenant.
     */
    public Intervenant(){}
    
    /**
     * Constructeur avec paramètres pour créer un nouvel intervenant.
     *
     * @param fname   Le prénom de l'intervenant.
     * @param lname   Le nom de famille de l'intervenant.
     * @param mail    L'adresse email de l'intervenant.
     * @param pw      Le mot de passe de l'intervenant.
     * @param inType  Le type d'intervenant.
     * @param cityId  L'identifiant de la ville associée.
     */
    public Intervenant(String fname, String lname, String mail, String pw, InType inType, int cityId){
        super(fname, lname, mail, pw);
        this.inType = inType;
        this.cityId = cityId;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de l'intervenant.
     *
     * @return Une chaîne de caractères représentant l'intervenant.
     */
    @Override
    public String toString() {
        return super.toString() + ", intervenant type : " + inType;
    }

    // Getters 

    /**
     * Obtient le type d'intervenant.
     *
     * @return Le type d'intervenant.
     */
    public InType getInType() {
        return this.inType;
    }

    /**
     * Obtient l'identifiant de la ville associée à l'intervenant.
     *
     * @return L'identifiant de la ville.
     */
    public int getCityId() {
        return cityId;
    }

    // Setters 

    /**
     * Modifie le type d'intervenant.
     *
     * @param inType Le nouveau type d'intervenant.
     */
    public void setInType(InType inType) {
        this.inType = inType;
    }

    /**
     * Modifie l'identifiant de la ville associée à l'intervenant.
     *
     * @param cityId Le nouvel identifiant de la ville.
     */
    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
