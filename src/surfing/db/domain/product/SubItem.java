package surfing.db.domain.product;

public class SubItem {
	private int subitem_idx;
	private TopItem topItem;
	private String subitem_name;

	public int getSubitem_idx() {
		return subitem_idx;
	}

	public void setSubitem_idx(int subitem_idx) {
		this.subitem_idx = subitem_idx;
	}

	public TopItem getTopItem() {
		return topItem;
	}

	public void setTopItem(TopItem topItem) {
		this.topItem = topItem;
	}

	public String getSubitem_name() {
		return subitem_name;
	}

	public void setSubitem_name(String subitem_name) {
		this.subitem_name = subitem_name;
	}

}
