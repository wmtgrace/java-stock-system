
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ManTing
 */
public class CustomerInformationPanel extends JPanel{
    
    private final static String PHONE = "[\\d]{8}";
    private final static String PW ="[\\w.!#$%&'*+/=?^_`{|}~-]{3,35}";
    private final static String NAME ="\\w+";//\w= word character: [a-zA-Z_0-9]
    //private final int wLbl =220,componentH=30,wTF = 400,xLbl=50,xTF=270,xCLbl=675,wCLbl=180;//
    
    public CustomerInformationPanel(Customer inCus) {
        final Customer cus = inCus;
        
        JLabel title = new JLabel("Personal Information",JLabel.CENTER);
        title .setFont(new Font("Lucida Console",Font.BOLD,30));
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        Dimension TF = new Dimension(400,30);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridx = 0;
        c.gridy = 0;
        this.add(title,c);

        JLabel loginIDLabel  = new JLabel("Login ID:" + cus.getLoginID());
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        this.add(loginIDLabel,c);

        c.gridx=0;
        c.gridy=2;
        JLabel nameLabel = new JLabel("Name: " + cus.getLName() + ' '+ cus.getFName());        
        this.add(nameLabel,c);
        
        c.gridx=1;
        c.gridy=2;
        c.gridwidth = 1;
        JLabel genderLabel = new JLabel ("Gender: " + cus.getGender());
        this.add(genderLabel,c);

        c.gridx=0;
        c.gridy=3;
        c.gridwidth = 1;
        JLabel DOBLabel = new JLabel("Date of Birth: " + cus.getDOB());
        this.add(DOBLabel, c);
        
        c.gridx=1;
        c.gridy=3;
        JLabel ageLabel = new JLabel("Age: " + Integer.toString(cus.getAge()));
        this.add(ageLabel,c);
        
        c.gridx=0;
        c.gridy=4;
        c.gridwidth = 1;
        JLabel phoneLabel = new JLabel("Phone Number: ");
        this.add(phoneLabel,c);
        
        c.gridx=1;
        c.gridy=4;
        final JTextField phoneTF = new JTextField(cus.getPhoneNum());
        phoneTF.setPreferredSize(TF);
        this.add(phoneTF,c);
        
        c.gridx=2;
        c.gridy=4;
        JButton phoneBut = new JButton("Change");
        this.add(phoneBut,c);
        
        phoneBut.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!phoneTF.getText().matches(PHONE)){
                    JOptionPane.showMessageDialog(null, "Phone number must be 8 digital value.", null, JOptionPane.WARNING_MESSAGE);
                    
                } else{
                    cus.setPhoneNum(phoneTF.getText());
                    JOptionPane.showMessageDialog(null, "Phone number changed.");
                }
                
            }
        });
        
        c.gridx=0;
        c.gridy=5;
        JLabel nicknameLabel = new JLabel("Nickname: ");
        this.add(nicknameLabel,c);
        
        c.gridx=1;
        c.gridy=5;
        
        final JTextField nicknameTF = new JTextField();
        nicknameTF.setDocument(new TFLengthRestricted(35));
        nicknameTF.setText(cus.getNickName());
        
        nicknameTF.setPreferredSize(TF);
        this.add(nicknameTF,c);
        
        c.gridx=2;
        c.gridy=5;
        JButton nicknameBut = new JButton("Change");
        this.add(nicknameBut,c);
        
        nicknameBut.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                if(nicknameTF.getText().matches(NAME)){
                    cus.setNickName(nicknameTF.getText());
                    JOptionPane.showMessageDialog(null, "Nickname changed.");
                    MainFrame.cusName =nicknameTF.getText();
                }
                else{
                    JOptionPane.showMessageDialog(null, "Wrong Format of Nick Name");
                }
                
            }
        });
        
        c.gridx=0;
        c.gridy=6;
        JLabel emailLabel = new JLabel("Email: ");
        this.add(emailLabel,c);
        
        c.gridx=1;
        c.gridy=6;
        final JTextField emailTF = new JTextField(cus.getEmail());
        emailTF.setPreferredSize(TF);
        this.add(emailTF,c);
        
        c.gridx=2;
        c.gridy=6;
        JButton emailBut = new JButton("Change");
        this.add(emailBut,c);
        
        emailBut.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                if(RegPanel.isValidEmailAddress(emailTF.getText())){
                    cus.setEmail(emailTF.getText());
                    JOptionPane.showMessageDialog(null, "Email changed.");
                }
                else{
                    JOptionPane.showMessageDialog(null, "Invalid email.", null, JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        
        
        c.gridx=0;
        c.gridy=7;
        JLabel addressLabel = new JLabel("Address:");
        this.add(addressLabel,c);
        
        c.gridx=1;
        c.gridy=7;
        final JComboBox districtCB = new JComboBox(new String[]{"District","Hong Kong","Kowloon","New Territories"});
        districtCB.setSelectedItem(cus.getDistrict());
        this.add(districtCB,c);
        
        c.gridx=2;
        c.gridy=7;
        c.gridheight=2;
        JButton addressBut = new JButton("Change");
        this.add(addressBut,c);
        
        c.ipady =100;
        c.gridx=1;
        c.gridy=8;
        
        final JTextArea addressTA = new JTextArea(cus.getAddress());
        this.add(addressTA,c);
        addressTA.setPreferredSize(TF);
        
        addressBut.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if(addressTA.getText().equals("") || districtCB.getSelectedItem().toString().equals(("District"))){
                    JOptionPane.showMessageDialog(null, "Address/District cannot be empty.", null, JOptionPane.WARNING_MESSAGE);
                }
                else{
                    cus.setDistrict(districtCB.getSelectedItem().toString());
                    cus.setAddress(addressTA.getText());
                    JOptionPane.showMessageDialog(null, "Address changed.");
                }
            }
        });
        c.ipady =0;
        c.gridx=0;
        c.gridy=10;
        JLabel pwLabel = new JLabel("Password: ");
        this.add(pwLabel,c);
        
        c.gridx=1;
        c.gridy=10;
        JButton pwBut = new JButton("Change");
        this.add(pwBut,c);
        
        pwBut.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                String oldPW, newPW, confirmNewPW;
                boolean oldPWMatch = false;
                JLabel oldPWLabel = new JLabel("Old Password:");
                JTextField oldPWF = new JPasswordField();
                Object[] oldPWObj = {oldPWLabel,oldPWF};
                int proceed = JOptionPane.showConfirmDialog(null, oldPWObj, null, JOptionPane.OK_CANCEL_OPTION);
                oldPW = CryptWithMD5.cryptWithMD5(oldPWF.getText());
                //oldPW = CryptWithMD5.cryptWithMD5(JOptionPane.showInputDialog(null, "Old password:"));
                if(oldPW.equals(cus.getPassword())){
                        oldPWMatch = true;
                }
                else if(proceed==JOptionPane.OK_OPTION){
                    JOptionPane.showMessageDialog(null, "Password is incorrect.", null, JOptionPane.WARNING_MESSAGE);
                }
                
                if(oldPWMatch&&proceed==JOptionPane.OK_OPTION){
                    JLabel newPWLabel = new JLabel("New Password:");
                    JTextField newPWF = new JPasswordField();
                    Object[] newPWObj = {newPWLabel,newPWF};
                    proceed = JOptionPane.showConfirmDialog(null, newPWObj, null, JOptionPane.OK_CANCEL_OPTION);
                    newPW = newPWF.getText();
                    if(!newPW.matches(PW)&&proceed==JOptionPane.OK_OPTION ){
                        JOptionPane.showMessageDialog(null, "Wrong Format of Password.");
                    }
                    else{
                        if(proceed==JOptionPane.OK_OPTION ){
                            JLabel confirmNewPWLabel = new JLabel("Confirm New Password:");
                            JTextField confirmNewPWF = new JPasswordField();
                            Object[] confirmNewPWObj = {confirmNewPWLabel,confirmNewPWF};
                            JOptionPane.showConfirmDialog(null, confirmNewPWObj, null, JOptionPane.OK_CANCEL_OPTION);
                            confirmNewPW = confirmNewPWF.getText();
                        
                            if(newPW.equals(confirmNewPW)){
                                cus.setPassword(CryptWithMD5.cryptWithMD5(newPW));
                                JOptionPane.showMessageDialog(null, "Password changed.");
                            }
                            else{
                                JOptionPane.showMessageDialog(null, "Password doesn't match.", null, JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    }//newPW = JOptionPane.showInputDialog(null, "New password:");
                    //confirmNewPW = JOptionPane.showInputDialog(null, "Enter new password again:");
                    
                }
                
            }
        });
        
    }
}
