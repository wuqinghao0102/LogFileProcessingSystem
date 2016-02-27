package MultipleThread;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Author: Qinghao Wu
 * Date: 02/25/2016
 * This class is the Multithreading solution for log file processing system.
 */

public class MultipleThreadSolution	extends Thread{
	// curFileNum indicates the file waiting for read;
	// curCountNum indicates the files we have counted;
	// curLineNum indicates the start line number of next file to write;
	// totalNum indicates the total number of files we have;
	public static int curFileNum = 0;
	public static int curCountNum = 0;
	public static long curLineNum = 1;
	public static int totalNum = 0;
	private Thread t;
	private String threadName;
	public static File[] files;
	
	public MultipleThreadSolution(String name) {
		threadName = name;
		// System.out.println("Creating " + threadName);
	}
	
	// This function can to put all files in the given folder into a array, and sort the files.
	public static File[] getFiles(String folderName){
		// put all the files in an array;
		File folder = new File(folderName);
		File[] listOfFiles = folder.listFiles();
		// Sort the files by name. The files should be ordered by timestamp;
		Arrays.sort(listOfFiles);
		return listOfFiles;
	}
	
	// This function can read the given file, and add the content to a list line by line.
	public static List<String> readFile(File file){
		List<String> lines = new ArrayList<String>();
		try {
			//File file = new File(FilePath);
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			// Read the file line by line;
			while ((line = br.readLine()) != null) {	
				lines.add(line);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lines;
	}
	
	// This function can write all lines in a list to a file, and add line information to each line before writing to file.
	public static void writeFile(String FilePath, List<String> lines, long StartLineNumber){
		try {
			File fileSave = new File(FilePath);
			PrintWriter pw = new PrintWriter(fileSave);
			for (String s : lines) {
				pw.print(StartLineNumber++ + ". " + s + "\n");
			}
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}
	
	// This function can check whether the given file is a log file.
	public static boolean isLogFile(String filename) { 
		Pattern p1 = Pattern.compile("logtest\\.[0-9]+-[0-9]+-[0-9]+\\.log");
        Matcher m1 = p1.matcher(filename); 
        boolean rs1 = m1.matches(); 
        return rs1; 
    }
	
	public static synchronized int getMyFileNum(){
		return curFileNum++;
	}
	public static synchronized void addCurCountNum(){
		curCountNum++;
	}
	public static synchronized long addCurLineNum(int linenum){
		long myLineNum = curLineNum;
		curCountNum++;
		curLineNum += linenum;
		return myLineNum;
	}
	public static synchronized int getCurFileNum(){
		return curFileNum;
	}
	public static synchronized int getCurCountNum(){
		return curCountNum;
	}
	
	// Each thread will read file, modify file, and save file continually until finish modifying all files.
	public void run() {
		// System.out.println("Running " + threadName);
		// Check whether there are still file not modified;
		//while (curFileNum < totalNum) {
		while (getCurFileNum() < totalNum) {
			// Get the file number this thread will modify, and make curFileNum to next one;
			//int myFileNum = curFileNum++;
			int myFileNum = getMyFileNum();
			// System.out.println(threadName + ": " + myFileNum);
			// Check whether the file is a log file or not;
			if (!isLogFile(files[myFileNum].getName())) {
				//Just make curCountNum to next one, and skip this file;
				//curCountNum++;
				addCurCountNum();
			} else {
				// Read the file;
				//List<String> lines = readFile(files[myFileNum].toString());
				List<String> lines = readFile(files[myFileNum]);
				// Wait if we have not get the start line number for current file;
				//while (curCountNum < myFileNum) {
				while (getCurCountNum() < myFileNum) {
					try {
						Thread.sleep(0);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				// Set start line number of current to curLineNum, add the line numbers to curLineNum, and make curCountNum to next one;
				//int myLineNum = curLineNum;
				//curLineNum += lines.size();
				long myLineNum = addCurLineNum(lines.size());
				//curCountNum++;
				//addCurCountNum();
				// Save file;
				writeFile(files[myFileNum].toString(), lines, myLineNum);
			}
		}
		 System.out.println("Thread " + threadName + " exiting.");
	}

	public void start() {
		 System.out.println("Starting " + threadName);
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}

	public static void main(String args[]) {
		if (args.length != 2) {
			throw new IllegalArgumentException(
					"Exactly 2 parameters required !");
		}
		int ThreadNum = 1;
		try {
			ThreadNum = Integer.parseInt(args[1]);
		} catch (NumberFormatException nbfe) {
			System.out.println("The second parameters should be a number, we will use single thread.");
		}
		files = getFiles(args[0]);
		totalNum = files.length;

		for(int i=0; i<ThreadNum; i++){
			new MultipleThreadSolution("Thread-"+i).start();;
		}
	} 

}
