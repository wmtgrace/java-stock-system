
import java.awt.*;
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
public class prodInfoP extends JPanel{
    private final int panelWidth,ButtonWidth=150,componentHeight=30;
    private final int labelX,textTFX;
    private int ButtonX,textTFWidth;
    private static final int labelWidth = 180;
    
    public final JTextField textField[];
    public final JLabel message[] ;
    public final JButton button[];
    public final JTextArea des;
    public final JComboBox categories;

    public prodInfoP(int w,String[] buttonT,boolean inmessage) {
        String[] columnT = {"Product ID :", "Product Name :","Categories","Description :", "Inventory :", "Price :"};

        categories = new JComboBox(new String[]{"Categories","Mouse","Keyboard","Notebook"});
        this.setLayout(null);
        textField = new JTextField[4];
        
        message = new JLabel[4];
        button = new JButton[buttonT.length];
        panelWidth=w;
        des = new JTextArea();
        textTFWidth = panelWidth/2;
        // make sure not too long
        if(textTFWidth>500){
            textTFWidth=400;
        }
        labelX = (panelWidth-20-textTFWidth-labelWidth)/2;
        textTFX = labelX+labelWidth+20;
        ButtonX = (panelWidth - buttonT.length*ButtonWidth -(buttonT.length-1)*10)/2-160;
        
        int y = -40;
        int k=0;//for message
        int textpos[] = {0,1,3,4};
        for (int i = 0; i < columnT.length; i++) {
            JLabel l = new JLabel(columnT[i]);

            l.setBounds(labelX, y+=50,labelWidth,componentHeight);
            //l.setBorder(new LineBorder(Color.red, 1));
            this.add(l);
            if(i==2){
                categories.setBounds(textTFX, y,textTFWidth,componentHeight);
                this.add(categories);
            }
            else if(i==3){
                des.setLineWrap(true);
                des.setWrapStyleWord(true);
                des.setDocument(new TFLengthRestricted(140));
                des.setBounds(textTFX,y,textTFWidth,100);
                this.add(des);
                y+=70;
            }
            else if(i<2){
                textField[i]=new JTextField();
                textField[i].setBounds(textTFX, y,textTFWidth,componentHeight);
                this.add(textField[i]);
            }
            //i=4,i=5
            else{
                textField[i-2]=new JTextField();
                textField[i-2].setBounds(textTFX, y,textTFWidth,componentHeight);
                this.add(textField[i-2]);
            }    
            if(i==0 ||i==2||i>3 && inmessage){
                message[k]=new JLabel();
                message[k].setFont(new Font("Lucida Console",Font.PLAIN,12));
                message[k].setBounds(textTFX, y+componentHeight,textTFWidth,15);
                message[k].setForeground(Color.red);
                //message[k].setBorder(new LineBorder(Color.red, 1));
                this.add(message[k]);
                k++;
            }
            
        }
        y+=60;
		
        for(int i = 0;i< buttonT.length;i++){
            button[i] = new JButton(buttonT[i]);
            button[i].setBounds(ButtonX+=160,y,ButtonWidth,componentHeight);
            this.add(button[i]);
        }
        textField[0].setDocument(new TFLengthRestricted(30));
        textField[1].setDocument(new TFLengthRestricted(50));
        
        
    }
    public int getLabelx(){
        return this.labelX;
    }


}
    
