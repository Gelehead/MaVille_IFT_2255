class Resident extends User{
    private String fname, lname, mail, pw, address;
    private int phoneNum, birthDay;

    public Resident(String fname, String lname, String mail, String pw, int phoneNum, String address, int birthDay){
        super(fname, lname, mail, pw);
        this.fname = fname;
        this.lname = lname;
        this.mail = mail;
        this.pw = pw;
        this.phoneNum = phoneNum;
        this.address = address;
        this.birthDay = birthDay;
    }

    // setters 
    public String getLname() {return this.lname;}
    public String getMail() {return this.mail;}
    public String getPw() {return this.pw;}
    public String getFname() {return this.fname;}
    public int getPhoneNum() {return this.phoneNum;}
    public String getaddress() {return this.address;}
    public int getBirthDay() {return this.birthDay;}
    
}