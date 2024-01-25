import java.util.*;
import java.io.*;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Factory method class used to generate pokemon randomly or through user input
 */
public class PokemonGenerator
{
  /**
   * Hashmap used to store the pokemon from text file.
   * instance created.
   */
  private HashMap<String, String> pokemon = new HashMap<String, String>();
  private static PokemonGenerator instance = null;

  /**
   * PokemonGenerator() constructor method reads from the file and stores the pokemon names and types into the Hashmap
   */
  private PokemonGenerator()
  {
    try {
      Scanner read = new Scanner( new File( "PokemonList.txt" ) );
  
      while( read.hasNextLine() ) {
        String p = read.nextLine();

        String[] s = p.split( "," );

        pokemon.put(s[0], s[1]);
      }
    read.close(); //closes the reader to prevent leaks
    }
    catch(FileNotFoundException fnf) {
      System.out.println( "Pokemon file was not found." );
    }
  } 

  /**
   * Singleton method to check if instance of Map has been made
   */
  public static PokemonGenerator getInstance()
  {
    if( instance == null )
    {
      instance = new PokemonGenerator();
    }
    return instance;
  }
  
  /**
   * generateRandomPokemon() method selects a random key from the PokemonList and uses the information stored in the Hashmap to create a new random pokemon.
   * Also automatically applies a random buff to pokemon of higher level areas
   * @param level is used to pass the level of the area so that the generated pokemon could get the appropriate buff
   * @return generated pokemon name, hp, maxHp
   */
  public Pokemon generateRandomPokemon( int level )
  {
    Pokemon p = null;
    int maxHp = (int) Math.floor(Math.random()*(25-20+1)+20);
    int hp = maxHp;

    List<String> arrayKey = new ArrayList<String>(pokemon.keySet());
    Random r = new Random();

    String s = arrayKey.get(r.nextInt(arrayKey.size()));
    String type = pokemon.get(s);

    if(type.equalsIgnoreCase( "Fire" )){
      p = new Fire( s, hp, maxHp );
    }
    else if(type.equalsIgnoreCase( "Water" )){
      p = new Water( s, hp, maxHp );
    }
    else if(type.equalsIgnoreCase( "Grass" )){
      p = new Grass( s, hp, maxHp );
    }

    int x = level - 1;
    for(int i = 0; i < x; i++){
      p = addRandomBuff( p );
    }
    return p;
  }

  /**
   * getPokemon() method uses the passed name to create a pokemon based off of its saved type in the HashMap
   * @param name is used as an input for the keyset in our hashmap
   * @return selected pokemon name, hp, maxHp
   */
   public Pokemon getPokemon( String name )
  {
    Pokemon p = null;

    int x = (int) Math.floor(Math.random()*(25-20+1)+20);
    int hp = x;
    int maxHp = x;
    String type = pokemon.get( name );

    if(type.equalsIgnoreCase( "Fire" )){
      p = new Fire( name, hp, maxHp );
    }
    else if(type.equalsIgnoreCase( "Water" )){
      p = new Water( name, hp, maxHp );
    }
    else if(type.equalsIgnoreCase( "Grass" )){
      p = new Grass( name, hp, maxHp );
    }

    return p;
  }

  /**
   * addRandomBuff() method adds a random attk/hp buff to the pokemon that is passed through
   * uses random() to choose between buffing the pokemon's hp or attack
   * @param p passes the existing info for the pokemon that is getting buffed
   * @return buffed pokemon name, hp, maxHp
   */
  public Pokemon addRandomBuff( Pokemon p )
  {
    Pokemon buffedPokemon = null;
    int ranBuff = (int)Math.floor(Math.random()*(2-1+1)+1);
    if( ranBuff == 1 ) {
      buffedPokemon = new AttackUp(p);
    }
    else {
     buffedPokemon = new HpUp(p);
    }
    return buffedPokemon;
  } 

  /**
   * addRandomDeBuff() method adds a random attk/hp debuff to the pokemon that is passed through
   * uses random() to choose between debuffing the pokemon's hp or attack
   * @param p passes the existing info for the pokemon that is getting debuffed
   * @return debuffed pokemon name, hp, maxHp
   */
  public Pokemon addRandomDeBuff(Pokemon p)
  {
    Pokemon debuffedPokemon = null;
    int ranDebuff = (int)Math.floor(Math.random()*(2-1+1)+1);
    if( ranDebuff == 1 ) {
      debuffedPokemon = new AttackDown(p);
    }
    else {
      debuffedPokemon = new HpDown(p);
    }
    return debuffedPokemon;
  } 
}