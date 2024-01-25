/**
 * Public class used for Grass type pokemon
 */
public class Grass extends Pokemon {

  /**
   * Constructor for Grass type objects
   * @param n The Grass type object's name
   * @param h The current HP of the object
   * @param m The max HP of  the object
   */
  public Grass( String n, int h, int m)  {
    super(n, h, m);
  }
  
  /**
   * Method to display either basic attack menu or grass attack menu
   * @param atkType The type of attack to be executed
   * @return The menu based on what atkType is
   */
  public String getAttackMenu( int atkType ) {
    String menu = "";
    if( atkType == 1 ) {
      menu = super.getAttackMenu( atkType );
    }
    else {
      menu = "1. Vine Whip\n2. Razor Leaf\n3. Solar Beam";
    }
    return menu;
  }

  /**
   * Method to get the number of items in the attack menu
   * @param atkType The type of attack to be executed
   * @return The number of items in the selected menu
   */
  public int getNumAttackMenuItems( int atkType ) {
    int menuItems = 0;
    if( atkType == 1 ) {
      menuItems = super.getNumAttackMenuItems( atkType );
    }
    else {
      menuItems = 3;
    }
    return menuItems;
  }

  /**
   * Method to receive the partial string of an attack
   * @param atkType The type of attack to be executed
   * @param move The specific move to be performed
   * @return The partial attack string of the chosen move
   */
  public String getAttackString( int atkType, int move ) {
    String atkString = "";
    if( atkType == 1 ) {
      atkString = super.getAttackString( atkType, move );
    }
    else if( atkType == 2 ) {
      if( move == 1 ) {
        atkString = "is ferociously whipped by vines";
      }
      if( move == 2 ) {
        atkString = "is cut by razor leaves";
      }
      if( move == 3 ) {
        atkString = "is struck by a solar beam";
      }
    }
    return atkString;
  }

  /**
   * Method to get the damage of the chosen move
   * @param atkType The type of attack to be executed
   * @param move The specific move to calculate damage of
   * @return The amount of damage the specified move does
   */
  public int getAttackDamage(int atkType, int move) {
    int damage = 0;
    if(atkType == 1) {
      damage = super.getAttackDamage(atkType, move);
    }
    else if(atkType == 2) {
      if(move == 1) {
        damage = (int)Math.floor(Math.random()*(3-1+1)+1);
      }
      else if(move == 2) {
        damage = (int)Math.floor(Math.random()*(4-2+1)+2);
      }
      else if(move == 3) {
        damage = (int)Math.floor(Math.random()*(5-0+1)+0);
      }
    }
    return damage;
  }

  /**
   * Method to get the multiplier of an attack move
   * @param p The pokemon being attacked
   * @param atkType The type of attack to be executed
   * @return The multiplier between the two object's type
   */
  public double getAttackMultiplier(Pokemon p, int atkType) {
    double multiplier = 1.0;
    if(atkType == 1) {
      multiplier = super.getAttackMultiplier(p, atkType);
    }
    else if(atkType == 2) {
      multiplier = Pokemon.battleTable[2][p.getType()];
    }
    return multiplier;
  }
}