package GUI;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;

public class NumberButton {
	JButton button = new JButton();
	String behalf;

    public NumberButton(String str) { //建立constructor 
    	behalf=str;
    	button.setText(str);
    	button.setPreferredSize(new Dimension(30,30));
		button.setBackground(Color.gray);
		
    }
  

    


}
