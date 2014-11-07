package dto;

import java.util.List;


public class CTItem {
	
	public String name;
	public String set;
	public String code;
	public int page;
	public String description;
	public List<String> exceptions;
	public List<String> tips;	
	public List<String> dependent;
	public List<String> substantial;
	public List<String> partial;
	public List<String> supervision;
	public List<String> setup;
	public List<String> independent;
	
	
	
		


	public CTItem(String name, String set, String code, int page,
			String description, List<String> exceptions, List<String> tips,
			List<String> dependent, List<String> substantial,
			List<String> partial, List<String> supervision, List<String> setup,
			List<String> independent) {
		super();
		this.name = name;
		this.set = set;
		this.code = code;
		this.page = page;
		this.description = description;
		this.exceptions = exceptions;
		this.tips = tips;
		this.dependent = dependent;
		this.substantial = substantial;
		this.partial = partial;
		this.supervision = supervision;
		this.setup = setup;
		this.independent = independent;
	}

	public CTItem() {
		super();
	}

//	public CTItem(CareToolItem item) {
//		super();
//		this.name = item.name;
//		this.set = item.set;
//		this.code = item.code;
//		this.page = item.page;
//		this.description = item.description;
//		this.exceptions = item.exceptions;
//		this.tips = item.tips;
//		this.dependent = item.dependent;
//		this.substantial = item.substantial;
//		this.partial = item.partial;
//		this.supervision = item.supervision;
//		this.setup = item.setup;
//		this.independent = item.independent;
//	}

	
	@Override
	public String toString(){
		return String.format("Item %s with %d exceptions",name, exceptions.size());
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



	public int getPage() {
		return page;
	}



	public void setPage(int page) {
		this.page = page;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public List<String> getExceptions() {
		return exceptions;
	}



	public void setExceptions(List<String> exceptions) {
		this.exceptions = exceptions;
	}



	public List<String> getTips() {
		return tips;
	}



	public void setTips(List<String> tips) {
		this.tips = tips;
	}
	
	
	
	
}
