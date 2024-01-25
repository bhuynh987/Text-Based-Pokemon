import java.util.ArrayList;
import java.awt.Point;
/**
 * Represents a trainer.
 */
public class Trainer extends Entity
{
  private int money;
  private int potions;
  private int pokeballs;
  private Point loc;
  private ArrayList<Pokemon> pokemon = new ArrayList<Pokemon>();

  /**
   * Constructor to create Trainer object
   * @param n The trainer's name 
   * @param p The trainer's starter pokemon
   * @param m The map the trainer is on
   */
  public Trainer(String n, Pokemon p) {
    super(n, 25, 25);
    this.pokemon.add(p);
    this.money = 20;
    this.potions = 2;
    this.pokeballs = 3;
    this.loc = Map.getInstance().findStart();
  }

  /**
   * Method returns current money amount
   * @return The money variable
   */
  public int getMoney() {
    return money;
  }

  /**
   * Method checks if Trainer is able to spend money and if they are, spend it
   * @param amt The amount that Trainer would like to spend
   * @return True if trainer can spend money, false if trainer cannot
   */
  public boolean spendMoney( int amt ) {
    boolean canSpendMoney = false;
    if (amt <= money )
    {
      this.money -= amt;
      canSpendMoney = true;
    }
    return canSpendMoney;
  }

  /**
   * Method adds money to money variable
   * @param amt Amount of money that is added
   */
  public void receiveMoney( int amt ) {
    money += amt;
  }

  /**
   * Method checks if trainer has potions
   * @return True if trainer has potions and false if amount of potions is 0
   */
  public boolean hasPotions() {
    boolean ifPotions = false;
    if( potions > 0 ) {
      ifPotions = true;
    }
    return ifPotions;
  }

  /**
   * Method represents getting 1 potion
   */
  public void receivePotion() {
    potions++;
  }

  /**
   * Method allows trainer to use potion to heal pokemon at a given index
   * @param pokeIndex Index of pokemon that needs to be healed
   */
  public void usePotion( int pokeIndex ) {
    if( hasPotions() ) {
      pokemon.get(pokeIndex).heal();
      pokemon.set(pokeIndex, PokemonGenerator.getInstance().addRandomBuff(pokemon.get(pokeIndex)));
    }
  }

  /**
   * Method checks if trainer has pokeballs
   * @return True if trainer has pokeballs and false if amount of pokeballs is 0
   */
  public boolean hasPokeballs() {
    boolean ifPokeballs = false;
    if( pokeballs > 0 ) {
      ifPokeballs = true;
    }
    return ifPokeballs;
  }

  /**
   * Method represents getting 1 pokeball
   */
  public void receivePokeball() {
    pokeballs++;
  }

  /**
   * Method shows if trainer caught pokemon
   * @param p Is the pokemon that trainer is attempting to catch
   * @return True if pokemon was caught, false if not caught
   */
  public boolean catchPokemon( Pokemon p ) {
    boolean caught = false;
    double currentHP = (double)p.getHP()/(double)p.getMaxHP();
    double catchPercentage = 1.0 - currentHP;
    double ranNum = Math.random();

    if( catchPercentage >= ranNum ) {
      caught = true;
      pokemon.add(p);
    }

    pokeballs--;
    return caught;
  }

  /**
   * Method gets current location of trainer
   * @return current value of variable loc
   */
  public Point getLocation() {
    return loc;
  }

  /**
   * Method moves trainer up if location is valid, otherwise stays
   * @return char at new location if in bounds. If out of bounds return m
   */  
  public char goNorth() {
    Map.getInstance().reveal(loc);
    Point tempLoc = new Point(loc);
    tempLoc.move( loc.x - 1, loc.y );
    char tempChar = Map.getInstance().getCharAtLoc(tempLoc);
    
    if( tempChar == 'm' ) {
      return 'm';
    }
    else {
      loc = tempLoc;
    }
    return tempChar;
  }

  /**
   * Method moves trainer down if location is valid, otherwise stays
   * @return char at new location if in bounds. If out of bounds return m
   */  
  public char goSouth() {
    Map.getInstance().reveal( loc );
    Point tempLoc = new Point (loc );
    tempLoc.move( loc.x + 1, loc.y );
    char tempChar = Map.getInstance().getCharAtLoc(tempLoc);

    if( tempChar == 'm' ) {
      return 'm';
    }
    else {
      loc = tempLoc;
    }
    return tempChar;
  }

  /**
   * Method moves trainer position to right if valid, otherwise trainer stays
   * @return char at new location if in bounds. If out of bounds return m
   */  
  public char goEast() {
    Map.getInstance().reveal( loc );
    Point tempLoc = new Point( loc );
    tempLoc.move ( loc.x, loc.y + 1 );
    char tempChar = Map.getInstance().getCharAtLoc(tempLoc);

    if( tempChar == 'm' ) {
        return 'm';
    }
    else {
      loc = tempLoc;
    }
    return tempChar;
  }

  /**
   * Method moves trainer position to left if location valid, otherwise trainer stays
   * @return char at new location if in bounds. If out of bounds return m
   */  
  public char goWest() {
    Map.getInstance().reveal( loc );
    Point tempLoc = new Point ( loc );
    tempLoc.move( tempLoc.x, tempLoc.y - 1 );
    char tempChar = Map.getInstance().getCharAtLoc(tempLoc);

    if( tempChar == 'm' ) {
      return 'm';
    }
    else {
      loc = tempLoc;
    }
    return tempChar;
  }

  /**
   * Method returns how many pokemons in pokemon list
   * @return The size of Pokemon ArrayList
   */
  public int getNumPokemon() {
    return pokemon.size();
  }

  /**
   * Method that heals all Pokemon within the ArrayList
   */
  public void healAllPokemon() {
    for( int i = 0; i < pokemon.size(); i++ ) {
      pokemon.get(i).heal();
    }
  }

  /**
   * Method buffs all pokemon in trainer's party
   */
  public void buffAllPokemon() {
    Pokemon buffedPokemon = null;
    for(int i = 0; i < getNumPokemon(); i++) {
      buffedPokemon = getPokemon(i);
      pokemon.set(i, PokemonGenerator.getInstance().addRandomBuff(buffedPokemon));
    }
  }

  /**
   * Method debuffs pokemon at chosen index
   * @param index The index of chosen pokemon
   */
  public void debuffPokemon( int index ) {
    Pokemon debuffedPokemon = pokemon.get(index);
    pokemon.set(index, PokemonGenerator.getInstance().addRandomDeBuff(debuffedPokemon));
  }

  /**
   * Method returns pokemon at specified index
   * @param index Is the index of wanted pokemon
   * @return A pokemon at parameter index
   */
  public Pokemon getPokemon( int index ) {
    return pokemon.get( index );
  }

  /**
   * Method returns position of Pokemon in ArrayList and displays their name and HP
   * @return Pokemon's names and health
   */
  public String getPokemonList() {
    String pokemonNames = "";
    for( int i = 0; i < pokemon.size(); i++ ) {
      pokemonNames += (String.valueOf(i + 1)) + ". " + pokemon.get(i).getName() + " HP: " + pokemon.get(i).getHP() +  "/" + pokemon.get(i).getMaxHP() + "\n";
    }
    return pokemonNames;
  }

  /**
   * Method returns position of Pokemon in ArrayList and displays their name and HP
   * @param index The index of the selected pokemon in the ArraytList
   * @return The pokemon that was removed
   */
  public Pokemon removePokemon( int index ) {
    Pokemon removedPokemon = pokemon.get( index-1 );
    pokemon.remove( index-1 );
    return removedPokemon;
  }

  /**
   * Method prints out a user readable version of things trainer has such as amount
   * pokeballs, potions, money and things of that nature and also using toString of the e
   * @return String Showing amount of money, potions, pokeballs and list of Pokemon in readable format
   */
  @Override
  public String toString() {
    String str = super.toString() + "\nMoney: $" + money + "\nPotions: " + potions + 
                  "\nPoke Balls: " + pokeballs + "\nPokemon\n-------\n" + getPokemonList() + "\nMap:\n" + Map.getInstance().mapToString(loc) + "\n";
    return str;
  }
}