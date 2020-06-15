package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class testGUI {
	JFrame guiFrame = new JFrame("mm");
	JLabel guiLabel = new JLabel("mm");
	JButton guiButton = new JButton("mm");
	JPanel guiPanel = new JPanel();

	
void creatgui() {
	    guiPanel.setLayout(null);
    	guiPanel.setPreferredSize(new Dimension(300,300));
    	guiButton.setBounds(100, 200, 100, 100);
    	guiLabel.setBounds(120, 50,200, 20);
	    guiPanel.add(guiButton);
	    guiPanel.add(guiLabel);
		guiButton.addActionListener(new ButtonPressed());

	    guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.add(guiPanel);
		guiFrame.pack();
		guiFrame.setLocationRelativeTo(null);
		guiFrame.setVisible(true);
		
}
    public class ButtonPressed implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			guiLabel.setText("get hit");
		    	
		}
    	
    }
	public static void main(String[] args) {
		new testGUI().creatgui();

	}

}
