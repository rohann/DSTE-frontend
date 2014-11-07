package dto;

public class CTExample {
	
	
	public String itemCode;
	public String situation;
	public String code;
	public String rationale;
	

	public CTExample(String itemCode, String situation, String code,
			String rationale) {
		super();
	
		this.itemCode = itemCode;
		this.situation = situation;
		this.code = code;
		this.rationale = rationale;
	}
	
	public CTExample() {
		super();
	}
	
	@Override
	public String toString(){
		return String.format("The examples %s belonging to %s", situation, itemCode);
	}
	
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getSituation() {
		return situation;
	}
	public void setSituation(String situation) {
		this.situation = situation;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getRationale() {
		return rationale;
	}
	public void setRationale(String rationale) {
		this.rationale = rationale;
	}
	
	
	
}
