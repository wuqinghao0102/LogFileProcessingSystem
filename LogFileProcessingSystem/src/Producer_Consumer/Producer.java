package Producer_Consumer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Producer implements Runnable{
	private StoreHouse storeHouse;
	private File[] files;
	private int totalNum;
	public Producer(StoreHouse storeHouse, int num, File[] files){
		this.storeHouse = storeHouse;
		this.totalNum = num;
		this.files = files;
	}
	
	// This function can read the given file, and add the content to a list line by line.
	public static List<String> readFile(File file) {
		List<String> lines = new ArrayList<String>();
		try {
			// File file = new File(FilePath);
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

    @Override
    public void run() {
        while(storeHouse.getNextRead() < totalNum){
        	int fileNum = storeHouse.ReadNext();
        	List<String> lines = readFile(files[fileNum]);
        	// add the file to storehouse when all its previous files are already there.
        	while(storeHouse.getFileNumber() < fileNum){
    			try {
    				Thread.sleep(0);
    			} catch (InterruptedException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    		}
        	// Make sure the storehouse has no more than 1000 files waiting to write.
        	while(storeHouse.QueueLimit()){
        		try {
    				Thread.sleep(1);
    			} catch (InterruptedException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
        	}
        	storeHouse.addFile(lines, fileNum, files[fileNum].getAbsolutePath());
        }
    }
	
	
}
