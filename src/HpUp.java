/**
 * Concrete decorator class that adds HP from pokemon
 */
public class HpUp extends PokemonDecorator {
  /**
   * Constructor for HpUp decoration
   * @param p The pokemon that is affected by decoration
   */
  public HpUp(Pokemon p) {
    super(p, "+HP", (int)Math.floor(Math.random()*(2-1+1)+1));
  }
}