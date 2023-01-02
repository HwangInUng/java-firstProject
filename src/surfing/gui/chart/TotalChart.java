package surfing.gui.chart;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.CategorySeries;
import org.knowm.xchart.CategorySeries.CategorySeriesRenderStyle;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.style.Styler.YAxisPosition;
import org.knowm.xchart.style.markers.SeriesMarkers;

import surfing.db.domain.Total;
import surfing.gui.MainPanel;
import surfing.gui.Page;

public class TotalChart extends JPanel {
	MainPanel mainPanel;
	CategoryChart chart;
	XChartPanel<CategoryChart> panel;

	int[] monthName = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };

	// 전체 테이블의 레코드를 담을 변수
	List<Total> totalList = new ArrayList<Total>();

	// 전체 테이블의 각각의 컬럼 정보를 담을 변수
	int[] nomalNum = new int[monthName.length];
	int[] coupleNum = new int[monthName.length];
	int[] singleNum = new int[monthName.length];
	int[] saleNum = new int[monthName.length];

	public TotalChart(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
		setPreferredSize(new Dimension(720, 370));
		setBackground(Page.BG_COLOR);

		// 차트 생성
		chart = getChart();

		panel = new XChartPanel(chart);
		panel.setPreferredSize(new Dimension(720, 360));
		
		setStyle();
		
		add(panel);
		
	}

	// 차트 생성 메서드
	public CategoryChart getChart() {
		// 반환값으로 사용할 차트 생성
		CategoryChart chart = new CategoryChartBuilder().width(720).height(360).title("").xAxisTitle("월")
				.yAxisTitle("매출액(만원)").build();

		// 범례 위치 지정
		chart.getStyler().setYAxisGroupPosition(0, YAxisPosition.Right);
		chart.getStyler().setYAxisGroupPosition(1, YAxisPosition.Left);
		chart.getStyler().setLegendPosition(LegendPosition.InsideNE);

		// 데이터 세팅
		setData();

		// 데이터 추가
		chart.setYAxisGroupTitle(1, "강습생 수(명)");
		chart.addSeries("입문", monthName, nomalNum).setYAxisGroup(1);
		chart.addSeries("커플", monthName, coupleNum).setYAxisGroup(1);
		chart.addSeries("개인", monthName, singleNum).setYAxisGroup(1);

		CategorySeries seriesSale = (CategorySeries) chart.addSeries("매출액", monthName, saleNum).setYAxisGroup(0);
		seriesSale.setChartCategorySeriesRenderStyle(CategorySeriesRenderStyle.Line);
		seriesSale.setMarker(SeriesMarkers.NONE);
		seriesSale.setLineColor(new Color(255, 110, 49));

		return chart;
	}
	
	//데이터 세팅
	public void setData() {
		totalList = mainPanel.totalDAO.selectAll();
		for (int i = 0; i < totalList.size(); i++) {
			Total total = totalList.get(i);
			nomalNum[i] = total.getNomal();
			coupleNum[i] = total.getCouple();
			singleNum[i] = total.getSingle();

			int sum = total.getBoard() + total.getFood() + total.getEtc();
			int result = sum / 10000;
			saleNum[i] = result;
		}
	}

	// 스타일 지정
	public void setStyle() {

		Color[] sliceColors = new Color[] { new Color(151, 222, 206), new Color(98, 182, 183),
				new Color(67, 154, 151) };

		// 차트 영역별 색 지정
		chart.getStyler().setSeriesColors(sliceColors);

		// 차트 전체 색상 및 범례 미표시
		chart.getStyler().setChartBackgroundColor(Page.BG_COLOR);
		chart.getStyler().setPlotBackgroundColor(Page.BG_COLOR);
		chart.getStyler().setPlotBorderVisible(false);
	}
}
