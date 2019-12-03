package env;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

public class Quadrant extends JButton {
	public Quadrant(ActionListener e, int bdSize, Difficulty diff) {
		super("");
		setFocusPainted(false);
		addActionListener(e);
		setBackground(new Color(52, 146, 235));
		setBorder(new LineBorder(Color.BLUE,1,false));
		setForeground(Color.white);
		setFont(new Font("Tahoma", Font.BOLD, (72*3)/bdSize ));
		setActionCommand(diff.toString());
	}
}
