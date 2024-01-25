/**
 * Represents map used to generate and edit map based on the way the trainer traverses it during the game
 */
import java.awt.Point;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.*;

public class Map {
  private char [][] map;
  private boolean [][] revealed;
  private static Map instance = null;

  /**
   * Constructor method used to create map objects 
   */
  private Map() {
    map = new char[5][5];
    revealed = new boolean[5][5];
  }

  /**
   * Singleton method to check if instance of Map has been made
   */
  public static Map getInstance()
  {
    if(instance == null)
    {
      instance = new Map();
    }
    return instance;
  }

  /**
   * loadMap() method loads the appropriate area text file based on where the trainer is at throughout the game
   * @param mapNum is used to determine which area text file should be loaded
   * @throws java.io.FileNotFoundException in the event the file is not found
   */
  public void loadMap( int mapNum ) {
    try {
      File fileIn = new File( "Area"+ mapNum + ".txt" );
      Scanner read = new Scanner( fileIn ); //read for file Area1.txt
      int i = 0;

      while( read.hasNextLine() ) {
        int c = 0;
        String p = read.nextLine();

        for( int j = 0; j < 5; j++ ) {
          map[i][j] = p.charAt(c);
          c = c + 2; 
        }
        i++;  
      }
    read.close(); //closes the reader to prevent leaks
    }
    catch(FileNotFoundException e) {
      System.out.println("Map file was not found.");
    }
  }

  /**
   * getCharAtLoc() method is used to find and return the existing char within map that the trainer has traversed to, and is also used to ensure that the trainer is not out of bounds
   * @param p is used to pass the point in which the trainer is currently located at
   * @return the existing char located on the map array
   */
  public char getCharAtLoc( Point p ) {
    char c = 'm';
    if( (p.x < 0) || (4 < p.x) || (p.y < 0) || (4 < p.y) ) {
      return c;
    }
    else{
      c = map[p.x][p.y];
      return c;
    }
  }

  /**
   * mapToString() method is able to print the map array as a string for use in game
   * @param p is used to locate and mark the current location of the trainer
   * @return the visual representation of the map at its current form as a string
   */
  public String mapToString( Point p ) {
    String mapPrint = "";
    
    for( int i = 0; i < 5; i++ ) {
      for ( int j = 0; j < 5; j++ ) {
        Point currentLoc = new Point(i, j);
        if( currentLoc.equals(p) ) {
          mapPrint = mapPrint + "* ";
        }
        else if( revealed[i][j] == true && !currentLoc.equals(p) ) {
          mapPrint = mapPrint + map[i][j] + " ";
        }
        else {
          mapPrint = mapPrint + "x ";
        }
      }
    }
    return mapPrint.substring(0, 9) + "\n" + mapPrint.substring(10, 19) + "\n" + mapPrint.substring(20, 29) + "\n" + mapPrint.substring(30, 39) + "\n" + mapPrint.substring(40, 49);
  }

  /**
   * findStart() method locates the start of the map and creates a point at it
   * @return the point where the start is located
   */
  public Point findStart() {
    int row = 0;
    int col = 0;
    for( int i = 0; i < 5; i++ ){
      for( int j = 0; j < 5; j++ ){
        char c = map[i][j];
        if(c == 's') {
          row = i;
          col = j;
          break;
        }
      }
    }
    Point p = new Point( row, col );
    return p;
  }

  /**
   * reveal() method is used to reveal the chars across the map as the trainer passes by them
   * @param p is used to mark which point on the map the character has traversed with our boolean array revealed[][]
   */
  public void reveal ( Point p ) {
    Point c = new Point(100, 100);
    if( p.x == c.x && p.y == c.y ) {
      for(int i = 0; i < 5; i++) {
        for(int j = 0; j < 5; j++) {
          revealed[i][j] = false;
        }
      }
    }
    else {
      revealed [p.x][p.y] = true;
    }
  }

  /**
   * removeCharAtLoc() method replaces i, p, and w chars on the map after they are either defeated, captured, or found
   * @param p helps check that location on the map so that we are able to detect and make the changes to those chars
   */
  public void removeCharAtLoc( Point p ) {
    map[p.x][p.y] = 'n';
  }
}