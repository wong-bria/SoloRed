package cs3500.solored;

import org.junit.Test;
import java.util.Random;
import cs3500.solored.model.hw02.SoloRedGameModel;

/**
 * Test public methods for the basic version of the game: SoloRedGameModel.
 * This is the concrete test class, and tests can be found at the abstract class:
 * TestSameInBothModel
 */
public class TestBasicSoloRedGameModel extends TestSameInBothModel<SoloRedGameModel> {
  @Override
  public void init() {
    game = new SoloRedGameModel();
    seededGame = new SoloRedGameModel(new Random(4));
    deckWithAllCards = game.getAllCards();
  }

  // test SoloRedGameModel constructor throw error when given null as argument
  @Test(expected = IllegalArgumentException.class)
  public void testSoloRedGameModelNull() {
    SoloRedGameModel nullGame = new SoloRedGameModel(null);
  }
}
