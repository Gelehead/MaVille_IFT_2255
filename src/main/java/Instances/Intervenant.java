package Instances;
public class Intervenant extends User{

    public enum InType {
        Public_enterprise,
        Private_entrepreneur,
        Individual,

        Unhandled
    }

    public User.Type type = User.Type.INTERVENANT;
    private InType inType;
    // for now, we assume that the city id is whatever (not contained in any database)
    // and does not require checking
    private int cityId;

    public Intervenant(){}
    
    public Intervenant(String fname, String lname, String mail, String pw, InType inType, int cityId){
        super(fname, lname, mail, pw);
        this.inType = inType;
        this.cityId = cityId;
    }

    @Override
    public String toString() {
        return super.toString() + ", intervenant type : " + inType;
    }

    // getters 
    public InType getInType() {return this.inType;}
    public int getCityId() {return cityId;}

    public void setInType(InType inType) {this.inType = inType;}
    public void setCityId(int cityId) {this.cityId = cityId;}
}
