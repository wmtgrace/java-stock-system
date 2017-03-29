import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

class LoadingFrame extends JFrame implements PropertyChangeListener{
    private final JProgressBar progressBar ;
    private final JFrame frame;
	public LoadingFrame(JFrame inFrame){
		Container contentPane;
		this.setSize(300,150);
		setTitle("Loading");
		setLocationRelativeTo( null ); // Center the frame
		
		contentPane = getContentPane();
		contentPane.setLayout(null);
		
		JLabel headln = new JLabel("Loading to login Page...");
		headln.setBounds(40,30,200,20);
                headln.setFont(new Font("Lucida Console",Font.PLAIN,13));
                progressBar = new JProgressBar(0,100);
		progressBar.setMinimum(0);
		progressBar.setBounds(40,60,200,20);
		contentPane.add(headln);
		contentPane.add(progressBar);
                Task task = new Task();
                task.addPropertyChangeListener(this);
                task.execute();
                frame = inFrame;
                this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                setVisible(true);
	}

    public JProgressBar getProgressBar() {
        return progressBar;
    }
        class Task extends SwingWorker<Void, Void> {
        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground() {
           for(int i=0;i<101;i++){
			final int percent = i;
			try {
                            Thread.sleep(30);
			} catch (InterruptedException e) {}
					setProgress(percent);
		}
           try {
                            Thread.sleep(50);
			} catch (InterruptedException e) {}
            return null;
        }

        /*
         * Executed in event dispatch thread
         */
        public void done() {
            dispose();
            frame.setVisible(true);
            
        }
    }
        @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);
            
        }
    }
	
   
	
}