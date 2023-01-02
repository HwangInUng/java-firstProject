package surfing.gui.chart;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.PieStyler.LabelType;

import surfing.gui.MainPanel;
import surfing.gui.Page;

public class Pie2 extends JPanel {
	MainPanel mainPanel;
	
	PieChart pie;
	XChartPanel<PieChart> panel;
	
	//각 차트의 데이터를 추가할 리스트보유
	public List<Integer> trainingNum = new ArrayList<Integer>();
	public List<Integer> saleNum = new ArrayList<Integer>();
	public int sum;
	
	public static final int CHART_WIDTH = 220;
	
	public Pie2(MainPanel mainPanel, String name) {
		this.mainPanel = mainPanel;
		
		setPreferredSize(new Dimension(CHART_WIDTH, 210));

		// 차트 생성 - 매개변수로 해당 차트의 구분(강습, 매출)을 지정
		pie = getChart(name);
		
		// 차트 패널 생성
		panel = new XChartPanel<PieChart>(pie);
		panel.setPreferredSize(new Dimension(CHART_WIDTH, 210));
		
		//스타일 지정
		setStyle();

		// 차트 부착
		add(panel);

		panel.setVisible(true);
	}

	// 차트 생성 메서드
	public PieChart getChart(String name) {
		// 차트 객체 생성
		PieChart chart = new PieChartBuilder().width(CHART_WIDTH).height(200).title("").build();
		
		//매개변수 값에 따라 현황이 필요한 메서드 구분 호출 후 차트영역 분배
		if(name.equals("강습")) {
			trainingNum = mainPanel.totalDAO.selectTrainingNum();
			chart.addSeries("입문", trainingNum.get(0));
			chart.addSeries("커플", trainingNum.get(1));
			chart.addSeries("개인", trainingNum.get(2));
			sum = trainingNum.get(0) + trainingNum.get(1) + trainingNum.get(2);
		} else if(name.equals("매출")) {
			saleNum = mainPanel.totalDAO.selectSaleNum();
			chart.addSeries("보드", saleNum.get(0));
			chart.addSeries("식품", saleNum.get(1));
			chart.addSeries("기타", saleNum.get(2));
			sum = saleNum.get(0) + saleNum.get(1) + saleNum.get(2);
		}
		
		
		return chart;
	}

	// 차트 스타일 지정
	public void setStyle() {
		
		Color[] sliceColors = new Color[] { new Color(151, 222, 206), new Color(98, 182, 183),
				new Color(67, 154, 151) };
		
		//차트 라벨 스타일
		pie.getStyler().setLabelType(LabelType.NameAndPercentage);
		pie.getStyler().setLabelsFontColorAutomaticEnabled(false);
		pie.getStyler().setLabelsFontColor(Color.black);
		pie.getStyler().setLabelsDistance(0.8);
		
		//차트 영역별 색 지정
		pie.getStyler().setSeriesColors(sliceColors);
		
		//차트 전체 색상 및 범례 미표시
		pie.getStyler().setChartBackgroundColor(Page.BG_COLOR);
		pie.getStyler().setPlotBackgroundColor(Page.BG_COLOR);
		pie.getStyler().setPlotBorderVisible(false);
		pie.getStyler().setLegendVisible(false);
	}

}
