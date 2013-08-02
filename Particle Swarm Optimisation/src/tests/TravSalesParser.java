package tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TravSalesParser {
	
	private String problem;
	
	public TravSalesParser(){}
	
	public TravSalesParser(String problem){
		this.problem = problem;
	}
	
	public List<City> extractNodes(){
		List<City> result = new ArrayList<City>();
		BufferedReader buff = null;
		try{
			File file = new File(problem);
			buff = new BufferedReader(new FileReader(file));
			boolean nodes = false;
			String str;
			while((str = buff.readLine()) != null){
				if(nodes == true && !str.equals("EOF")){
					City city = parseCity(str);
					result.add(city);
				}
				if(str.equals("NODE_COORD_SECTION")){
					nodes = true;
				}
			}
		}catch(FileNotFoundException ex){
			ex.printStackTrace();
		}catch(IOException ex){
			ex.printStackTrace();
		}finally{
			try{
				if(buff != null){
					buff.close();
				}
			}catch(IOException ex){
				ex.printStackTrace();
			}
			
		}
		return result;
	}

	private City parseCity(String str) {
		City city = new City();
		Pattern numPattern = Pattern.compile("[0-9]+");
		Matcher match = numPattern.matcher(str);
		boolean index = true;
		String s = null;
		while(match.find()){
			s = match.group();
			if(index){
				double nodeIndex = Double.parseDouble(s);
				city.setIndex(nodeIndex);
				index = false;
			}else{
				double coordinate = Double.parseDouble(s);
				city.getCoordinates().add(coordinate);
			}
		}
		return city;
	}
}
