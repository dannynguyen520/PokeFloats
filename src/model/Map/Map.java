package model.Map;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


@SuppressWarnings("serial")
public class Map implements Serializable {

	private MapElement[][] map;
	private HashMap<Point, String> doors;
	private int maxWidth;
	private int maxHeight;
	private String mapName;
	private String doorNames[];
	private Point currPoint;
	
	
	/**
	 * Constructor
	 * This class take an url to a text file and generates a 2D array
	 * of a map
	 * @param fileName
	 */
	public Map(String fileName, Point start){
		
		doors = new HashMap<Point, String>();
		mapName = fileName;
		currPoint = start;
		
		File mapTxt = new File(fileName);
		try {
			BufferedReader br = new BufferedReader(new FileReader(mapTxt));
			int row = 0;
			int col = 0;
			String line;
			MapElement cave = new MapElement("Cave");
			MapElement grass = new MapElement("Grass");
			MapElement space = new MapElement("Space");
			MapElement wall = new MapElement("Wall");
			MapElement door = new MapElement("Door");
			
			line = br.readLine();
			String dimensions[] = line.split(" ");
			maxHeight = Integer.parseInt(dimensions[0]);
			maxWidth = Integer.parseInt(dimensions[1]);
			
			line = br.readLine();
			doorNames = line.split(" ");
			
			map = new MapElement[maxHeight][maxWidth];
			
			while ((line = br.readLine()) != null) {
				
				for(int i = 0; i < line.length(); i++) {
					switch (line.charAt(i)) {
					
						case 'C':
							map[row][col] = cave;
							break;
						case 'G':
							map[row][col] = grass;
							break;
						case 'S':
							map[row][col] = space;
							break;
						case 'W':
							map[row][col] = wall;
							break;
						case 'D':
							map[row][col] = door;
							addDoor(new Point(col, row));
							break;
						default:
							
					}
					col++;
				}
				col = 0;
				row++;	
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	private void addDoor(Point at){
		doors.put(at, doorNames[doors.size()]);
		
	}
	
	public HashMap<Point, String> getDoors() {
		return doors;
	}
	
	public String getMapName() {
		return mapName;
	}
	
	public Point getLastPoint() {
		return currPoint;
	}
	
	
	/**
	 * This returns the map
	 * @return A 2D array representation of the map 
	 */
	public MapElement[][] getMap() {
		return map;
	}



	/**
	 * It prints the map
	 */
	public void printMap() {
		
		for(int row = 0; row < maxHeight; row++) {
			for(int col = 0; col < maxWidth; col++) {
				System.out.printf("%8s",map[row][col].getElement());
			}
			System.out.println();
		}
		
	}
	
}

