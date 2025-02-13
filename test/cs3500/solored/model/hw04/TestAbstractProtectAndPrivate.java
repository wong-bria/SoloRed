package cs3500.solored.model.hw04;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This is the abstract test class where duplicate tests are stored between
 * TestProtectAndPrivateADVANCED and TestProtectAndPrivateBASIC
 * for protected and private method tests.
 * @param <M> The model of a RedGameModel: SoloRedGameModel for basic
 *            or AdvancedSoloRedGameModel for advanced
 */
public abstract class TestAbstractProtectAndPrivate<M extends AbstractSoloRedGameModel> {
  protected M game;

  @Before
  public abstract void init();



  // test findWinningPaletteByHighestCard for red rule
  @Test
  public void testFindWinningPaletteByHighestCard() {
    game.startGame(game.getAllCards(), false, 3, 3);

    int result = WinningPaletteEvaluator.findWinningPaletteByHighestCard(game);
    Assert.assertEquals(2, result);
  }

  // test findWinningPaletteByMostCommonNumber for orange rule
  @Test
  public void testFindWinningPaletteByMostCommonNumber() {
    game.startGame(game.getAllCards(), false, 3, 3);

    int result = WinningPaletteEvaluator.findWinningPaletteByMostCommonNumber(game);
    Assert.assertEquals(2, result);
  }

  // test findWinningPaletteByMostDifferentColors for blue rule
  @Test
  public void testFindWinningPaletteByMostDifferentColors() {
    game.startGame(game.getAllCards(), false, 3, 3);

    int result = WinningPaletteEvaluator.findWinningPaletteByMostDifferentColors(game);
    Assert.assertEquals(2, result);
  }

  // test findWinningPaletteByLongestRun for indigo rule
  @Test
  public void testFindWinningPaletteByLongestRun() {
    game.startGame(game.getAllCards(), false, 3, 3);

    int result = WinningPaletteEvaluator.findWinningPaletteByLongestRun(game);
    Assert.assertEquals(2, result);
  }

  // test findWinningPaletteByMostCardsBelowFour for violet rule
  @Test
  public void testFindWinningPaletteByMostCardsBelowFour() {
    game.startGame(game.getAllCards(), false, 3, 3);

    int result = WinningPaletteEvaluator.findWinningPaletteByMostCardsBelowFour(game);
    Assert.assertEquals(2, result);
  }
}
