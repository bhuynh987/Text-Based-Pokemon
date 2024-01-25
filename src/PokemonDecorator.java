/**
* Represents Pokemon decorator class
**/
public abstract class PokemonDecorator extends Pokemon {
  private Pokemon pokemon;
  
  /**
  * Pokemon decorator constructor calls the constructor of the superclass
  */
  public PokemonDecorator( Pokemon p, String extraName, int extraHp ) 
  {
    super (p.getName() + " " + extraName, p.getHP() + extraHp, p.getMaxHP() + extraHp );
    pokemon = p;
  }

  /**
  * Method that detirmines which menu to return depending on type attack
  * @param int atkType tells which menu to use
  * @return returns either basic menu or fire menu depending on atkType
  */
  public String getAttackMenu( int atkType ) 
  {
    return pokemon.getAttackMenu( atkType );
  }

  /**
  * Method that detirmines how many attack items there are in the menu
  * @param int atkType tells which menu to use
  * @return menuItems returns how many attack items the menu has
  */
  public int getNumAttackMenuItems( int atkType ) 
  {
    return pokemon.getNumAttackMenuItems( atkType );
  }

  /**
  * Method that detirmines which attack phrase to use
  * @param int atkType tells which attack to use
  * @param int move specifies which move to use
  * @return partial attack phrase depending on move
  */
  public String getAttackString( int atkType, int move ) 
  {
    return pokemon.getAttackString( atkType, move );
  }

  /**
  * Method that finds the multiplier of attack
  * @param p is pokemon being attacked
  * @param atkType type of attack used
  * @return calculated multiplier
  */
  public double getAttackMultiplier( Pokemon p, int type )
  {
    return pokemon.getAttackMultiplier( p, type );
  }

  /**
   * returns the extra damage the pokemon will do when buffed
   * @param atkType used to pass atkType to super
   * @return The amount of damage bonus
   */
  public int getAttackBonus( int atkType ) {
    return pokemon.getAttackBonus( atkType );
  }

  /**
   * Determines the typing of the Pokemon
   * @return 0 if Fire Pokemon, 1 if Water Pokemon, 2 if Grass Pokemon
   */
  public int getType() {
    return pokemon.getType();
  }
}