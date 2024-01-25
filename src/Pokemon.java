/**
 * Represents a Pokemon
 */
public abstract class Pokemon extends Entity {
  public static final double [][] battleTable = {{1, .5, 2}, {2, 1, .5}, {.5, 2, 1}};

  /**
   * Creates Pokemon with specified name and random HP between 20-25
   * @param n The Pokemon's name
   */
  public Pokemon( String n, int h, int m ) {
    super( n, h, m );
  }

  /**
   * Displays the attack menu
   * @return A string of attack menu
   */
  public String getAttackTypeMenu() {
    return "1. Basic Attack\n2. Special Attack";
  }

  /**
   * Returns the number of items in attack menu
   * @return An int 2
   */
  public int getNumAttackTypeMenuItems() {
    return 2;
  }

  /**
   * Displays the basic attack menu
   * @return A string of basic attack menu
   */
  public String getAttackMenu(int atkType) {
    return "1. Slam\n2. Tackle\n3. Punch";
  }

  /**
   * Returns the number of items in the basic attack menu
   * @return An int 3
   */
  public int getNumAttackMenuItems(int atkType) {
    return 3;
  }

  /**
   * Method that performs calculation of total damage and builds the entire attack string
   * @param p The pokemon being attacked
   * @param atkType The type of attack to be executed
   * @param move The move to be executed
   * @return The completed attack string 
   */
  public String attack(Pokemon p, int atkType, int move) {
    String atkString = "";
    int damage = (int)this.getAttackDamage(atkType, move);
    //System.out.println("Raw damage: " + damage); //Debugging
    damage = (int)(damage * this.getAttackMultiplier(p, atkType));
    //System.out.println("Damage multiplied: " + damage); //Debugging
    damage = damage + this.getAttackBonus(atkType); 
    //System.out.println("Damage multiplied + bonus: " + damage); //Debugging

    //If damage is less than 0 due to debuffs then set to 0 to avoid negative damage
    if(damage < 0) {
      damage = 0;
    }
    
    if(atkType == 1) {
      atkString = p.getName() + " is " + this.getAttackString(atkType, move) + " by " + this.getName() + " and took " + damage + " damage.\n";
    }
    else if(atkType == 2) {
      atkString = p.getName() + " " + this.getAttackString(atkType, move) + " and took " + damage + " damage.\n";
    }
    p.takeDamage(damage);
    return atkString;
  }

  /**
   * Method to receive the partial string of an attack
   * @param atkType The type of attack to be executed
   * @param move The specific move to be performed
   * @return The partial attack string of the chosen move
   */
  public String getAttackString(int atkType, int move) {
    String atkString = "";
    if(move == 1) {
      atkString = "slammed";
    }
    else if(move == 2) {
      atkString = "tackled";
    }
    else if(move == 3) {
      atkString = "punched";
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
    if(move == 1) {
      damage = (int)Math.floor(Math.random()*(5-0+1)+0);
    }
    else if(move == 2) {
      damage = (int)Math.floor(Math.random()*(3-2+1)+2);
    }
    else if(move == 3) {
      damage = (int)Math.floor(Math.random()*(4-1+1)+1);
    }
    return damage;
  }

  /**
   * Method to get the multiplier of an attack move
   * @param p The pokemon being attacked
   * @param atkType The type of attack to be executed
   * @return The multiplier for basic moves which is 1.0
   */
  public double getAttackMultiplier(Pokemon p, int atkType) {
    return 1.0; 
  }

  /**
   * Method to get the amount of bonus damage from buffs/debuffs
   * @param atkType The type of attack to be executed
   * @return The amount of bonus damage to be added
   */
  public int getAttackBonus(int atkType) {
    return 0;
  }

  /**
   * Determines the typing of the Pokemon
   * @return An int 0 if Fire Pokemon, 1 if Water Pokemon, 2 if Grass Pokemon
   */
  public int getType() {
    int type = 0;
    if( this instanceof Fire ) {
      type = 0;
    }
    else if( this instanceof Water ) {
      type = 1;
    }
    else if( this instanceof Grass ) {
      type = 2;
    }
    return type;
  }
}