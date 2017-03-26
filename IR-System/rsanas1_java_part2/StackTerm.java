//package rsanas1_java_part2;

public class StackTerm {

	private String name;
	private String info;
	private int status;
	
	public StackTerm(String name) {
		super();
		this.name = name;
		info="";
		status=0;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int statusFlag) {
		status = statusFlag;
	};
	
	
}
