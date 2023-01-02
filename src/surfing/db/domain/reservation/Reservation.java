package surfing.db.domain.reservation;

public class Reservation {
	private int rsv_idx;
	private Step step;
	private String rsv_name;
	private int rsv_number;
	private int rsv_time;
	private String regdate;

	public int getRsv_idx() {
		return rsv_idx;
	}

	public void setRsv_idx(int rsv_idx) {
		this.rsv_idx = rsv_idx;
	}

	public Step getStep() {
		return step;
	}

	public void setStep(Step step) {
		this.step = step;
	}

	public String getRsv_name() {
		return rsv_name;
	}

	public void setRsv_name(String rsv_name) {
		this.rsv_name = rsv_name;
	}

	public int getRsv_number() {
		return rsv_number;
	}

	public void setRsv_number(int rsv_number) {
		this.rsv_number = rsv_number;
	}

	public int getRsv_time() {
		return rsv_time;
	}

	public void setRsv_time(int rsv_time) {
		this.rsv_time = rsv_time;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

}
