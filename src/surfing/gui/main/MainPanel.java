package surfing.gui.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Label;
import java.text.NumberFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;

import surfing.db.repository.TotalDAO;
import surfing.gui.Page;
import surfing.gui.SurfingApp;
import surfing.gui.chart.Pie2;
import surfing.gui.chart.TotalChart;

public class MainPanel extends Page {
	public TotalDAO totalDAO;

	// 상단 분야별 현황 필드
	private MainTopPanel p_total, p_training, p_sales;
	private MainLabel la_total, la_training, la_sales;
	private JLabel la_totalTraining, la_totalSales;
	private Pie2 training_pie, sales_pie;
	private TotalChart totalChart;

	// 중앙 종합현황 필드
	private JPanel p_yearTotal;
	private JLabel la_yearTotal;

	// 사이즈 조절을 위한 상수
	public static final int TOTALLABEL_WIDTH = 170;
	public static final int TOTALLABEL_HEIGHT = 60;
	public static final int TOP_WIDTH = 240;
	public static final int TOP_HEIGHT = 260;
	public static final int CENTER_WIDTH = 750;
	public static final int CENTER_HEIGHT = 420;

	public MainPanel(SurfingApp app) {
		super(app);
		totalDAO = new TotalDAO();

		// 원하는 상태로 배치하기 위하여 FlowLayout 생성자 활용
		container.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 30));

		// 상단 분야별 현황 필드
		p_total = new MainTopPanel(app, TOP_WIDTH, TOP_HEIGHT);
		la_total = new MainLabel("총 강습 및 매출 현황", Label.WIDTH / 2);
		p_training = new MainTopPanel(app, TOP_WIDTH, TOP_HEIGHT);
		la_training = new MainLabel("강습 형태별 현황", Label.WIDTH / 2);
		training_pie = new Pie2(this, "강습");
		p_sales = new MainTopPanel(app, TOP_WIDTH, TOP_HEIGHT);
		la_sales = new MainLabel("매출 구분별 현황", Label.WIDTH / 2);
		sales_pie = new Pie2(this, "매출");

		// 차트 생생 이후 데이터 종합을 위해 생성위치 조정
		NumberFormat formatter = NumberFormat.getNumberInstance(); // 세자리마다 ","로 구분해주기 위한 객체 생성
		la_totalTraining = new JLabel("<html>총 강습생 <br/>" + formatter.format(training_pie.sum) + "명</html>", Label.WIDTH / 2);
		la_totalSales = new JLabel("<html>총 매출액 <br/>" + formatter.format(sales_pie.sum) + "원</html>", Label.WIDTH / 2);

		p_total.setLayout(null);
		la_total.setBounds((TOP_WIDTH-TOTALLABEL_WIDTH)/2, 10, TOTALLABEL_WIDTH, 30);
		la_totalTraining.setOpaque(true);
		la_totalTraining.setBackground(new Color(151, 222, 206));
		la_totalTraining.setBounds((TOP_WIDTH-TOTALLABEL_WIDTH)/2, 80, TOTALLABEL_WIDTH, TOTALLABEL_HEIGHT);
		la_totalTraining.setFont(new Font("배달의민족 연성", Font.BOLD, 15));
		la_totalSales.setOpaque(true);
		la_totalSales.setBackground(new Color(151, 222, 206));
		la_totalSales.setBounds((TOP_WIDTH-TOTALLABEL_WIDTH)/2, 160, TOTALLABEL_WIDTH, TOTALLABEL_HEIGHT);
		la_totalSales.setFont(new Font("배달의민족 연성", Font.BOLD, 15));

		// 총 현황
		p_total.add(la_total);
		p_total.add(la_totalTraining);
		p_total.add(la_totalSales);

		// 강습 및 판매 현황
		p_training.add(la_training);
		p_training.add(training_pie);
		p_sales.add(la_sales);
		p_sales.add(sales_pie);

		// 중앙 연간 총 현황 필드
		p_yearTotal = new MainTopPanel(app, CENTER_WIDTH, CENTER_HEIGHT);
		la_yearTotal = new JLabel("2022년 강습 및 매출 종합현황", Label.WIDTH / 2);
		totalChart = new TotalChart(this);

		la_yearTotal.setPreferredSize(new Dimension(250, 30));
		la_yearTotal.setBorder(Page.MAINLABEL_LINEBORDER);
		la_yearTotal.setFont(new Font("배달의민족 연성", Font.BOLD, 15));

		p_yearTotal.add(la_yearTotal);
		p_yearTotal.add(totalChart);

		// 컨테이너에 부착
		container.add(p_total);
		container.add(p_training);
		container.add(p_sales);
		container.add(p_yearTotal);

		add(container);
	}
}
