package surfing.db.domain.member;

public class Profile {
	// 반정규화의 중복컬럼 달성을 위해 surfMember를 통째로 보관
	private SurfMember surfMember;
	private int profile_idx;
	private String spot;
	private String board_name;
	private int board_price;
	private String filename;

	public int getProfile_idx() {
		return profile_idx;
	}

	public void setProfile_idx(int profile_idx) {
		this.profile_idx = profile_idx;
	}

	public SurfMember getSurfMember() {
		return surfMember;
	}

	public void setSurfMember(SurfMember surfMember) {
		this.surfMember = surfMember;
	}

	public String getSpot() {
		return spot;
	}

	public void setSpot(String spot) {
		this.spot = spot;
	}

	public String getBoard_name() {
		return board_name;
	}

	public void setBoard_name(String board_name) {
		this.board_name = board_name;
	}

	public int getBoard_price() {
		return board_price;
	}

	public void setBoard_price(int board_price) {
		this.board_price = board_price;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

}
