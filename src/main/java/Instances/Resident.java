package Instances;

import java.util.ArrayList;

import backend.Database;
import backend.Database.District_name;

public class Resident extends User{
    public User.Type type = User.Type.RESIDENT;
    private String address, phoneNum;
    private int birthDay;
    private ArrayList<District> notifications = new ArrayList<>();

    private District_name district;

    public Resident(){}

    public Resident(String fname, String lname, String mail, String pw, String phoneNum, String address, int birthDay){
        super(fname, lname, mail, pw);
        this.phoneNum = phoneNum;
        this.address = address;
        this.birthDay = birthDay;
        notifications.add(Database.getDistrict(address));
    }

    @Override
    public String toString() {
        return super.toString() + ", birthday: " + birthDay + ", phone number : " + phoneNum + ", adress : " + address;
    }


    // getters
    public String getPhoneNum()    {return this.phoneNum;}
    public String getaddress()  {return this.address;}
    public int getBirthDay()    {return this.birthDay;}
    public District_name geDistrict(){return this.district;}  

    // setters
    public void setPhoneNum(String phoneNum)     { this.phoneNum = phoneNum; }
    public void setAddress(String address)    { this.address = address; }
    public void setBirthDay(int birthDay)     { this.birthDay = birthDay; }
    public void setDistrict(District_name district){ this.district = district; }  
}