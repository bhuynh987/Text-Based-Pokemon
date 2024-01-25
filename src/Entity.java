/**
 * Represents an Entity object
 */
public abstract class Entity {
  private String name;
  private int hp;
  private int maxHP;

  /**
   * Constructs an Entity object with specific parameters
   * @param n The name of the entity
   * @param h The current HP of the entity
   * @param m The max HP of the entity
   */
  public Entity( String n, int h, int m ) {
    name = n;
    hp = h;
    maxHP = m;
  }

  /**
   * Returns the HP of the entity
   * @return An int representing the current HP of the entity
   */
  public int getHP() {
    if( hp < 0 ) {
      hp = 0;
    }
    return hp;
  }

  /**
   * Returns the max HP of the entity
   * @return An int representing the max HP of the entity
   */
  public int getMaxHP() {
    return maxHP;
  }

  /**
   * Deals specified amount of damage to implicit entity
   * @param d The amount of damage entity will take
   */
  public void takeDamage( int d ) {
    /*
     * Checks if implicit parameter is above 0 HP. If they are, create tempHP, else set HP to 0
     * If they are above 0 HP, set tempHP to current HP - damage 
     * If tempHP is a negative number after taking damage set HP to 0, else set HP to tempHP
     */
    if( this.getHP() > 0)  {
      int tempHP = hp - d;
      if( tempHP < 0 ) {
        hp = 0;
      }
      else {
        hp = tempHP;
      }
    }
    else if( this.getHP() <= 0 ) {
      hp = 0;
    }
  }

  /**
   * Heals implicit entity to full HP
   */
  public void heal() {
    hp = maxHP;
  }

  /**
   * Returns the name of the entity
   * @return A String that represents the name of the entity
   */
  public String getName() {
    return name;
  }

  /**
   * String that represents information of the entity
   * @return A string relaying information of specified entity
   */
  public String toString() {
    return name + " HP: " + hp + "/" + maxHP;
  }
}