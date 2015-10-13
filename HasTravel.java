package org.has.travel;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class HasTravel {
	private static final String BEGIN = "H";
	private static final String END = "L";
	private int shortestPath = 0;
	
	private class HasVector {
		
		
		
		private String begin;
		private String end;
		public HasVector (String begin, String end) {
			_set(begin, end);
		}
		
		public HasVector(String line) {
			String[] parts = line.trim().split("\\s+");
			_set(parts[0], parts[1]);
					
			
		}
		
		private void _set(String begin, String end) {
			this.begin = begin;
			this.end = end;
			
		}
		
		public String getBegin() {
			return this.begin;
		}
		
		public String getEnd() {
			return this.end;
		}
		
		public String toString() {
			return this.begin +"=>"+ this.end;
		}
		
	};
	private class HasArray extends ArrayList<HasVector>{}
	String[]  shortestPathArray = null;






	
	
	private String fileData = null;
	
//	HashMap<String, String> data = null;
	
	HasArray data = null;
	HashMap<String, HasArray> indexBegin = null;
	HashMap<String, HasArray> indexEnd = null;
	
	
	public static void main (String[] args) {
		String fileName = args[0];
		HasTravel travel = new HasTravel(fileName);
	}
	
	
	
	public HasTravel(String fileName) {
//		this.data = new HashMap<K, V>();
		this.data = new HasArray();
		this.indexBegin = new HashMap<String, HasArray>();
		this.indexEnd = new HashMap<String, HasArray>();
		
		indexFile(fileName);
//		System.err.println(this.data.size());
		this.shortestPathArray = new String[this.data.size()+1];
		
//		System.err.println(this.indexEnd.toString());
		findPath(BEGIN, new String[1], 1);
		System.err.println(Arrays.toString( this.shortestPathArray ) );
		System.err.println(shortestPath);
		
		
	}
	
	
	private void findPath(String begin, String[] text, int step) {
		if (!this.indexBegin.containsKey(begin)) {
			return;
		}
		for(HasVector thisStation : this.indexBegin.get(begin)) {
			text[step-1] = thisStation.toString();
			if (thisStation.getEnd().equals(END) ) {
				if (shortestPath == 0) {
					shortestPath = step;
					this.shortestPathArray = new String[step];
					System.arraycopy(text, 0, this.shortestPathArray, 0, step);
//					this.shortestPathArray[step] = thisStation.getEnd();
					
				}
				else if (step < shortestPath) {
					shortestPath = step;
					this.shortestPathArray = new String[step];
					System.arraycopy(text, 0, this.shortestPathArray, 0, step);
//					this.shortestPathArray[step] = thisStation.getEnd();
				}
			}
			else {
				
				String[] newData = new String[step+1];
				System.arraycopy(text, 0, newData, 0, step);						
				findPath(thisStation.getEnd(), newData,step+1);
				
			}
		}
	}
	
	
	
	private void indexFile (String fileName){
		
		BufferedReader fileStream =  null;
		try {
			fileStream = new BufferedReader( new InputStreamReader(new FileInputStream(fileName)));
			String line = null;
			while ( (line = fileStream.readLine()) != null) {
				this.data.add(new HasVector(line));
			}
			
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
		catch(IOException ex) {
			ex.printStackTrace();
			
		}
		
		finally {
			try {
				if (fileStream != null) {
					fileStream.close();
				}	
			}
			catch(IOException ex) {
				ex.printStackTrace();
			}
		}
		for (HasVector route : this.data) {
			if (! this.indexBegin.containsKey(route.getBegin() ) ) {
				this.indexBegin.put(route.getBegin(), new HasArray());
			}
			this.indexBegin.get(route.getBegin()).add(route);			
			
			if (! this.indexEnd.containsKey(route.getEnd() ) ) {
				this.indexEnd.put(route.getEnd(), new HasArray());
			}
			this.indexEnd.get(route.getEnd()).add(route);			
		}
	}

}
