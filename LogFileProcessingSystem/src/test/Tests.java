package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import MultipleThread.MultipleThreadSolution;

public class Tests {
	@Test
	public void isLogFileTest(){
		assertTrue(MultipleThreadSolution.isLogFile("logtest.2010-06-11.log"));
		assertFalse(MultipleThreadSolution.isLogFile("logtest.2010--06-11.log"));
		assertFalse(MultipleThreadSolution.isLogFile("logtest.2010-06-11.logg"));
		assertTrue(MultipleThreadSolution.isLogFile("logtest.2010-10-11.log"));
		assertFalse(MultipleThreadSolution.isLogFile("logtet.2010-06-11.log"));
		
	}
	
	@Test
	//Create a new file and test the readFile and writeFile function.
	public void ReadAndWriteFileTest(){
		List<String> s = new ArrayList<String>();
		s.add("first line");
		s.add("second line");
		s.add("third line");
		MultipleThreadSolution.writeFile("test.txt",s,0);
		File file = new File("test.txt");
		List<String> in = MultipleThreadSolution.readFile(file);
		for(int i=0; i<s.size(); i++){
			assertEquals("line: " + i, i + ". " + s.get(i), in.get(i));
		}
		file.delete();
	}
	
	public static void main(String[] args) {
	    Result result = JUnitCore.runClasses(Tests.class);
	    for (Failure failure : result.getFailures()) {
	      System.out.println(failure.toString());
	    }
	  }
	
}
