package surfing.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

public class MainLabel extends JLabel{
	public MainLabel(String str, int n) {
		super(str, n);
		setPreferredSize(new Dimension(170, 30));
		setBorder(Page.MAINLABEL_LINEBORDER);
		setFont(new Font("배달의민족 연성", Font.BOLD, 15));
	}
}
