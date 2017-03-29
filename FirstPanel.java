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
public class FirstPanel extends JPanel implements PropertyChangeListener{
    public JButton enterButton;
    public static JTextArea loading;
    private JProgressBar progressBar;
    private Task task;
    private boolean printcus = false,printprod = false,printtrans = false;
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
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setBounds(20,350,470,30);
        this.add(progressBar);
        loading = new JTextArea();
        loading.setEditable(false);
        loading.setBorder(new LineBorder(Color.LIGHT_GRAY,1));
        loading.setLineWrap(true);
        loading.setWrapStyleWord(true);
        loading.setBounds(20,380,470,150);
        loading.setFont(new Font("Arial",Font.PLAIN,15));
        JScrollPane loadSP = new JScrollPane(loading);
        loadSP.setBounds(20,380,470,150);
        enterButton = new JButton("Enter");
        enterButton.setBounds(625,420,120,40);
        enterButton.setEnabled(false);
        this.setBackground(Color.white);
        this.add(icon);
        this.add(titleName);
        this.add(idea);
        this.add(loadSP);
        this.add(enterButton);
        this.setVisible(true);
        if(SystemMenu.openfile(this,"admin.txt")){
            SystemMenu.openfile();
            task = new Task();
            task.addPropertyChangeListener(this);
            task.execute();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);
            
            if(progress>30 && !printcus ){
                loading.append(SystemMenu.cusfile.toString());
                printcus=true;
                
            }
            if(progress>50&& !printprod){
                loading.append(SystemMenu.prodfile.toString());
                printprod=true;
            }
            if(progress>90 && !printtrans){
                loading.append(SystemMenu.tranfile.toString());
                printtrans=true;
            }
            if(progress>98&& printtrans){
                loading.append(SystemMenu.other.toString());
                printtrans=false;
            }
            
            
        } 
    }
    class Task extends SwingWorker<Void, Void> {
        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground() {
            Random random = new Random();
            int progress = 0;
            //Initialize progress property.
            setProgress(0);
            while (progress < 100) {
                //Sleep for up to one second.
                try {
                    Thread.sleep(random.nextInt(300));
                } catch (InterruptedException ignore) {}
                //Make random progress.
                progress += random.nextInt(10);
                setProgress(Math.min(progress, 100));
            }
            return null;
        }

        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();
            enterButton.setEnabled(true);
            setCursor(null); //turn off the wait cursor
            
            loading.append("Finish reading file\n");
        }
    }
}
