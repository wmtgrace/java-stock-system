import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ManTing
 */

public final class Customer implements Comparable<Customer>{
    //declare data member
    private String loginID;
    private String password;
    private Date DOB;
    private int Age;
    private char gender;
    private String phoneNum;
    private float discount;
    private String nickName,FName,LName,email,district,address;
  
    //Constructor of customer
    //initiate
    public Customer(){
        nickName="";
        district="";
        address="";
    }
    //constructor without optional item
    public Customer(String inID, String inPW, String inFName,String inLName,char inGender, String inEmail, Date inDOB,String inPhone) {
        this();
        this.FName = inFName;
        this.LName = inLName;
        this.loginID = inID;
        this.password =  inPW;
        this.gender=inGender;
        this.email= inEmail;
        this.phoneNum=inPhone;
        this.DOB=inDOB;
        this.Age = CalculateAge(DOB);//calculate the age
        this.discount = SystemMenu.checkDiscount(Age);
    }
    //constructor with NickName
    public Customer(String inID, String inPW,String inFName,String inLName, String inNName,char inGender, String inEmail,Date inDOB,String inPhone){
        this(inID, inPW, inFName,inLName,inGender,inEmail,inDOB, inPhone);
        this.nickName = inNName;
    }
    //constructor with address
    public Customer(String inID, String inPW,String inFName,String inLName,char inGender, String inEmail,Date inDOB,String inPhone,String inDistrict,String inAddress){
        this(inID, inPW, inFName,inLName,inGender,inEmail,inDOB, inPhone);
        this.district = inDistrict;
        this.address = inAddress;
    }
    //constructor with all input
     public Customer(String inID, String inPW,String inFName,String inLName, String inNName,char inGender, String inEmail,Date inDOB,String inPhone,String inDistrict,String inAddress) {
        this(inID, inPW, inFName,inLName,inGender,inEmail,inDOB, inPhone);
        this.nickName = inNName;
        this.district = inDistrict;
        this.address = inAddress;
    }

    

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNickName() {
        return nickName;
    }

    public String getEmail() {
        return email;
    }

    public String getDistrict() {
        return district;
    }

    public String getAddress() {
        return address;
    }
    //setter for user to change the password
    public void setPassword(String pw) {
        this.password = pw;
    }
    //setter for user to change the phone number
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
        
    }
    public void setDiscount(float discount) {
        this.discount = discount;
    }
    public String getLoginID() {
        return loginID;
    }

    public String getPassword() {
        return password;
    } 
    public String getFName() {
        return FName;
    }

    public String getLName() {
        return LName;
    }
    public char getGender() {
        return gender;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getDOB() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dob=sdf.format(DOB);
        return dob;
    }

    public int getAge() {
        return Age;
    }
    
   
    public float getDiscount() {
        return discount;
    }
    private int CalculateAge(Date dateOfBirth) {

        Calendar today = Calendar.getInstance();
        Calendar birthDate = Calendar.getInstance();

        int age = 0;

        birthDate.setTime(dateOfBirth);
        try{
        if (birthDate.after(today)) {
            throw new IllegalArgumentException("Can't be born in the future");
        }
        
        age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

        // If birth date is greater than todays date (after 2 days adjustment of leap year) then decrement age one year   
        if ( (birthDate.get(Calendar.DAY_OF_YEAR) - today.get(Calendar.DAY_OF_YEAR) > 3) ||(birthDate.get(Calendar.MONTH) > today.get(Calendar.MONTH ))){
            age--;
         // If birth date and todays date are of same month and birth day of month is greater than todays day of month then decrement age
        }else if ((birthDate.get(Calendar.MONTH) == today.get(Calendar.MONTH )) && (birthDate.get(Calendar.DAY_OF_MONTH) > today.get(Calendar.DAY_OF_MONTH ))){
            age--;
        }
        if(age>130){
            throw new IllegalArgumentException("May not be possible age more than 130");
        }
        }catch(IllegalArgumentException e){
            System.out.println("Error: "+ e.getMessage());
			//SystemMenu.loginSystem();
        }
        return age;
    }

    @Override
    public int compareTo(Customer otherCus) {
        return loginID.compareTo(otherCus.loginID);
    }
}
