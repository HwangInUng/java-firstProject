package surfing.gui.main;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

import surfing.gui.Page;

public class MainLabel extends JLabel{
	public MainLabel(String title, int width) {
		super(title, width);
		setPreferredSize(new Dimension(170, 30));
		setBorder(Page.MAINLABEL_LINEBORDER);
		setFont(new Font("배달의민족 연성", Font.BOLD, 15));
	}
}
