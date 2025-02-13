package cs3500.solored;

import org.junit.Assert;
import org.junit.Test;

import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw02.SoloRedCard;
import cs3500.solored.model.hw02.SoloRedGameModel;
import cs3500.solored.model.hw04.AdvancedSoloRedGameModel;
import cs3500.solored.model.hw04.RedGameCreator;

/**
 * Test public methods. The only public method here is createGame since all
 * other public methods are being tested elsewhere.
 */
public class TestPublic {

  // test create game with null type
  @Test(expected = IllegalArgumentException.class)
  public void testCreateGameNull() {
    RedGameCreator.createGame(null);
  }

  // test create game with basic creates a SoloRedGameModel
  @Test
  public void testCreateGameBasicSoloRedGameModel() {
    RedGameModel<SoloRedCard> basic = RedGameCreator.createGame(RedGameCreator.GameType.BASIC);
    Assert.assertEquals(SoloRedGameModel.class, basic.getClass());
  }

  // test create game with advanced creates a AdvancedSoloRedGameModel
  @Test
  public void testCreateGameAdvancedSoloRedGameModel() {
    RedGameModel<SoloRedCard> advanced =
            RedGameCreator.createGame(RedGameCreator.GameType.ADVANCED);
    Assert.assertEquals(AdvancedSoloRedGameModel.class, advanced.getClass());
  }
}
