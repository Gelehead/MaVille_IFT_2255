package Instances;

import java.util.ArrayList;

import backend.Database;
import backend.Database.District_name;

/**
 * La classe {@code Resident} représente un résident qui est une extension de la classe {@code User}.
 * Elle contient des informations spécifiques à un résident telles que l'adresse, 
 * le numéro de téléphone, la date de naissance et les notifications de district.
 */
public class Resident extends User {
    
    /**
     * Type d'utilisateur, ici défini comme {@code RESIDENT}.
     */
    public User.Type type = User.Type.RESIDENT;

    /**
     * Adresse du résident.
     */
    private String address;

    /**
     * Numéro de téléphone du résident.
     */
    private String phoneNum;

    /**
     * Date de naissance du résident.
     */
    private int birthDay;

    /**
     * Liste des notifications de district pour le résident.
     */
    private ArrayList<District> notifications = new ArrayList<>();

    /**
     * Nom du district du résident.
     */
    private District_name district;

    /**
     * Constructeur par défaut de la classe {@code Resident}.
     */
    public Resident() {}

    /**
     * Constructeur avec paramètres pour créer un nouvel {@code Resident}.
     *
     * @param fname     Le prénom du résident.
     * @param lname     Le nom de famille du résident.
     * @param mail      L'adresse e-mail du résident.
     * @param pw        Le mot de passe du résident.
     * @param phoneNum  Le numéro de téléphone du résident.
     * @param address   L'adresse du résident.
     * @param birthDay  La date de naissance du résident.
     */
    public Resident(String fname, String lname, String mail, String pw, String phoneNum, String address, int birthDay) {
        super(fname, lname, mail, pw);
        this.phoneNum = phoneNum;
        this.address = address;
        this.birthDay = birthDay;
        notifications.add(Database.getDistrict(address));
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de l'objet {@code Resident}.
     *
     * @return Une chaîne de caractères représentant le résident.
     */
    @Override
    public String toString() {
        return super.toString() + ", birthday: " + birthDay + ", phone number: " + phoneNum + ", address: " + address;
    }

    // Getters

    /**
     * Obtient le numéro de téléphone du résident.
     *
     * @return Le numéro de téléphone du résident.
     */
    public String getPhoneNum() {
        return this.phoneNum;
    }

    /**
     * Obtient l'adresse du résident.
     *
     * @return L'adresse du résident.
     */
    public String getaddress() {
        return this.address;
    }

    /**
     * Obtient la date de naissance du résident.
     *
     * @return La date de naissance du résident.
     */
    public int getBirthDay() {
        return this.birthDay;
    }

    /**
     * Obtient le nom du district du résident.
     *
     * @return Le nom du district du résident.
     */
    public District_name geDistrict() {
        return this.district;
    }

    // Setters

    /**
     * Définit le numéro de téléphone du résident.
     *
     * @param phoneNum Le nouveau numéro de téléphone du résident.
     */
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    /**
     * Définit l'adresse du résident.
     *
     * @param address La nouvelle adresse du résident.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Définit la date de naissance du résident.
     *
     * @param birthDay La nouvelle date de naissance du résident.
     */
    public void setBirthDay(int birthDay) {
        this.birthDay = birthDay;
    }

    /**
     * Définit le nom du district du résident.
     *
     * @param district Le nouveau nom du district du résident.
     */
    public void setDistrict(District_name district) {
        this.district = district;
    }
    
}
