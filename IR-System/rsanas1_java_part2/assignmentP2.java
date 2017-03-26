//package rsanas1_java_part2;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class assignmentP2 {

	public static String directory_path;
	public static HashMap<String, HashMap<Integer,Integer >> postingList;
	public static HashMap<String,DictionaryType> dictionary;
	public static HashMap<Integer,DocsType> docsTable;
	public static FileProcessor fp,fp3;
	public static FileProcessor fp2;
	public static FileProcessor fp1;
	public static int docId;
	public static String currentDoc;
	static String filenames[];
	public static int docLength;
	public static int snippetLength;
	public static int total;
	public static void main(String[] args) {
		total=0;
		docId=1;
		postingList=new HashMap<>();
		dictionary=new HashMap<>();
		docsTable=new HashMap<>();
		if(args.length!=1)
		{
			System.err.println("Invalid Arguments");
			System.exit(1);
		}
		directory_path=args[0];
		//deleting dictionary before execution
		filenames=new String[]{"dictionary.csv","postings.csv","docsTable.csv","total.txt"};
		for(String s : filenames)
		{
			File f = new File(s);
			if (f.exists())
			{
				f.delete();
			}
		}
		
		ArrayList<File> files= new ArrayList<File>();
		assignmentP2 obj=new assignmentP2();
		obj.listf(args[0],files);
		
		 List<String> v = new ArrayList<String>(dictionary.keySet());
		    Collections.sort(v);
			
		    int offset=0;
		    fp.writeToFile("term,cf,df,offset \n");
		    fp1.writeToFile("docId,tf \n");
		    fp2.writeToFile("doc number,headline,doc length |D|,snippet,Doc path \n");
		    
			for(String key: v)
			{
				int flag=0;
				fp.writeToFile(key+","+assignmentP2.dictionary.get(key).getCf()+","
						+assignmentP2.dictionary.get(key).getDocsList().size()+","+offset+"\n");
				
				total+=assignmentP2.dictionary.get(key).getCf();
				
				for(int i :assignmentP2.dictionary.get(key).getDocsList().keySet())
					fp1.writeToFile(i+","+assignmentP2.dictionary.get(key).getDocsList().get(i)+"\n");
				
					
				
					offset+=assignmentP2.dictionary.get(key).getDocsList().size();
			}
			fp3.writeToFile(total+"\n");
			for(int i: docsTable.keySet())
			{
				docsTable.get(i).setSnippet(docsTable.get(i).getSnippet().replaceAll("</text>", ""));
				docsTable.get(i).setSnippet(docsTable.get(i).getSnippet().replaceAll(",", " "));
				docsTable.get(i).setHeadline(docsTable.get(i).getHeadline().replaceAll("</headline>", ""));
				docsTable.get(i).setHeadline(docsTable.get(i).getHeadline().replaceAll(",", " "));
				fp2.writeToFile(i+","+docsTable.get(i).getHeadline()+","+docsTable.get(i).getDocLength()+","+
				docsTable.get(i).getSnippet()+","+docsTable.get(i).getDocPath()+"\n");
			}
		
	}
	
	public void listf(String directoryName, ArrayList<File> files) {
	    File directory = new File(directoryName);
	    // get all the files from a directory
	    File[] fList = directory.listFiles();
	    for (File file : fList) {
	        if (file.isFile() && !file.getName().contains("~")) {
	        	try {
	        		currentDoc=file.getCanonicalPath();
	        		docLength=0;
	        		snippetLength=0;
					processor(file.getAbsolutePath());
					docsTable.get(docId).setDocLength(docLength);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					
				}
	        	
	            files.add(file);
	            docId++;
	        } else if (file.isDirectory()) {
	            listf(file.getAbsolutePath(), files);
	        }
	    }
	    
	   
	}
	
	public void processor(String input)
	{	
	
		fp=new FileProcessor(input, filenames[0]);
		
		fp1=new FileProcessor(input, filenames[1]);
		
		fp2=new FileProcessor(input, filenames[2]);
		
		fp3=new FileProcessor(input, filenames[3]);
		
		
		
		String line;
		try {
			while((line=fp.readFromFile())!=null)
			{
				
				List<String> items = Arrays.asList(line.split(" "));
				String newLine= "";
				for(String s : items)
				{
					if(!(s.length()==1) && !(s.equalsIgnoreCase("<")) && !(s.equalsIgnoreCase(">")) )
						newLine+=s+" ";
				}
			 
				String []tokens1=newLine.split(" |\\, |\\. |! |\\-|\\? |\\: |\\; |\\.$", 0);
				for(int i=0;i<tokens1.length;i++)
				{
					tokens1[i]=tokens1[i].toLowerCase();
					
				}
				
				for(String s:tokens1)
				{
					
					if(!s.isEmpty())
						try {
							TagOperations.checkTag(s,true);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							
						}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		
		
		
	}
	
}

