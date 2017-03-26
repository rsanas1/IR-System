
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class assignmentP3 {

	FileProcessor fp3,fp1,fp2,fp4;
	HashMap<Integer,ArrayList<Term>>docDetail;
	HashMap<String,DictionaryType> dictionary;
	HashMap<Integer,DocsType> docsTable;
	HashMap<Integer,Double>ranks;
	ArrayList<String> queryTerms;
	
	int total;
	
	public static void main(String []args) throws IOException
	{
		assignmentP3 obj=new assignmentP3();
		ArrayList<Term> docDetailEntry=new ArrayList<>();
		obj.fillStructures();
		Map<String, DictionaryType> treeMap = new TreeMap<>(obj.dictionary);
		for (String str : treeMap.keySet()) {
			obj.fp3.writeToFile(str+"\n");
		}
//		for(String t : obj.dictionary.keySet())
//		{
//			
//		}
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		
		String inputLine="";
		do
		{
			obj.ranks=new HashMap<>();
			obj.docDetail=new HashMap<>();
			obj.queryTerms=new ArrayList<>();
			System.out.println("Enter your query");
			try {
				inputLine=br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			String query[]=inputLine.split(" |\\, |\\. |! |\\-|\\? |\\: |\\; |\\.$", 0);
			
			for(String each:query)
			{
				each=obj.processQueryTerm(each.toLowerCase());

				if(obj.dictionary.get(each)!=null)
				{
					for(int i : obj.dictionary.get(each).docs)
					{
						if(!obj.docDetail.containsKey(i))
									obj.docDetail.put(i, null);
					//	System.out.println(i);
						
					}
					
					obj.queryTerms.add(each);
				}
				
				
				
			}
			
			if(obj.queryTerms.isEmpty())
			{
				obj.fp1.writeToFile("NO RESULTs");
				continue;
			}
			
			for(String each:obj.queryTerms)
			{
				docDetailEntry.clear();
				for(int i : obj.docDetail.keySet())
				{
					if(obj.docDetail.get(i)!=null)
					{
						docDetailEntry=obj.docDetail.get(i);
					}
					else
					{
						docDetailEntry=new ArrayList<Term>();
					}
					
					if(obj.dictionary.get(each).docs.contains(i))
					{
							Term t=new Term(each,obj.dictionary.get(each).docsList.get(i));
							docDetailEntry.add(t);
						
						
					}
					else
					{
						
						docDetailEntry.add(new Term(each,0));
						
					}
					
					obj.docDetail.put(i,docDetailEntry);
				}
				
			}
			
			for(int i : obj.docDetail.keySet())
			{
				double probability=0;
				
				for(Term term : obj.docDetail.get(i))
				{
					int tf= term.tf; 
					int docLength=obj.docsTable.get(i).docLength;
					int term_cf=obj.dictionary.get(term.name).cf; 
				
					probability+= ( Math.log( (0.9*(double)((double)tf/docLength)) + (0.1*(double)((double)term_cf/obj.total)) )) / (Math.log(2) );
					
				}
				obj.ranks.put(i, probability);
			}
			
			int rankSize = obj.ranks.size();
			for(int i=0 ; i<5 && i< rankSize ; i++)
			{
				Map.Entry<Integer, Double> maxEntry = null;
				for (Map.Entry<Integer, Double> entry : obj.ranks.entrySet())
				{
					if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
					{
						maxEntry = entry;
						
					}
				}
				obj.ranks.remove(maxEntry.getKey());
				
				obj.fp1.writeToFile(obj.docsTable.get(maxEntry.getKey()).headline+"\n");
				obj.fp1.writeToFile(obj.docsTable.get(maxEntry.getKey()).docPath+"\n");
				obj.fp1.writeToFile("Computed probablity: "+maxEntry.getValue()+"\n");
				obj.fp1.writeToFile(obj.docsTable.get(maxEntry.getKey()).snippet+"\n\n");
				
			}
			
			for(String s : obj.queryTerms)
			{
				obj.fp1.writeToFile(s+" ");
			}
			obj.fp1.writeToFile("\n");
			
			for(int i : obj.docDetail.keySet())
			{
				obj.fp1.writeToFile(obj.docsTable.get(i).docPath+"\n");
			}
			obj.fp1.writeToFile("\n");
			
		}while(!inputLine.equalsIgnoreCase("EXIT"));
	}
	
	
	
	public String processQueryTerm(String term)
	{
		
		Set<String> stop=new HashSet<String>(Arrays.asList("and","an","by","from","of","the","with",
				"a","in","for","hence","within","who","when","where","why","how","whom",
				"have","had","has","not","but","do","does","done"));
		
		if(stop.contains(term))
			return "";
		
		term= stemming(term);
		
		if(term.length()>1)
			return term;
		
		return "";
		
	}
	
	void fillStructures()throws IOException
	{
		
		dictionary=new HashMap<>();
		docsTable=new HashMap<>();
		try {
			fp1=new FileProcessor("rsanas1_java_part2\\dictionary.csv", "result.txt");
			fp2=new FileProcessor("rsanas1_java_part2\\postings.csv", "result.txt");
			fp3=new FileProcessor("rsanas1_java_part2\\docsTable.csv", "mayo.txt");
			fp4=new FileProcessor("rsanas1_java_part2\\total.txt", "result.txt");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String line="";
		int m=1;
		while((line=fp1.readFromFile())!=null)
		{
			
			String tokens[]=line.split("\\,");
			DictionaryType d=new DictionaryType(tokens[0]);
			d.term=tokens[0];
			d.cf=Integer.parseInt(tokens[1]);
			d.df=Integer.parseInt(tokens[2]);
			d.offset=Integer.parseInt(tokens[3]);
			for(int i=0;i<d.df;i++)
			{
				String postingLine=fp2.readFromFile();
				String []postingTokens=postingLine.split("\\,",0);
				d.docsList.put(Integer.parseInt(postingTokens[0]), Integer.parseInt(postingTokens[1]));
				d.docs.add(Integer.parseInt(postingTokens[0]));
			}
			
			if(dictionary.containsKey(d.term))
			{
				System.out.println(d.term+" Sapadla");
			}
			
			dictionary.put(d.term, d);
			//System.out.println(m);
			m++;
		}
		
		while((line=fp3.readFromFile())!=null)
		{
			String tokens[]=line.split("\\,");
			DocsType d=new DocsType(Integer.parseInt(tokens[0]), tokens[1], Integer.parseInt(tokens[2]), tokens[3], tokens[4]);
			docsTable.put(Integer.parseInt(tokens[0]), d);
			
		}
		
		total=Integer.parseInt(fp4.readFromFile());
	}
	
	static public String stemming(String token)
	{
			token=token.replaceAll("[^A-Za-z0-9 ]", "");
			
			if(token.endsWith("'s"))
			{
				token = token.replaceAll("\\'s$","s");
				return stemming(token);
			}
			if(token.endsWith("s'"))
			{
				token = token.replaceAll("s\\'$","s");
				return stemming(token);
			}	
			
			if(token.endsWith("ies"))
			{
				if(!token.endsWith("eies") && !token.endsWith("aies"))
				{
					token=token.replaceAll("ies$", "y");
					return stemming(token);
				}
			}
			
			else if(token.endsWith("es") )
			{
				if(!token.endsWith("aes") && !token.endsWith("ees") && !token.endsWith("oes"))
				{
					
					token=token.replaceAll("es$", "e");
					return stemming(token);
				}
			}
			
			else if(token.endsWith("s") )
			{
				if(!token.endsWith("us") && 
					!token.endsWith("ss"))
				{
				token=token.replaceAll("s$", "");
				return stemming(token);
				}
			
			}
			
			
			return token;
			
		
		
	}
	
	
}
