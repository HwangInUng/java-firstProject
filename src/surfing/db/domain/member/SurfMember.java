package surfing.db.domain.member;

public class SurfMember {
	private int surfmember_idx;
	private String id;
	private String name;
	private String gender;
	private String phoneno;
	private String snsId;
	private int career;
	private String regdate;
	public int getSurfmember_idx() {
		return surfmember_idx;
	}
	public void setSurfmember_idx(int surfmember_idx) {
		this.surfmember_idx = surfmember_idx;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhoneno() {
		return phoneno;
	}
	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}
	public String getSnsId() {
		return snsId;
	}
	public void setSnsId(String snsId) {
		this.snsId = snsId;
	}
	public int getCareer() {
		return career;
	}
	public void setCareer(int career) {
		this.career = career;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}



}
