package dto;

public class CTMiniItem {
	public String name;
	public String set;
	public String code;
	
	
	public CTMiniItem(){
		super();
	}
	
	public CTMiniItem(String name, String set, String code) {
		super();
		this.name = name;
		this.set = set;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSet() {
		return set;
	}

	public void setSet(String set) {
		this.set = set;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
	
	
}
