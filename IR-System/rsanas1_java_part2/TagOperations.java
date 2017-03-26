//package rsanas1_java_part2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TagOperations {

	static private ArrayList<StackTerm> stack=new ArrayList<StackTerm>();
	static private int i=-1;
	public static boolean headline;
	public static boolean snippet;


	
	  static void checkTag(String term,boolean fresh)
	{
		  String headlineterm=term;
		  if(headline==true && stack.get(i).getStatus()==2 && fresh==true)
		  {
			  if(assignmentP2.docsTable.containsKey(assignmentP2.docId))
			  {
				  DocsType dt= assignmentP2.docsTable.get(assignmentP2.docId);
				  dt.setDocNo(assignmentP2.docId);
				  dt.setHeadline(dt.getHeadline()+" "+term);
				  assignmentP2.docsTable.put(assignmentP2.docId, dt);
			  }
			  else
			  {
				  DocsType dt=new DocsType(assignmentP2.docId, term, 0, "", assignmentP2.currentDoc);
				  assignmentP2.docsTable.put(assignmentP2.docId, dt);
			  }
			  
			 
		  }
		  if(snippet==true && stack.get(i).getStatus()==2 && fresh==true)
		  {		
				  if(!term.contains(">")&& !term.contains("<") && assignmentP2.snippetLength<40)
				  {
					  if(assignmentP2.docsTable.containsKey(assignmentP2.docId))
					  {
						  DocsType dt= assignmentP2.docsTable.get(assignmentP2.docId);
						  dt.setDocNo(assignmentP2.docId);
						  dt.setSnippet(dt.getSnippet()+" "+term);
						  assignmentP2.docsTable.put(assignmentP2.docId, dt);
					  }
					  else
					  {
						 
					  DocsType dt=new DocsType(assignmentP2.docId, "", 0, term, assignmentP2.currentDoc);
					  assignmentP2.docsTable.put(assignmentP2.docId, dt);
					  }
					  assignmentP2.snippetLength++;
				  }
			  
			 
		  }
			  
		Set<String> stop=new HashSet<String>(Arrays.asList("and","an","by","from","of","the","with",
				"a","in","for","hence","within","who","when","where","why","how","whom",
				"have","had","has","not","but","do","does","done"));
		
		StringBuffer temp=new StringBuffer("");
		StringBuffer stemmingTemp=new StringBuffer("");
		StackTerm st=null;
		
		
		if(term.startsWith("<") && !term.startsWith("</"))
		{
			if(term.endsWith(">"))
			{
				st=new StackTerm(term.substring(1, term.length()-1));
				st.setStatus(2);
				++i;
				stack.add(i, st);
				
			if(stack.get(i).getName().trim().equalsIgnoreCase("headline"))
				{headline=true; }
			
			if(stack.get(i).getName().trim().equalsIgnoreCase("text"))
			{snippet=true;}
			
			}
			else
			{
				st=new StackTerm(term.substring(1));
				st.setStatus(1);
				++i;
				stack.add(i, st);
				
			}
				
		}
		else if(term.startsWith("</") && term.contains(stack.get(i).getName()))
		{
			if(stack.get(i).getName().equalsIgnoreCase("headline"))
			{
				headline=false;
			}
			if(stack.get(i).getName().equalsIgnoreCase("text"))
			{
				snippet=false;
			}
			stack.set(i, null);
			--i;
		}
		else if(term.endsWith(">") && stack.get(i).getStatus()==1)
			{
			stack.get(i).setName(stack.get(i).getName()+term.substring(0, term.length()-1));
			stack.get(i).setName(stack.get(i).getName().trim());
				stack.get(i).setStatus(2);
				if(stack.get(i).getName().trim().equalsIgnoreCase("headline"))
					{headline=true; }
				if(stack.get(i).getName().trim().equalsIgnoreCase("text"))
				{snippet=true;}
			}
		else if(!term.endsWith(">") && term.contains(">") && stack.get(i).getStatus()==1)
		{
			
			stack.get(i).setName(stack.get(i).getName()+term.substring(0, term.indexOf(">")));
			stack.get(i).setName(stack.get(i).getName().trim());
			term=term.substring(term.indexOf(">")+1);
					stack.get(i).setStatus(2);
					if(stack.get(i).getName().trim().equalsIgnoreCase("headline"))
						{headline=true; }
					if(stack.get(i).getName().trim().equalsIgnoreCase("text"))
					{snippet=true; }
			checkTag(term,false);
			
		}
		else if(stack.get(i).getStatus()==2 && !term.startsWith("<"))
		{
			
			if(stop.contains(term))
				return;
			temp=new StringBuffer(term);
			if(term.startsWith("\'")||term.startsWith("\"")||
					term.startsWith("(")||term.startsWith("[")||
					term.startsWith(")")||term.startsWith(")"))
			{
				
				checkTag(term.substring(1),false);
				return;
			}
			
			if(term.endsWith("\'")||term.endsWith("\"")||
					term.endsWith("(")||term.endsWith("[")||
					term.endsWith(")")||term.endsWith("]"))
			{
				
				checkTag(term.substring(0,term.length()-1),false);
				return;
				
			}
			
			
			try {
				stemmingTemp=new StringBuffer(stemming(temp.toString())) ;
				if(!stemmingTemp.toString().equalsIgnoreCase(temp.toString()))
				{
					 checkTag(stemmingTemp.toString(), false);
					 return;
				}
				else
				{
					temp=new StringBuffer(stemming(stemmingTemp.toString())) ;
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//System.out.println(e.toString());
			}
			
			if(temp.length()==1)
				return;
			
			if(temp.length()>1)
			{	
				assignmentP2.docLength++;
				try {
					if(assignmentP2.dictionary.containsKey(temp.toString()))
					{
						DictionaryType d=assignmentP2.dictionary.get(temp.toString());
						d.setCf(d.getCf()+1);
						
						if(d.getDocsList().containsKey(assignmentP2.docId))
							d.getDocsList().put(assignmentP2.docId, d.getDocsList().get(assignmentP2.docId)+1);
						else
							d.getDocsList().put(assignmentP2.docId, 1);
						
						
						assignmentP2.dictionary.put(temp.toString(),d);
					}
					else
					{
						DictionaryType d=new DictionaryType(temp.toString());
						d.setCf(1);
						d.getDocsList().put(assignmentP2.docId, 1);
						assignmentP2.dictionary.put(temp.toString(),d);
					}
				} catch (Exception e) {
					
					
				}
			}
		}
		
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
