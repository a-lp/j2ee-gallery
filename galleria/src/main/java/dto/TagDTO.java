package dto;

public class TagDTO {
	private Integer id;
	private String tag;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {
		return "TagDTO [id=" + id + ", tag=" + tag + "]";
	}

}
