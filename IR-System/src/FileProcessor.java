
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileProcessor {
	private BufferedReader reader;
	private BufferedWriter writer;
	

	// constructor takes input read file
	public FileProcessor(String inputFileName,String outputFileName) {
		
		try {
			this.reader = new BufferedReader(new FileReader(inputFileName));
			
		} catch (FileNotFoundException e) {
			System.err.println("Exception as inputfile"+ inputFileName+"not found");
			System.exit(1);
		} 		
		try {
			this.writer = new BufferedWriter(new FileWriter(outputFileName, true));
		} catch (FileNotFoundException e) {
			System.err.println("Exception as outputfile"+ outputFileName+"not found");
			System.exit(1);
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/***
	 * Reads data from the file
	 * 
	 * @return only single of file
	 */
	public String readFromFile() {
		
		String inputLineFromFile = "";
		try {
			inputLineFromFile = this.reader.readLine(); // buffer reader use
		} catch (FileNotFoundException e) {
			System.err.println("Exception as file not found");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Exception due Stream Reader I/O operation ");
			System.exit(1);
		}
		return inputLineFromFile;
	}

	/***
	 * Write particular line to a file
	 * 
	 * @param writeThisLine
	 * 
	 */
	public void writeToFile(String writeThisLine) {
	
		try {
			

			this.writer.append(writeThisLine); // buffer writer use
			this.writer.flush();
		} catch (FileNotFoundException e) {
			
			System.exit(1);
		} catch (IOException e) {

			System.err.println("Exception due Stream Writer I/O operation ");
			System.exit(1);
		}

	}
	
	//
	public void closeWrite()
	{
		try {
			this.writer.close();
		} catch (IOException e) {

			System.err.println("Exception due Stream Writer I/O operation ");
			System.exit(1);
		}
	}
	
	
	

	// Overriding toString method in FileProcessor class
	public String toString() {
		return "\nOverriding toString in FileProcessor Class";
	}

}
