package cs3500.solored;

import org.junit.Assert;
import org.junit.Test;
import java.util.Random;
import cs3500.solored.model.hw04.AdvancedSoloRedGameModel;

/**
 * Test public methods for the advanced version of the game: AdvancedSoloRedGameModel.
 * This is the concrete test class, and tests can be found at the abstract class:
 * TestSameInBothModel
 */
public class TestAdvancedSoloRedGameModel extends TestSameInBothModel<AdvancedSoloRedGameModel> {
  @Override
  public void init() {
    game = new AdvancedSoloRedGameModel();
    seededGame = new AdvancedSoloRedGameModel(new Random(4));
    deckWithAllCards = game.getAllCards();
  }

  // test AdvancedSoloRedGameModel constructor throw error when given null as argument
  @Test (expected = IllegalArgumentException.class)
  public void testAdvancedSoloRedGameModelNull() {
    AdvancedSoloRedGameModel nullGame = new AdvancedSoloRedGameModel(null);
  }

  // test drawForHand only allow 1 draw by not playing to canvas
  @Test
  public void testOneDrawNoCanvas() {
    seededGame.startGame(seededGame.getAllCards(), true, 4, 7);
    seededGame.playToPalette(0, 0);
    seededGame.drawForHand();
    Assert.assertEquals("Hand should still be 7 cards", 7, seededGame.getHand().size());
  }

  // test drawForHand allow 2 card draw after playing to canvas
  @Test
  public void testDrawForHandTwoDraw() {
    seededGame.startGame(seededGame.getAllCards(), true, 4, 7);
    seededGame.playToCanvas(0);
    seededGame.playToPalette(0, 0);
    seededGame.drawForHand();
    Assert.assertEquals("Hand should have 7 cards", 7, seededGame.getHand().size());
  }

  // test drawForHand allow 1 card draw after playing to canvas
  @Test
  public void testDrawForHandOneDraw() {
    seededGame.startGame(seededGame.getAllCards(), true, 4, 7);
    seededGame.playToPalette(1, 6);
    seededGame.drawForHand();
    seededGame.playToCanvas(1);
    seededGame.playToPalette(0, 0);
    seededGame.drawForHand();
    Assert.assertEquals("Hand should have 6 cards", 6, seededGame.getHand().size());
  }


}
