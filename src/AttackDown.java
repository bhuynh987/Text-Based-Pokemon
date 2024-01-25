/**
 * concrete decorator class that removes attack points from pokemon
 */
public class AttackDown extends PokemonDecorator
{
  
  /**
   * constructor for AttackDown
   * @param p is the pokemon that is effected by this
   */
  public AttackDown(Pokemon p)
  {
    super(p, "-ATK", 0);
  }
  
  /**
   * returns the damage debuff the pokemon will do when debuffed
   * @param atkType used to pass atkType to super
   */
  public int getAttackBonus(int atkType)
  {
    int dmgBonus = (int)Math.floor(Math.random()*(2-1+1)+1);
    return -dmgBonus + super.getAttackBonus(atkType);
  }
}