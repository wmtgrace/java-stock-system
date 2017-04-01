
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.*;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
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
public class LoginFrame extends JFrame {
    private static final int FRAME_WIDTH    = 900;
    private static final int FRAME_HEIGHT   = 600;
    private final Container contentPane;
    public static JPanel loginPanel =new JPanel();
    private RegPanel regP;
    private JTextField loginTF;
    private JPasswordField pwTF;
    private JFrame mainF;
    public static Font newfont;//set this as class variable and public so that it can be easy to get by other class
    public static JScrollPane infoSP;
    public LoginFrame(){
        this.setUIFont (new javax.swing.plaf.FontUIResource(new Font("Lucida Console", 0, 20)));//set default font
        //ImageIcon img = new ImageIcon("16logo.jpg");
       // this.setIconImage(img.getImage());
        //this.setUndecorated(true);
        setTitle("Login System");
        setSize(FRAME_WIDTH,FRAME_HEIGHT);
	setLocationRelativeTo( null ); // Center the frame
        setResizable(false);
        //register 'Exit upon closing' as a default close operation
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        contentPane = getContentPane();
        
        try {
            newfont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("spacerangeracad.ttf"));
            firstP();
            
        } catch (FontFormatException | IOException ex ) {
            
        } 
        
        loginP();

    }
    private void firstP(){
        
        final FirstPanel firstPanel = new FirstPanel();
        contentPane.add(firstPanel);

        firstPanel.enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                firstPanel.setVisible(false);
                contentPane.add(loginPanel);
                loginPanel.setVisible(true);
            }
        }); 
    }
    private void loginP(){
        
        final Music loginsd= new Music("nokia.wav");
		loginPanel.setBackground(Color.white);
        loginPanel.setLayout(null);
        JLabel titleName = new JLabel("GJLR IT LIMITED",JLabel.CENTER);
        newfont=newfont.deriveFont(Font.PLAIN,100);
        titleName.setFont(newfont);
        titleName.setBounds(50, 50,800,130);
        loginPanel.add(titleName);
        
        JLabel loginID = new JLabel("Login ID:");
        loginID.setFont(new Font("Lucida Console",Font.PLAIN,24));
        loginID.setBounds(170,200,150,50);
        loginPanel.add(loginID);
        
        loginTF = new JTextField();
        loginTF.setBounds(310,200,400,50);
        loginTF.setFont(new Font("Lucida Console",Font.PLAIN,24));
        loginPanel.add(loginTF);
        
        JLabel pwLbl = new JLabel("Password:");
        pwLbl.setFont(new Font("Lucida Console",Font.PLAIN,24));
        pwLbl.setBounds(170,300,150,50);
        loginPanel.add(pwLbl);
        
        pwTF = new JPasswordField();
        pwTF.setFont(new Font("Lucida Console",Font.PLAIN,24));
        pwTF.setBounds(310,300,400,50);
        loginPanel.add(pwTF);
       
        JButton loginBut = new JButton("Login");
        loginBut.setFont(new Font("Lucida Console",Font.PLAIN,24));
        loginBut.setBounds(225,400,210,50);
        loginPanel.add(loginBut);
        
        JButton regBut = new JButton("Registration");
        regBut.setFont(new Font("Lucida Console",Font.PLAIN,24));
        regBut.setBounds(475,400,210,50);
        loginPanel.add(regBut);
        
        loginBut.addActionListener(new ActionListener() {
             @Override
            public void actionPerformed(ActionEvent e) {
                
                    if(SystemMenu.login(loginTF.getText(), CryptWithMD5.cryptWithMD5(new String(pwTF.getPassword())))){
                        
                        setVisible(false);
                        
                        if(loginTF.getText().equals("admin")){
                            Font filefont=new Font("Lucida Console",Font.PLAIN,14);//font use in the menuBar
                            mainF= new MainFrame(loginTF.getText());
                            
                            JMenuBar jMenuBar = new JMenuBar();
                            mainF.setJMenuBar(jMenuBar);
                            JMenu fileMenu= new JMenu("File");
                      
                            JMenuItem save = new JMenuItem("Save");
                            JMenuItem saveas = new JMenuItem("Save As...");
 
                            fileMenu.add(save);
                            fileMenu.add(saveas);
                            
                            JMenu exitMenu= new JMenu("Exit");
                            JMenuItem logout = new JMenuItem("Logout");
                            JMenuItem quit = new JMenuItem("Quit the system");
                            exitMenu.add(logout);
                            exitMenu.add(quit);
                            

                            save.setFont(filefont);
                            saveas.setFont(filefont);
                            fileMenu.setFont(filefont);
                            exitMenu.setFont(filefont);
                            logout.setFont(filefont);
                            quit.setFont(filefont);
                            
                            jMenuBar.add(fileMenu);
                            jMenuBar.add(exitMenu);
                            
                            // Set keyboard accelerators
                            
                            save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
                            saveas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK|ActionEvent.ALT_MASK));
                            logout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
                            quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
                            
                            //set ActionListener
                            save.addActionListener(new ActionListener() { 
                             @Override 
                            public void actionPerformed(ActionEvent e) { 
                                if(SystemMenu.savefile()){
                                    JOptionPane.showMessageDialog(null,"File Saved");
                                }
                            } 
                            });
                            saveas.addActionListener(new ActionListener() { 
                             @Override 
                            public void actionPerformed(ActionEvent e) { 
                                String file1;
                                JFileChooser chooser = new JFileChooser("Save Product File");
                                
                                int status = chooser.showSaveDialog(mainF);
                                if(status == JFileChooser.APPROVE_OPTION) {
                                    file1=chooser.getSelectedFile().getAbsolutePath();
                                    if(SystemMenu.saveprodfile(file1)){
                                        JOptionPane.showMessageDialog(null,"Product File Saved in" +file1);
                                    }
                                }
                            } 
                            });
                            logout.addActionListener(new ActionListener() { 
                             @Override 
                            public void actionPerformed(ActionEvent e) { 
                                int confirm = JOptionPane.showConfirmDialog(null,"Are you Sure to Logout?","Exit Confirmation", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                                if (confirm == JOptionPane.YES_OPTION) {
                                    //SystemMenu.savefile();
                                    mainF.setVisible(false);
                        
                                    Main.test.setVisible(true);
                                    loginPanel.setVisible(true);
                                }
                            } 
                            });
                            quit.addActionListener(new ActionListener() { 
                             @Override 
                            public void actionPerformed(ActionEvent e) { 
                                int confirm = JOptionPane.showConfirmDialog(null,"Are you Sure to quit the system?","Exit Confirmation", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                                if (confirm == JOptionPane.YES_OPTION) {
                                    if(SystemMenu.savefile()){
                                        JOptionPane.showMessageDialog(null,"File Saved");
                                    }
                                    System.exit(0);
                                }
                            } 
                            });
                        }
                        else
                            mainF = new MainFrame(loginTF.getText());
                        //makeFrameFullSize(cusF);
                        WindowListener exitListener = new WindowAdapter() {

                           @Override
                            public void windowClosing(WindowEvent e) {
                                int confirm = JOptionPane.showConfirmDialog(mainF,"Are You Sure to Close this Application and file not save?","Exit Confirmation", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                                if (confirm == JOptionPane.YES_OPTION) {
                                    if(SystemMenu.savefile()){
                                        JOptionPane.showMessageDialog(null,"File Saved");
                                    }
                                    System.exit(0);
                                }
                            }
                        };
                        mainF.addWindowListener(exitListener);
                        mainF.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                        //clear the content for the next input
                        loginTF.setText("");
                        pwTF.setText("");
                        
                        loginsd.musicplay();
                        final LoadingFrame load= new LoadingFrame(mainF);
                        

                    }
                        
                
            }
        }); 
        
        regBut.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                loginPanel.setVisible(false);
                regP = new RegPanel();
                regP.setPreferredSize(new Dimension(FRAME_WIDTH,FRAME_HEIGHT+180));
                int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
                int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
                infoSP=new JScrollPane(regP,v,h);
                contentPane.add(infoSP);
                //clear the content for the next input
                loginTF.setText("");
                pwTF.setText("");
                infoSP.setVisible(true);
                regP.setVisible(true);
            }
        }); 
        
    }
    
    
   private static void setUIFont(javax.swing.plaf.FontUIResource f)
    {
        UIManager.put("Label.font", f);
        UIManager.put("Button.font", f);
        UIManager.put("TextField.font", f);
        UIManager.put("TextArea.font", f);
        UIManager.put("PasswordField.font", f);
        UIManager.put("ComboBox.font", f);
        UIManager.put("Table.font",new javax.swing.plaf.FontUIResource(new Font("Arial", 0, 15)));
        UIManager.put("TableHeader.font",new javax.swing.plaf.FontUIResource(new Font("Arial", 0, 15)));
    }
    
}
