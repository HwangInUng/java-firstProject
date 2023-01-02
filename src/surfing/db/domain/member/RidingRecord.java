package surfing.db.domain.member;

public class RidingRecord {
	private int record_idx;
	private SurfMember surfMember;
	private String riding_spot;
	private String board_spec;
	private String pado;
	private String image_name;
	private String regdate;

	public int getRecord_idx() {
		return record_idx;
	}

	public void setRecord_idx(int record_idx) {
		this.record_idx = record_idx;
	}

	public SurfMember getSurfMember() {
		return surfMember;
	}

	public void setSurfMember(SurfMember surfMember) {
		this.surfMember = surfMember;
	}

	public String getRiding_spot() {
		return riding_spot;
	}

	public void setRiding_spot(String riding_spot) {
		this.riding_spot = riding_spot;
	}

	public String getBoard_spec() {
		return board_spec;
	}

	public void setBoard_spec(String board_spec) {
		this.board_spec = board_spec;
	}

	public String getPado() {
		return pado;
	}

	public void setPado(String pado) {
		this.pado = pado;
	}

	public String getImage_name() {
		return image_name;
	}

	public void setImage_name(String image_name) {
		this.image_name = image_name;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

}
