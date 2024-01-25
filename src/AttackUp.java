/**
 * concrete decorator class that adds attack points to pokemon
 */
public class AttackUp extends PokemonDecorator
{

  /**
   * constructor for AttackDown
   * @param p is the pokemon that is effected by this
   */
  public AttackUp(Pokemon p)
  {
    super(p, "+ATK", 0);
  }

  /**
   * returns the extra damage the pokemon will do when buffed
   * @param atkType used to pass atkType to super
   */
  public int getAttackBonus(int atkType)
  {
    int dmgBonus = (int)Math.floor(Math.random()*(2-1+1)+1);
    return dmgBonus + super.getAttackBonus(atkType);
  }
}