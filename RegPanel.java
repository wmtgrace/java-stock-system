
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ManTing
 */
public class RegPanel extends JPanel implements ItemListener{
    //componet in registration panel
    private final  static String PHONE = "[\\d]{8}";
    private final  static String PW ="[\\w.!#$%&'*+/=?^_`{|}~-]{3,35}";
    private final static String ID = "[a-zA-Z][\\w]{1,35}";
    private final  static String NAME ="[\\s\\w]{2,35}";//\w= word character: [a-zA-Z_0-9]
    private final static String EMAIL ="\\b[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,4}\\b";
    private final static String ADDRESS = "[\\s\\w!#$%&'()*+-./:,;<=>?]{1,231}";
    private final String EMPTY = "";
    private final JLabel reg;
    private JPasswordField pw1TF,pw2TF;
    private final int wLbl =220,componentH=30,wTF = 400,xLbl=50,xTF=270,xCLbl=675,wCLbl=180;//width of Label,Height of component,width of TF,x position of label,x position of TF,x position of the check label,width of checklabel
    private int cusY,cusD;
    private String cusM,inputDate,inputDistrict;
    private char cusGender;
    private final JTextField inputTF[];
    private final JTextArea address;
    private final JTextArea check[] ;
    
    public RegPanel(){
        String[] label = new String[]{"Login ID","Password","Re-enter Password","<html>Fullname:<font size=3>(Surname frist)</font> </html>","<html>Nickname:<font size=3>(optional)</font></html>","Gender","Email","Date of Birth","Phone Number","<html>Address:<font size=3>(optional)</font></html>"};
        Integer[] lengthTF = new Integer[]{30,35,35,35,35,50,8};//login size,password size,name size,nickname size,email size,phone size
        inputDistrict=EMPTY;
        inputTF = new JTextField[8];
        check = new JTextArea[label.length];

        this.setLayout(null);
        reg = new JLabel("Registration",JLabel.CENTER);
        reg.setFont(new Font("Lucida Console",Font.BOLD,30));
        reg.setBounds(350, 20,250,componentH);
        this.add(reg);
        int y=20;
        int j=0;
        for(int i=0;i<label.length;i++){
            JLabel l = new JLabel(label[i]+": ");
            if(i==2){
                l.setFont(new Font("Lucida Console",Font.PLAIN,18));
            }
            
            l.setBounds(xLbl,y+=50,wLbl,componentH);
            //.setBorder(new LineBorder(Color.red, 1));
            this.add(l);
            if(i!=1 && i!=2 &&i!=3 && i!=5 && i!=7 && i!=9){
                inputTF[j]= new JTextField();
                inputTF[j].setBounds(xTF,y,wTF,componentH);
                l.setLabelFor(inputTF[j]);
                if(i<6){
                    inputTF[j].setDocument(new TFLengthRestricted(lengthTF[i]));
                }
                else if(i==6)
                    inputTF[j].setDocument(new TFLengthRestricted(lengthTF[i-1]));
                else if(i==8){
                    inputTF[j].setDocument(new TFLengthRestricted(lengthTF[i-2]));
                }
                    
                    
                this.add(inputTF[j]);
                j++;
            }
            else if(i==1){
                pw1TF = new JPasswordField();
                pw1TF.setBounds(xTF,y,wTF,componentH);
                pw1TF.setFont(new Font("Lucida Console",Font.PLAIN,20));
                l.setLabelFor(pw1TF);
                pw1TF.setDocument(new TFLengthRestricted(lengthTF[i]));
                this.add(pw1TF);
            }
            else if(i==2){
                pw2TF = new JPasswordField();
                pw2TF.setBounds(xTF,y,wTF,componentH);
                l.setLabelFor(pw2TF);
                pw2TF.setFont(new Font("Lucida Console",Font.PLAIN,20));
                pw2TF.setDocument(new TFLengthRestricted(lengthTF[i]));
                this.add(pw2TF);
            }
            else if(i==3){
                inputTF[j]= new JTextField();
                inputTF[j].setBounds(xTF,y,200,componentH);
                inputTF[j].setDocument(new TFLengthRestricted(lengthTF[i]));
                this.add(inputTF[j]);
                j++;
                inputTF[j]= new JTextField();
                inputTF[j].setBounds(xTF+200,y,200,componentH);
                inputTF[j].setDocument(new TFLengthRestricted(lengthTF[i]));
                this.add(inputTF[j]);
                j++;
            }
            
            check[i] = new JTextArea();
            
            check[i].setFont(new Font("Arial",Font.PLAIN,12));
            check[i].setBounds(xCLbl, y, wCLbl, componentH);
            check[i].setForeground(Color.red);
            check[i].setBackground(this.getBackground());
            //check[i].setBorder(new LineBorder(Color.red, 1));
            check[i].setEditable(false);
            check[i].setLineWrap(true);
            check[i].setWrapStyleWord(true);
            this.add(check[i]);
        }
        check[9].setSize(wCLbl, 50);
        ButtonGroup genderGp = new ButtonGroup();
        JRadioButton m =new  JRadioButton("Male");
        JRadioButton f =new  JRadioButton("Female");
        m.setBounds(xTF,320,110,componentH);
        f.setBounds(xTF+200,320,110,componentH);
        m.setFont(new Font("Lucida Console",Font.PLAIN,20));
        f.setFont(new Font("Lucida Console",Font.PLAIN,20));
        m.addItemListener(this);
        f.addItemListener(this);
        genderGp.add(m);
        genderGp.add(f);
        m.setSelected(true);//select male first
        this.add(m);
        this.add(f);
       
        String[] monthItem = {"Month","January","February","March","April","May","June","July","August","September","October","November","December"};
        JComboBox monthBox = new JComboBox(monthItem);
        monthBox.setBounds(xTF,420,180,componentH);
        monthBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent monthev) {
                if (monthev.getStateChange() == ItemEvent.SELECTED) {
                    if(monthev.getItem().equals("Month")){
                        cusM=null;
                    }
                    else
                    cusM = monthev.getItem().toString();
                }
            }
        });
        this.add(monthBox);
        String[] dayItem  = new String[32];
        dayItem[0] = "Day";
        for(int i=1;i<32;i++){
            dayItem[i] =Integer.toString(i);
        }
        JComboBox dayBox = new JComboBox(dayItem);
        dayBox.setBounds(xTF+180,420,90,componentH);
        dayBox.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent day) {
                if (day.getStateChange() == ItemEvent.SELECTED) {
                    if(day.getItem().equals("Day")){
                        cusD=0;
                    }
                    else
                    cusD = Integer.parseInt(day.getItem().toString());
                }
            }
        });
        this.add(dayBox);
        String[] yearItem  = new String[132];
        yearItem[0] = "Year";
        int start = 2014;
        for(int i=0;i<131;i++){ 
            yearItem[i+1] =Integer.toString(start-i);
        }
        
        JComboBox yearBox = new JComboBox(yearItem);
        yearBox.setBounds(xTF+180+90,420,130,componentH);
        yearBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent yr) {
                if (yr.getStateChange() == ItemEvent.SELECTED) {

                    if(yr.getItem().equals("Year")){
                        cusY=0;
                    }
                    else
                    cusY = Integer.parseInt(yr.getItem().toString());

                }
            }
        });
        this.add(yearBox);
        JComboBox district = new JComboBox(new String[]{"District","Hong Kong","Kowloon","New Territories"});
        district.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                        inputDistrict = e.getItem().toString();

                }
            }
        });
        district.setBounds(xTF, y, wTF, componentH);
        this.add(district);
        address =new JTextArea();
        address.setLineWrap(true);
        address.setWrapStyleWord(true);
        address.setDocument(new TFLengthRestricted(231));
        address.setBounds(xTF, y+=componentH, wTF, 150);
        this.add(address);
        
        JButton regBut = new JButton("Create account");
        regBut.setBounds(185,y+=170,240,50);
        this.add(regBut);
        regBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent create) {
                int check = checkInput();
                if(AllDataAvailable()){
                    Customer newcus;
                    if(check==0){
                         newcus= new Customer(inputTF[0].getText(),CryptWithMD5.cryptWithMD5(new String(pw1TF.getPassword())),inputTF[1].getText(),inputTF[2].getText(),cusGender,inputTF[4].getText(),SystemMenu.isThisDateValid(inputDate).getSecond(),inputTF[5].getText());
                    }
                    else if(checkInput()==1){
                        newcus = new Customer(inputTF[0].getText(),CryptWithMD5.cryptWithMD5(new String(pw1TF.getPassword())),inputTF[1].getText(),inputTF[2].getText(),inputTF[3].getText(),cusGender,inputTF[4].getText(),SystemMenu.isThisDateValid(inputDate).getSecond(),inputTF[5].getText());
                    }else if(checkInput()==2){
                        newcus= new Customer(inputTF[0].getText(),CryptWithMD5.cryptWithMD5(new String(pw1TF.getPassword())),inputTF[1].getText(),inputTF[2].getText(),cusGender,inputTF[4].getText(),SystemMenu.isThisDateValid(inputDate).getSecond(),inputTF[5].getText(),inputDistrict,address.getText());
                    }
                    else{
                        newcus= new Customer(inputTF[0].getText(),CryptWithMD5.cryptWithMD5(new String(pw1TF.getPassword())),inputTF[1].getText(),inputTF[2].getText(),inputTF[3].getText(),cusGender,inputTF[4].getText(),SystemMenu.isThisDateValid(inputDate).getSecond(),inputTF[5].getText(),inputDistrict,address.getText());
                    }
                    
                    SystemMenu.createAC(newcus);
                    JOptionPane.showMessageDialog(null,"Account with ID: "+inputTF[0].getText()+ " created successfully.\n Back to Login Page");
                    setVisible(false);
                    LoginFrame.infoSP.setVisible(false);
                    LoginFrame.loginPanel.setVisible(true);
                }
            }
            
        }); 
        JButton backBut = new JButton("Back");
        backBut.setBounds(475,y,240,50);
        this.add(backBut);
        backBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent back) {
                setVisible(false);
                LoginFrame.infoSP.setVisible(false);
                LoginFrame.loginPanel.setVisible(true);
            }
            
        }); 
        
        //setToolTipText
       
        
        /*for(int i=0;i<8;i++){
            inputTF[0].setToolTipText(TTT[i]);
        }*/
        
    
    }
    public void itemStateChanged(ItemEvent event) {
        JRadioButton source = (JRadioButton) event.getSource();
        if (event.getStateChange() == ItemEvent.SELECTED) {
            System.out.println(source.getText());
            cusGender = source.getText().charAt(0);
        }
    }
    
    
    public static boolean isValidEmailAddress(String email) {
           String ePattern = EMAIL;
           java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
           java.util.regex.Matcher m = p.matcher(email);
           return m.matches();
    }
    // for the check of optional
    private int checkInput(){
         
        if((!inputDistrict.equals(EMPTY)&& address.getText().equals(EMPTY) )|| (inputDistrict.equals(EMPTY)&& !address.getText().equals(EMPTY))){
            check[9].setText("The address is not completed, should have both address and district");
        }
        if(inputDistrict.equals(EMPTY)||address.getText().equals(EMPTY)){
            if(inputTF[3].getText().equals(EMPTY)){
                return 0;//all empty
            }
            else{
                return 1;//no address but have nickname
            }
        }
        
        else if(!inputDistrict.equals(EMPTY)&&!address.getText().equals(EMPTY)){
            if(!inputTF[3].getText().equals(EMPTY)){
                return 3;//all not empty
            }
            else{
                return 2;//have address but no nickName
            }
        }
        return 0;
    }
    private boolean AllDataAvailable() {
        boolean available =true;
        final String patternW = "Pattern is not correct";
        if(inputTF[0].getText().equals(EMPTY)){
            check[0].setText("Login ID is required");
            available=false;
        }
        else if(SystemMenu.checkLogin(inputTF[0].getText())){
           check[0].setText("The ID already exist");
           available=false;
        }
        else if(inputTF[0].getText().toLowerCase().equals("admin")){
            check[0].setText("Account cannot be admin");
           available=false;
        }
        else if(!inputTF[0].getText().matches(ID)){
              check[0].setText(patternW);
              available = false;
        }   
       else 
            check[0].setText(EMPTY);
        
        if(pw1TF.getPassword().length==0){
            check[1].setText("Please create a password");
            available=false;
        }
        else if(!pw1TF.getPassword().toString().matches(PW)) {
             //check[1].setText(patternW);       
        }    
        else
            check[1].setText("");
           
        if(pw2TF.getPassword().length==0){
            check[2].setText("Please re-enter the password");
            available=false;
        }
        else if(!Arrays.toString(pw1TF.getPassword()).equals(Arrays.toString(pw2TF.getPassword()))&&pw1TF.getPassword().length>0){
            check[2].setText("Two password must be the same");
            available=false;
        }
        else{
            
            check[2].setText("");
        }
        if(inputTF[1].getText().equals(EMPTY) && inputTF[2].getText().equals(EMPTY)){
            check[3].setText("Name is required");
            available=false;
        }
        else if(inputTF[1].getText().equals(EMPTY)){
            check[3].setText("Surname is required");
            available=false;
        }
        else if(inputTF[2].getText().equals(EMPTY)){
            check[3].setText("Last name is required");
            available=false;
        }
        else if(!inputTF[1].getText().matches(NAME)||!inputTF[2].getText().matches(NAME)){
            check[3].setText(patternW);
        }
        else
            check[3].setText("");
        
        if (inputTF[4].getText().equals(EMPTY)){
            check[6].setText("Email is required");
            available=false;
        }
        else if(!isValidEmailAddress(inputTF[4].getText())){
            check[6].setText("Invalid Email Address");
            available=false;
        }
        else
            check[6].setText("");
        
        if(cusY==0||cusM.equals(null)||cusD==0){
            check[7].setText("Date of Birth is required");
            available=false;
        }
        else {
            inputDate = String.format("%d-%s-%02d",cusY,cusM,cusD);
            if(!SystemMenu.isThisDateValid(inputDate).getFirst()){
                    check[7].setText("Invalid Date");
                    available=false;
                }
            else
                check[7].setText("");
        }
                
        System.out.println(inputTF[5].getText());    
        if(inputTF[5].getText().equals(EMPTY)){
            check[8].setText("Phone number is required");
            available=false;
        }
        else if(!inputTF[5].getText().matches(PHONE) ){
                check[8].setText("Phone Number must be 8 digital value");
                available=false;
       }
        else
            check[8].setText("");
        if(address.getText().length()!=0&&!address.getText().matches(ADDRESS)){
			address.setText("");
            JOptionPane.showMessageDialog(null, "Address has incorrect pattern", null, JOptionPane.WARNING_MESSAGE);
        }
        if(!inputTF[3].getText().equals(EMPTY)&&!inputTF[3].getText().matches(NAME)){
			inputTF[3].setText("");
             JOptionPane.showMessageDialog(null, "Nick Name has invorrect pattern", null, JOptionPane.WARNING_MESSAGE);
        }
        return available;
    }
}
