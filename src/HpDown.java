/**
 * Concrete decorator class that removes HP from pokemon
 */
public class HpDown extends PokemonDecorator {
  /**
   * Constructor for HpDown decoration
   * @param p The pokemon that is affected by decoration
   */
  public HpDown(Pokemon p) {
    super(p, "-HP", -1);
  }
}