import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Random;
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
public class FirstPanel extends JPanel {
    public JButton enterButton;
    //Constructs the layout.
    public FirstPanel() {
        
        //set null layout and use set bound 
        this.setLayout(null);
        JLabel icon = new JLabel(new ImageIcon("300logo.gif"));
        icon.setBounds(20, 20, 300, 300);
        JLabel titleName= new JLabel("GJLR IT LIMITED");
       
        LoginFrame.newfont = LoginFrame.newfont.deriveFont(Font.PLAIN,70);
        titleName.setFont(LoginFrame.newfont);
        titleName.setForeground(new Color(247,152,57));
        titleName.setBounds(340, 150, 530, 70);
        
        JLabel idea = new JLabel();
        idea.setText("<html><b><font size=5>-do Good Job,being the Leading Role of IT</font></font></b></html>");

        idea.setForeground(new Color(247,152,57));
        idea.setBounds(340, 220, 500, 40);
        enterButton = new JButton("Enter");
        enterButton.setBounds(390,420,120,40);
        this.setBackground(Color.white);
        this.add(icon);
        this.add(titleName);
        this.add(idea);
        this.add(enterButton);
        this.setVisible(true);
        if(SystemMenu.openfile(this,"admin.txt")){
            SystemMenu.openfile();
        }
    }

  
}
