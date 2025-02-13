package cs3500.solored;

import org.junit.Test;

import java.io.StringReader;
import java.util.Random;

import cs3500.solored.controller.SoloRedTextController;
import cs3500.solored.model.hw04.AdvancedSoloRedGameModel;

import static org.junit.Assert.assertEquals;


/**
 * This is the concrete test class for AdvancedSoloRedGameModel to test
 * integration between the view, model, and controller.
 */
public class TestADVANCEDIntegration extends AbstractIntegrationTest<AdvancedSoloRedGameModel> {

  @Override
  public void init() {
    game = new AdvancedSoloRedGameModel();
    seededGame = new AdvancedSoloRedGameModel(new Random(4));
  }

  // test allow 1 draw after playToPalette
  @Test
  public void testOneDrawAfterPalette() {
    StringReader input = new StringReader("palette 2 7 canvas 2 palette 1 1 q");
    StringBuilder gameLog = new StringBuilder();

    SoloRedTextController controller = new SoloRedTextController(input, gameLog);
    controller.playGame(seededGame, seededGame.getAllCards(), true, 4, 7);

    assertEquals("Canvas: R\n"
            + "P1: R2\n"
            + "P2: O5\n"
            + "> P3: V6\n"
            + "P4: R5\n"
            + "Hand: I3 I2 O4 O3 V3 B3 O7\n"
            + "Number of cards in deck: 24\n"
            + "Canvas: R\n"
            + "P1: R2\n"
            + "> P2: O5 O7\n"
            + "P3: V6\n"
            + "P4: R5\n"
            + "Hand: I3 I2 O4 O3 V3 B3 O1\n"
            + "Number of cards in deck: 23\n"
            + "Canvas: I\n"
            + "P1: R2\n"
            + "> P2: O5 O7\n"
            + "P3: V6\n"
            + "P4: R5\n"
            + "Hand: I3 O4 O3 V3 B3 O1\n"
            + "Number of cards in deck: 23\n"
            + "Canvas: I\n"
            + "> P1: R2 I3\n"
            + "P2: O5 O7\n"
            + "P3: V6\n"
            + "P4: R5\n"
            + "Hand: O4 O3 V3 B3 O1 R4\n"
            + "Number of cards in deck: 22\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "Canvas: I\n"
            + "> P1: R2 I3\n"
            + "P2: O5 O7\n"
            + "P3: V6\n"
            + "P4: R5\n"
            + "Hand: O4 O3 V3 B3 O1 R4\n"
            + "Number of cards in deck: 22\n", gameLog.toString());
  }

  // test allow 2 draw after playToPalette
  @Test
  public void testTwoDrawAfterPalette() {
    StringReader input = new StringReader("canvas 1 palette 1 6 q");
    StringBuilder gameLog = new StringBuilder();

    SoloRedTextController controller = new SoloRedTextController(input, gameLog);
    controller.playGame(seededGame, seededGame.getAllCards(), true, 4, 7);

    assertEquals("Canvas: R\n"
            + "P1: R2\n"
            + "P2: O5\n"
            + "> P3: V6\n"
            + "P4: R5\n"
            + "Hand: I3 I2 O4 O3 V3 B3 O7\n"
            + "Number of cards in deck: 24\n"
            + "Canvas: I\n"
            + "P1: R2\n"
            + "P2: O5\n"
            + "> P3: V6\n"
            + "P4: R5\n"
            + "Hand: I2 O4 O3 V3 B3 O7\n"
            + "Number of cards in deck: 24\n"
            + "Canvas: I\n"
            + "> P1: R2 O7\n"
            + "P2: O5\n"
            + "P3: V6\n"
            + "P4: R5\n"
            + "Hand: I2 O4 O3 V3 B3 O1 R4\n"
            + "Number of cards in deck: 22\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "Canvas: I\n"
            + "> P1: R2 O7\n"
            + "P2: O5\n"
            + "P3: V6\n"
            + "P4: R5\n"
            + "Hand: I2 O4 O3 V3 B3 O1 R4\n"
            + "Number of cards in deck: 22\n", gameLog.toString());
  }


}
