//package rsanas1_java_part2;

public class DocsType {

	int docNo;
	String headline;
	int docLength;
	String snippet;
	String docPath;
	public DocsType(int docNo, String headline, int docLength, String snippet, String docPath) {
		super();
		this.docNo = docNo;
		this.headline = headline;
		this.docLength = docLength;
		this.snippet = snippet;
		this.docPath = docPath;
	}
	public int getDocNo() {
		return docNo;
	}
	public void setDocNo(int docNo) {
		this.docNo = docNo;
	}
	public String getHeadline() {
		return headline;
	}
	public void setHeadline(String headline) {
		this.headline = headline;
	}
	public int getDocLength() {
		return docLength;
	}
	public void setDocLength(int docLength) {
		this.docLength = docLength;
	}
	public String getSnippet() {
		return snippet;
	}
	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}
	public String getDocPath() {
		return docPath;
	}
	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}
	
	
}
