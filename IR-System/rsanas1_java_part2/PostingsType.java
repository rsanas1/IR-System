//package rsanas1_java_part2;

public class PostingsType {

	
	
	String term;
	int docId;
	int tf;
	public PostingsType(String term,int docId, int tf) {
		super();
		this.docId = docId;
		this.tf = tf;
	}
	public int getDocId() {
		return docId;
	}
	public void setDocId(int docId) {
		this.docId = docId;
	}
	public int getTf() {
		return tf;
	}
	public void setTf(int tf) {
		this.tf = tf;
	}
	
	
}
