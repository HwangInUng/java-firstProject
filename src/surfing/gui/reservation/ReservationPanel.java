package surfing.gui.reservation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import surfing.db.domain.reservation.Reservation;
import surfing.db.reopsitory.reservation.ReservationDAO;
import surfing.gui.Page;
import surfing.gui.SurfingApp;

public class ReservationPanel extends Page {
	ReservationDAO reservationDAO;

	// 상단 필드
	JButton bt_prev, bt_next;
	JLabel la_yymm;

	// 중앙 캘린더 필드
	JPanel p_calendar, p_dayOfWeek, p_dayOfMonth;
	String[] title = { "Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat" };
	DayCell[] dayList = new DayCell[7];
	DateCell[][] dateList = new DateCell[6][7];
	Calendar currentCal;

	// 날짜별 예약현황 조회 시 제어를 위해 멤버로 보유
	ReservationBoard reservationBoard;

	// 예약자 현황이 실시간으로 변경가능하기 때문에 멤버로 list 보유
	List<Reservation> rsvList;

	// 현재 년월을 멤버로 보유
	String date;

	public ReservationPanel(SurfingApp app) {
		super(app);
		reservationDAO = new ReservationDAO();

		container.setLayout(new BorderLayout());
		currentCal = Calendar.getInstance();
		reservationBoard = new ReservationBoard(app, this);

		// 상단필드
		bt_prev = new JButton("이전");
		bt_next = new JButton("다음");
		la_yymm = new JLabel("2022년 12월");

		p_north.add(bt_prev);
		p_north.add(la_yymm);
		p_north.add(bt_next);

		// 중앙필드
		p_calendar = new JPanel();
		p_dayOfWeek = new JPanel();
		p_dayOfMonth = new JPanel();

		p_calendar.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
		p_dayOfWeek.setLayout(new GridLayout(1, 7));
		p_dayOfMonth.setLayout(new GridLayout(6, 7));
		p_calendar.setPreferredSize(new Dimension(750, 630));
		p_dayOfWeek.setPreferredSize(new Dimension(730, 80));
		p_dayOfMonth.setPreferredSize(new Dimension(730, 480));
		p_calendar.setBorder(Page.PANEL_LINEBORDER);

		createDayOfWeek();
		createDayOfMonth();
		printAll();

		p_calendar.add(p_dayOfWeek);
		p_calendar.add(p_dayOfMonth);

		container.add(p_north, BorderLayout.NORTH);
		container.add(p_calendar);

		add(container);
		add(reservationBoard);

		bt_prev.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeMonth(-1);
			}
		});

		bt_next.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeMonth(1);
			}
		});
	}

	// 날짜 클릭 시 해당 일의 예약현황 조회
	public void getRsvList(String day) {
		// 현재 년월 + 클릭 시 얻어온 날짜를 조합
		String currentDate = date + day;
		System.out.println(currentDate);
		
		reservationBoard.boardModel.list = reservationDAO.selectDay(currentDate);
		//모델이 보유하고 있는 list를 이용하여 각 항목별 인원 수를 집계
		reservationBoard.showInfo(reservationBoard.boardModel.list);
		reservationBoard.reservationTable.updateUI();
	}

	// 생성 시 예약현황 시각정보 표시
	public void showReservation() {
		if (rsvList != null) {
			rsvList.removeAll(rsvList); // 리스트 정보를 초기화
			resetColor(); // 리스트 정보 초기화에 따른 Cell색상 초기화
		}
		// 현재 년도와 월을 변수에 저장하여 하나의 자료형으로 조합
		String year = Integer.toString(currentCal.get(Calendar.YEAR));
		String month = Integer.toString(currentCal.get(Calendar.MONTH) + 1);
		date = year + month;

		rsvList = reservationDAO.selectAll(date);
		if (rsvList != null) {
			for (int i = 0; i < dateList.length; i++) {
				for (int j = 0; j < dateList[i].length; j++) {
					if (dateList[i][j].title.equals("") == false) {
						for (int a = 0; a < rsvList.size(); a++) {
							Reservation rsv = rsvList.get(a);
							if (dateList[i][j].title.equals(rsv.getRegdate())) {
								dateList[i][j].color = Page.RSVCELL_COLOR;
							}
						}
					}
				}
			}
			p_dayOfMonth.repaint();
		}
	}

	// dateCell color 초기화 메서드
	public void resetColor() {
		for (int i = 0; i < dateList.length; i++) {
			for (int j = 0; j < dateList[i].length; j++) {
				dateList[i][j].color = Page.BG_COLOR;
			}
		}
		p_dayOfMonth.repaint();
	}

	// 월 변경 시 달력정보 갱신 메서드
	public void changeMonth(int n) {
		int mm = currentCal.get(Calendar.MONTH);
		currentCal.set(Calendar.MONTH, mm + n);

		printAll();
	}

	// 요일 타이틀 생성 메서드
	public void createDayOfWeek() {
		for (int i = 0; i < dayList.length; i++) {
			//주말구분을 통한 시각적 효과를 위한 조건문
			if (i == 0) {
				dayList[i] = new DayCell(this, title[i], Page.BG_COLOR, Color.red);
			} else if (i == dayList.length - 1) {
				dayList[i] = new DayCell(this, title[i], Page.BG_COLOR, Color.blue);
			} else {
				dayList[i] = new DayCell(this, title[i], Page.BG_COLOR, Color.black);
			}
			p_dayOfWeek.add(dayList[i]);
		}
	}

	// 날짜 생성 메서드
	public void createDayOfMonth() {
		int index = 0;

		for (int i = 0; i < dateList.length; i++) {
			for (int j = 0; j < dateList[i].length; j++) {
				index++;
				//주말구분을 통한 시각적 효과를 위한 조건문
				if (j == 0) {
					dateList[i][j] = new DateCell(this, Integer.toString(index), Page.BG_COLOR, Color.red, index);
				} else if (j == dateList[i].length - 1) {
					dateList[i][j] = new DateCell(this, Integer.toString(index), Page.BG_COLOR, Color.blue, index);
				} else {
					dateList[i][j] = new DateCell(this, Integer.toString(index), Page.BG_COLOR, Color.black, index);
				}
				p_dayOfMonth.add(dateList[i][j]);
			}
		}
	}

	// 시작일과 해당 월의 날짜를 계산하여 달력에 출력하는 메서드
	public void printDate() {
		int n = 0; // 조건의 지표로만 사용
		int date = 1;

		for (int i = 0; i < dateList.length; i++) {
			for (int j = 0; j < dateList[i].length; j++) {
				n++;
				if (n >= getStartDayOfWeek() && date <= getLastDayOfMonth()) {
					dateList[i][j].title = Integer.toString(date);
					date++;
				} else {
					dateList[i][j].title = "";
				}
			}
		}
		p_dayOfMonth.repaint();
	}

	// 해당 월의 시작 요일 반환메서드
	public int getStartDayOfWeek() {
		int yy = currentCal.get(Calendar.YEAR);
		int mm = currentCal.get(Calendar.MONTH);

		Calendar cal = Calendar.getInstance();
		cal.set(yy, mm, 1);

		int day = cal.get(Calendar.DAY_OF_WEEK);
		return day;
	}

	// 해당 월이 몇일까지 있는지 반환하는 메서드
	public int getLastDayOfMonth() {
		int yy = currentCal.get(Calendar.YEAR);
		int mm = currentCal.get(Calendar.MONTH);

		Calendar cal = Calendar.getInstance();
		cal.set(yy, mm + 1, 0);
		int lastDay = cal.get(Calendar.DATE);

		return lastDay;
	}

	// 현재 년도와 월을 타이틀에 출력하는 메서드
	public void printTitle() {
		int yy = currentCal.get(Calendar.YEAR);
		int mm = currentCal.get(Calendar.MONTH);

		String str = yy + "년 " + (mm + 1) + "월";
		la_yymm.setText(str);
	}

	// 패널 전환
	public void showBoard() {
		container.setVisible(false);
		reservationBoard.setVisible(true);
	}

	public void printAll() {
		printTitle();
		printDate();
		showReservation();
	}

}
