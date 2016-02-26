package Producer_Consumer;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class file{
	public List<String> lines;
	public int fileNumber;
	public String fileName;
	public long startLineNumber;
	
	public file(List<String> s, int n, String filename, long sl){
		lines = s;
		fileNumber = n;
		fileName = filename;
		startLineNumber = sl;
	}
}
public class StoreHouse {
	private Queue<file> files = new LinkedList<file>();
	private int fileNumber = 0;
	private long lineNumber = 1;
	private int nextRead = 0;
	
	public synchronized boolean getQueueStatus(){
		return files.isEmpty();
	}
	
	public synchronized boolean QueueLimit(){
		return (files.size() > 1000);
	}
	
	public synchronized int getFileNumber(){
		return fileNumber;
	}
	public synchronized int getNextRead(){
		return nextRead;
	}
	public synchronized int ReadNext(){
		return nextRead++;
	}
	
	public synchronized long getLineNumber(List<String> file){
		long curLineNumber = lineNumber;
		lineNumber += file.size();
		fileNumber ++;
		return curLineNumber;
	}
	
	public synchronized void addFile(List<String> file, int fileNumber, String fileName){
		files.add(new file(file, fileNumber, fileName, getLineNumber(file)));
	}
	
	public synchronized file getFile(){
			return files.poll();
	}
}
