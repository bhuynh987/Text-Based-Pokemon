/**
 * Represents Fire class for pokemon
 */
public class Fire extends Pokemon 
{
  /**
  * Represents Fire constructor
  * @param n is pokemon name
  * @param h is pokemon health
  * @param m is max pokemon health
  */
  Fire( String n, int h, int m)  
  {
    super(n, h, m);
  }
  
  /**
  * Method that detirmines which menu to return depending on type of attack
  * @param int atkType tells which menu to use
  * @return returns either basic menu or fire menu depending on atkType
  */
  public String getAttackMenu(int atkType) 
  {
    String menu = "";
    if(atkType == 1) 
    {
      menu = super.getAttackMenu(atkType);
    }
    else 
    {
      menu = "1. Ember\n2. Fire Blast\n3. Fire Punch";
    }
    return menu;
  }

  /**
  * Method that detirmines how many attack items there are in the menu
  * @param int atkType tells which menu to use
  * @return menuItems returns how many attack items the menu has
  */
  public int getNumAttackMenuItems(int atkType) 
  {
    int menuItems = 0;
    if(atkType == 1) 
    {
      menuItems = super.getNumAttackMenuItems(atkType);
    }
    else {
      menuItems = 3;
    }
    return menuItems;
  }
  
  /**
  * Method that detirmines which attack phrase to use
  * @param int atkType tells which attack to use
  * @param int move specifies which move to use
  * @return partial attack phrase depending on move
  */
  public String getAttackString(int atkType, int move) 
  {
    String atkString = "";
    if(atkType == 1) 
    {
      atkString = super.getAttackString(atkType, move);
    }
    else if(atkType == 2) 
    {
      if(move == 1) {
        atkString = "is engulfed in embers";
      }
      if(move == 2) 
      {
        atkString = "is blasted with fire";
      }
      if(move == 3) 
      {
        atkString = "is punched with fire";
      }
    }
    return atkString;
  }

  /**
  * Method value of damage depening on attack and move
  * @param int atkType tells which attack to use
  * @param int move specifies which move to use
  * @return amount of damage the move does
  */
  public int getAttackDamage(int atkType, int move) 
  {
    int damage = 0;
    if(atkType == 1) 
    {
      damage = super.getAttackDamage(atkType, move);
    }
    else if(atkType == 2) 
    {
      if(move == 1) 
      {
        damage = (int)Math.floor(Math.random()*(4-1+1)+1);
      }
      else if(move == 2) 
      {
        damage = (int)Math.floor(Math.random()*(4-1+1)+1);
      }
      else if(move == 3) 
      {
        damage = (int)Math.floor(Math.random()*(3-1+1)+1);
      }
    }
    return damage;
  }

  /**
  * Method that finds the multiplier of attack
  * @param p is pokemon being attacked
  * @param atkType type of attack used
  * @return calculated multiplier
  */
  public double getAttackMultiplier(Pokemon p, int atkType) 
  {
    double multiplier = 1.0;
    if(atkType == 1) 
    {
      multiplier = super.getAttackMultiplier(p, atkType);
    }
    else if(atkType == 2) 
    {
      multiplier = Pokemon.battleTable[0][p.getType()];
    }
    return multiplier;
  }
}