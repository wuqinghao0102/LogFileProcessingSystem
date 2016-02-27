package Producer_Consumer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProducerAndConsumer {
	
	
	// This function can to put all files in the given folder into a array, and
	// sort the files.
	public static File[] getFiles(String folderName) {
		// put all the files in an array;
		File folder = new File(folderName);
		File[] listOfFiles = folder.listFiles();
		// Sort the files by name. The files should be ordered by timestamp;
		Arrays.sort(listOfFiles);
		return listOfFiles;
	}
	
	// This function can check whether the given file is a log file.
	public static boolean isLogFile(String filename) {
		Pattern p1 = Pattern.compile("logtest\\.[0-9]+-[0-9]+-[0-9]+\\.log");
		Matcher m1 = p1.matcher(filename);
		boolean rs1 = m1.matches();
		return rs1;
	}
	
	public static File[] filesFilter(File[] files){
		List<File> allfiles = new ArrayList<File>();
		Pattern p1 = Pattern.compile("logtest\\.[0-9]+-[0-9]+-[0-9]+\\.log");
		for(File file : files){
			Matcher m1 = p1.matcher(file.getName());
			if(m1.matches()){
				allfiles.add(file);
			}
		}
		File[] filteredFiles = allfiles.toArray(new File[0]);
		return filteredFiles;
		
	} 
	
	public static void main(String[] args) {
		if (args.length != 3) {
			throw new IllegalArgumentException(
					"Exactly 2 parameters required !");
		}
		File[] allfiles = getFiles(args[0]);
		File[] filteredfiles = filesFilter(allfiles);
		StoreHouse storeHouse = new StoreHouse();
		Producer producer = new Producer(storeHouse, filteredfiles.length, filteredfiles);
		Consumer consumer = new Consumer(storeHouse, filteredfiles.length);
		int ThreadNum_r = 1;
		int ThreadNum_w = 1;
		try {
			ThreadNum_r = Integer.parseInt(args[1]);
			ThreadNum_w = Integer.parseInt(args[2]);
		} catch (NumberFormatException nbfe) {
			System.out.println("The second parameters should be a number, we will use single thread.");
		}
		for(int i=0; i<ThreadNum_r; i++){
			new Thread(producer).start();
		}
		
		for(int i=0; i<ThreadNum_w; i++){
			new Thread(consumer).start();
		}
	}
}
