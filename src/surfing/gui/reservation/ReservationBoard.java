package surfing.gui.reservation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import surfing.db.domain.reservation.Reservation;
import surfing.db.model.BoardModel;
import surfing.gui.Page;
import surfing.gui.SurfingApp;

public class ReservationBoard extends Page {
	private ReservationPanel reservationPanel;

	// 상단 필드
	private JLabel la_date;

	// 중앙필드
	public JTable reservationTable;
	public BoardModel boardModel;
	private JScrollPane reservationScroll;

	// 동측 필드
	private JPanel p_east;
	private JLabel la_reservationCount, la_reservationNumber;
	private TrainigTime trainigTime_10, trainigTime_12, trainigTime_3;
	private JButton bt_back;

	public ReservationBoard(SurfingApp app, ReservationPanel reservationPanel) {
		super(app);
		this.reservationPanel = reservationPanel;

		container.setLayout(new BorderLayout());

		// 상단필드 정의
		la_date = new JLabel("당일 예약현황");
		la_date.setFont(new Font("배달의민족 연성", Font.BOLD, 25));

		p_north.add(la_date);

		// 중앙필드 정의
		reservationTable = new JTable(boardModel = new BoardModel());
		reservationScroll = new JScrollPane(reservationTable);

		reservationScroll.setPreferredSize(new Dimension());

		// 동측 필드 정의
		p_east = new JPanel();
		la_reservationCount = new JLabel("총 예약 건수 : ");
		la_reservationNumber = new JLabel("총 예약 인원 : ");
		trainigTime_10 = new TrainigTime("10시");
		trainigTime_12 = new TrainigTime("12시");
		trainigTime_3 = new TrainigTime("3시");
		bt_back = new JButton("이전");

		la_reservationCount.setFont(new Font("배달의민족 연성", Font.BOLD, 15));
		la_reservationNumber.setFont(new Font("배달의민족 연성", Font.BOLD, 15));
		p_east.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 15));
		p_east.setPreferredSize(new Dimension(200, 700));
		p_east.setBorder(Page.PANEL_LINEBORDER);

		p_east.add(la_reservationCount);
		p_east.add(la_reservationNumber);
		p_east.add(trainigTime_10);
		p_east.add(trainigTime_12);
		p_east.add(trainigTime_3);
		p_east.add(bt_back);

		container.add(p_north, BorderLayout.NORTH);
		container.add(reservationScroll);
		container.add(p_east, BorderLayout.EAST);

		add(container);

		setVisible(false);

		bt_back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				reservationPanel.container.setVisible(true);
			}
		});
	}

	// 현황정보 출력 메서드
	public void showInfo(List<Reservation> list) {
		int sum = 0;
		for (int i = 0; i < list.size(); i++) {
			// 매개변수로 넘겨받은 list에서 객체를 1건씩 추출
			Reservation rsv = list.get(i);

			// 강습시간별 조건문 내에서 인원을 구해오는 메서드 실행
			if (rsv.getRsv_time() == 10) {
				getRsvNumber(rsv, trainigTime_10);
			} else if (rsv.getRsv_time() == 12) {
				getRsvNumber(rsv, trainigTime_12);
			} else if (rsv.getRsv_time() == 3) {
				getRsvNumber(rsv, trainigTime_3);
			}
			// 전체 예약인원을 반복문 내에서 누적 저장
			sum += rsv.getRsv_number();
		}
		la_reservationCount.setText("총 예약 건수 : " + list.size() + "건");
		la_reservationNumber.setText("총 예약 인원 : " + sum + "명");

		// 계산완료 후 멤버변수 값을 0으로 초기화
		resetNum();
	}

	// 조건별 변수 정보를 저장하는 메서드
	public void getRsvNumber(Reservation rsv, TrainigTime time) {

		// 강습 형태 조건에 따라 인원 수 추출
		if (rsv.getStep().getStep_name().equals("입문")) {
			time.nomalNum += rsv.getRsv_number();
		} else if (rsv.getStep().getStep_name().equals("커플")) {
			time.coupleNum += rsv.getRsv_number();
		} else if (rsv.getStep().getStep_name().equals("개인")) {
			time.singleNum += rsv.getRsv_number();
		}
		// 누적된 인원 수를 종합하여 변수에 저장
		int total = time.nomalNum + time.coupleNum + time.singleNum;
		setLabel(time, time.nomalNum, time.coupleNum, time.singleNum, total);
	}

	// 시간별 강습 현황 데이터 세팅 메서드
	public void setLabel(TrainigTime time, int num1, int num2, int num3, int total) {
		time.la_nomal.setText("입문 강습 : " + num1 + "명");
		time.la_couple.setText("커플 강습 : " + num2 + "명");
		time.la_single.setText("개인 강습 : " + num3 + "명");
		time.la_total.setText("총 강습 : " + total + "명");
	}

	// 현황정보 인원수에 재조회를 위한 멤버변수 초기화
	public void resetNum() {
		TrainigTime[] timeList = { trainigTime_10, trainigTime_12, trainigTime_3 };
		for (int i = 0; i < timeList.length; i++) {
			timeList[i].nomalNum = 0;
			timeList[i].coupleNum = 0;
			timeList[i].singleNum = 0;
		}
	}

}
