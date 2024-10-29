package Users;

import backend.Database.District_name;

public class Resident extends User{
    private String fname, lname, mail, pw, address;
    private int phoneNum, birthDay;

    //TODO: create hashmap of districts with enum as key
    private District_name district;

    public Resident(){}

    public Resident(String fname, String lname, String mail, String pw, int phoneNum, String address, int birthDay){
        super(fname, lname, mail, pw);
        this.phoneNum = phoneNum;
        this.address = address;
        this.birthDay = birthDay;
    }

    @Override
    public String toString() {
        return super.toString() + ", birthday: " + birthDay + ", phone number : " + phoneNum + ", adress : " + address;
    }


    // getters
    public String getLname()    {return this.lname;}
    public String getMail()     {return this.mail;}
    public String getPw()       {return this.pw;}
    public String getFname()    {return this.fname;}
    public int getPhoneNum()    {return this.phoneNum;}
    public String getaddress()  {return this.address;}
    public int getBirthDay()    {return this.birthDay;}
    public District_name geDistrict(){return this.district;}  

    // setters
    public void setFname(String fname)        { this.fname = fname; }
    public void setLname(String lname)        { this.lname = lname; }
    public void setMail(String mail)          { this.mail = mail; }
    public void setPw(String pw)              { this.pw = pw; }
    public void setPhoneNum(int phoneNum)     { this.phoneNum = phoneNum; }
    public void setAddress(String address)    { this.address = address; }
    public void setBirthDay(int birthDay)     { this.birthDay = birthDay; }
    public void setDistrict(District_name district){ this.district = district; }  
}