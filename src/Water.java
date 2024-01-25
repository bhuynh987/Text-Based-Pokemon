/**
 * Public interface used for Water type pokemon
 */
public class Water extends Pokemon {
  /**
   * constructor for water type pokemon
   * @param n passes the name of pokemon 
   * @param h pasess the hp of the pokemon
   * @param m passes the maxHp of the pokemon
   */
  public Water(String n, int h, int m) {
    super(n, h, m);
  }

  /**
   * getAttackMenu() method used to print the apropriate attack menu for the water atkType
   * @param atkType selects the attack menu
   * @return the base attack or special attack menu
   */
  public String getAttackMenu( int atkType ) {
    String menu = "";
    if( atkType == 1 ) {
      menu = super.getAttackMenu( atkType );
    }
    else {
      menu = "1. Water gun\n2. Bubble Beam\n3. Waterfall";
    }
    return menu;
  }

  /**
   * getNumAttackMenu() method used to retrieve the number of attack menu items
   * @param atkType selects the attack menu
   * @return the number of attack menu items
   */
  public int getNumAttackMenu( int atkType ) {
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
   * getAttackString() method used to print the apropriate attack string for a water type pokemon
   * @param atkType selects the attack menu
   * @param move selects the type of move being made
   * @return the appropriate selected string
   */
  public String getAttackString( int atkType, int move ) {
    String atkString = "";
    if( atkType == 1 ) {
      atkString = super.getAttackString( atkType, move );
    }
    else if( atkType == 2 ) {
      if( move == 1 ) {
        atkString = "is blasted with a forceful shot of water";
      }
      if( move == 2 ) {
        atkString = "is sprayed with a stream of bubbles";
      }
      if( move == 3 ) {
        atkString = "drowns in a raging waterfall";
      }
    }
    return atkString;
  }

  /**
   * getAttackDamage() method used to randomly select the damage done to pokemon
   * @param atkType selects the attack menu
   * @param move selects the type of move being made
   * @return the amount of damage that is made
   */
  public int getAttackDamage( int atkType, int move ) {
    int damage = 0;
    if( atkType == 1 ) {
      damage = super.getAttackDamage(atkType, move);
    }
    else if( atkType == 2 ) {
      if(move == 1) {
        damage = (int)(Math.random()*5-2+1) + 2;
      }
      else if(move == 2) {
        damage = (int)(Math.random()*3-1+1) + 1;
      }
      else if(move == 3) {
        damage = (int)(Math.random()*4-1+1) + 1;
      }
    }
    return damage;
  }

  /**
   * getAttackMultiplier() method used to calculate the attack multiplier
   * @param atkType selects the attack menu
   * @param p passes the pokemon getting the multiplier
   * @return the multiplier for the attack
   */
  public double getAttackMultiplier( Pokemon p, int atkType ) {
    double multiplier = 1.0;
    if( atkType == 1 ) {
      multiplier = super.getAttackMultiplier( p, atkType );
    }
    else if( atkType == 2 ) {
      multiplier = Pokemon.battleTable[1][p.getType()];
    }
    return multiplier;
  }
}