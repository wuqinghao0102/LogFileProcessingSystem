package SingleThread;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Author: Qinghao Wu
 * Date: 02/24/2016
 * This class is the single thread solution for log file processing system.
 */

public class SingleThreadSolution {
	// Define global variable to record the line number;
	public static int lineNum = 1;
	
	// This function is used to check whether the given file is a log file or not.
	// This function use regular expression to check whether the given string satisfy the given pattern.
	public static boolean isLogFile(String filename) { 
		Pattern p1 = Pattern.compile("logtest\\.[0-9]+-[0-9]+-[0-9]+\\.log");
        Matcher m1 = p1.matcher(filename); 
        boolean rs1 = m1.matches(); 
        return rs1; 
    } 
	
	// This function is used to add line numbers in front of each line for given file.
	public static void AddLineNum(String FilePath){
		try {
			File file = new File(FilePath);
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			StringBuffer buf = new StringBuffer();
			//Read the file line by line, and put the modified line to string buffer;
			while ((line = br.readLine()) != null) {
				buf.append(Integer.toString(lineNum) + ". " + line + "\n");
				lineNum++;
			}
			br.close();
			//save the string buffer to a new file with same name;
			File fileSave = new File(FilePath);
			PrintWriter pw = new PrintWriter(fileSave);
			pw.print(buf.toString());
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}
	
	// This function is used to modify the files in a file folder;
	public static void AddLineNumFolder(String folderName){
		// put all the files in an array;
		File folder = new File(folderName);
		File[] listOfFiles = folder.listFiles();
		// Sort the files by name. The files should be ordered by timestamp;
		Arrays.sort(listOfFiles);
		for (File file : listOfFiles) {
		    if (file.isFile()) {
		    	String filename = file.getName();
		    	// Check whether current file is a log file. If yes, modify the file.
		    	if(isLogFile(filename)){
		    		AddLineNum(folderName +"/"+ filename);
		    	}
		    }
		}
	}
	
	public static void main(String args[]){
		if (args.length != 1) {
			throw new IllegalArgumentException(
					"Exactly 1 parameters required !");
		}
		String folderName = args[0];
		AddLineNumFolder(folderName);
	}
}
