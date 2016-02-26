package Producer_Consumer;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

public class Consumer implements Runnable{
	
	private StoreHouse storeHouse;
	private int totalnum;
	
	public Consumer(StoreHouse storeHouse, int num){
		this.storeHouse = storeHouse;
		this.totalnum = num;
	}

	// This function can write all lines in a list to a file, and add line
	// information to each line before writing to file.
	public static void writeFile(String FilePath, List<String> lines,
			long StartLineNumber) {
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
	
	@Override
    public void run() {
          while(storeHouse.getFileNumber() < totalnum || !storeHouse.getQueueStatus()){
        	  file fileToWrite = storeHouse.getFile();
        	  if(fileToWrite != null){
        		  writeFile(fileToWrite.fileName, fileToWrite.lines, fileToWrite.startLineNumber);
        	  }
          }
    }
}
