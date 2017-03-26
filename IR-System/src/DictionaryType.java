
import java.util.ArrayList;
import java.util.HashMap;

public class DictionaryType{

	
	String term;
	int cf;
	ArrayList<Integer>docs;
	HashMap<Integer, Integer> docsList;
	
	int df;
	int offset;
	
	DictionaryType(String term){
		this.term=term;
		docs=new ArrayList<Integer>();
		docsList=new HashMap<>();
	}
	
	public HashMap<Integer, Integer> getDocsList() {
		return docsList;
	}

	public void setDocsList(HashMap<Integer, Integer> docsList) {
		this.docsList = docsList;
	}

	public ArrayList<Integer> getDocs() {
		return docs;
	}

	public void setDocs(ArrayList<Integer> docs) {
		this.docs = docs;
	}
	
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public int getCf() {
		return cf;
	}
	public void setCf(int cf) {
		this.cf = cf;
	}
	public int getDf() {
		return df;
	}
	public void setDf(int df) {
		this.df = df;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	
	
}
