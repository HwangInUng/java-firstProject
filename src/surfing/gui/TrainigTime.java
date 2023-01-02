package surfing.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class TrainigTime extends JPanel{
	JLabel la_time, la_nomal, la_couple, la_single, la_total;
	int nomalNum, coupleNum, singleNum;
	
	public TrainigTime(String title) {
		la_nomal = new JLabel("입문 강습 : ");
		la_couple = new JLabel("커플 강습 : ");
		la_single = new JLabel("개인 강습 : ");
		la_total = new JLabel("총 강습생 : ");
		
		la_nomal.setFont(new Font("배달의민족 연성", Font.BOLD, 15));
		la_couple.setFont(new Font("배달의민족 연성", Font.BOLD, 15));
		la_single.setFont(new Font("배달의민족 연성", Font.BOLD, 15));
		la_total.setFont(new Font("배달의민족 연성", Font.BOLD, 15));
		
		add(la_nomal);
		add(la_couple);
		add(la_single);
		add(la_total);
		
		setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
		setPreferredSize(new Dimension(190, 170));
		
		TitledBorder border = new TitledBorder(title);
		border.setBorder(Page.PANEL_LINEBORDER);
		border.setTitleFont(new Font("배달의민족 연성", Font.BOLD, 20));
		setBorder(border);
	}
}
