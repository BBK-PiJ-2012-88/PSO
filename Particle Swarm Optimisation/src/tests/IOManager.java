package tests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class IOManager {
	
	public void writeTofile(String content, File file){
		System.out.println("write to file: " + content);
		BufferedWriter writer = null;
		try{
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(content);
			writer.newLine();
		}catch(FileNotFoundException ex){
			ex.printStackTrace();
		}catch(IOException ex){
			ex.printStackTrace();
		}finally{
			try{
				if(writer != null){
					writer.flush();
					writer.close();
				}
			}catch(IOException ex){
				ex.printStackTrace();
			}
		}
	}
}
