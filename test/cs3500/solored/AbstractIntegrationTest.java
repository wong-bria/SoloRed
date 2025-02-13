package cs3500.solored;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import cs3500.solored.controller.SoloRedTextController;
import cs3500.solored.model.hw02.SoloRedCard;
import cs3500.solored.model.hw04.AbstractSoloRedGameModel;
import static org.junit.Assert.assertEquals;


/**
 * This is the abstract test class to store duplicate tests between TestBASICIntegration
 * and TestADVANCEDIntegration. Tests the public methods of the controller through
 * integration tests with the model, view, and controller.
 * @param <M> The model of a RedGameModel: SoloRedGameModel for basic
 *           or AdvancedSoloRedGameModel for advanced
 */
public abstract class AbstractIntegrationTest<M extends AbstractSoloRedGameModel> {
  protected M game;
  protected M seededGame;

  @Before
  public abstract void init();

  // test output of invalid command: something other than 'q', 'Q', 'palette', 'canvas'
  @Test
  public void testOutputOfInvalidCommand() throws IOException {
    StringReader input = new StringReader("aoisfai\nq");
    StringBuilder gameLog = new StringBuilder();

    SoloRedTextController controller = new SoloRedTextController(input, gameLog);
    controller.playGame(game, game.getAllCards(), false, 2, 2);

    assertEquals("Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n"
            + "Invalid command. Try again. Commands are: 'q', 'Q', 'palette', or 'canvas'\n"
            + "Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n", gameLog.toString());
  }

  // test output of q command at start
  @Test
  public void testLowerCaseQCommandAtStart() throws IOException {
    StringReader input = new StringReader("q");
    StringBuilder gameLog = new StringBuilder();

    SoloRedTextController controller = new SoloRedTextController(input, gameLog);
    controller.playGame(game, game.getAllCards(), false, 2, 2);

    assertEquals("Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n", gameLog.toString());
  }

  // test output of Q command at start
  @Test
  public void testUpperCaseQCommandAtStart() throws IOException {
    StringReader input = new StringReader("Q");
    StringBuilder gameLog = new StringBuilder();

    SoloRedTextController controller = new SoloRedTextController(input, gameLog);
    controller.playGame(game, game.getAllCards(), false, 2, 2);

    assertEquals("Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n", gameLog.toString());
  }

  // test output of Q command after calling canvas
  @Test
  public void testUpperCaseQCommandAtCanvas() throws IOException {
    StringReader input = new StringReader("canvas\nQ");
    StringBuilder gameLog = new StringBuilder();

    SoloRedTextController controller = new SoloRedTextController(input, gameLog);
    controller.playGame(game, game.getAllCards(), false, 2, 2);

    assertEquals("Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n", gameLog.toString());
  }

  // test output of q command after calling canvas
  @Test
  public void testLowerCaseQCommandAtCanvas() throws IOException {
    StringReader input = new StringReader("canvas\nq");
    StringBuilder gameLog = new StringBuilder();

    SoloRedTextController controller = new SoloRedTextController(input, gameLog);
    controller.playGame(game, game.getAllCards(), false, 2, 2);

    assertEquals("Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n", gameLog.toString());
  }

  // test output of Q command after calling palette and putting Q as palette index
  @Test
  public void testUpperCaseQCommandAtPaletteIndex() throws IOException {
    StringReader input = new StringReader("palette\nQ");
    StringBuilder gameLog = new StringBuilder();

    SoloRedTextController controller = new SoloRedTextController(input, gameLog);
    controller.playGame(game, game.getAllCards(), false, 2, 2);

    assertEquals("Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n", gameLog.toString());
  }

  // test output of q command after calling palette and putting Q as palette index
  @Test
  public void testLowerCaseQCommandAtPaletteIndex() throws IOException {
    StringReader input = new StringReader("palette\nq");
    StringBuilder gameLog = new StringBuilder();

    SoloRedTextController controller = new SoloRedTextController(input, gameLog);
    controller.playGame(game, game.getAllCards(), false, 2, 2);

    assertEquals("Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n", gameLog.toString());
  }

  // test output of Q command after calling palette and putting Q as card index
  @Test
  public void testUpperCaseQCommandAtCardIndex() throws IOException {
    StringReader input = new StringReader("palette\n1\nQ");
    StringBuilder gameLog = new StringBuilder();

    SoloRedTextController controller = new SoloRedTextController(input, gameLog);
    controller.playGame(game, game.getAllCards(), false, 2, 2);

    assertEquals("Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n", gameLog.toString());
  }

  // test output of q command after calling palette and putting Q as card index
  @Test
  public void testLowerCaseQCommandAtCardIndex() throws IOException {
    StringReader input = new StringReader("palette\n1\nq");
    StringBuilder gameLog = new StringBuilder();

    SoloRedTextController controller = new SoloRedTextController(input, gameLog);
    controller.playGame(game, game.getAllCards(), false, 2, 2);

    assertEquals("Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n", gameLog.toString());
  }


  // test playToCanvas output with letters as inputs
  @Test
  public void testPlayToCanvasWithLettersAsNumbersInput() throws IOException {
    StringReader input = new StringReader("canvas\na\nb");
    StringBuilder gameLog = new StringBuilder();

    SoloRedTextController controller = new SoloRedTextController(input, gameLog);
    controller.playGame(game, game.getAllCards(), false, 2, 2);

    assertEquals("Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n", gameLog.toString());
  }

  // test playToCanvas output with 0 as inputs
  @Test
  public void testPlayToCanvasWithZeroAsInput() throws IOException {
    StringReader input = new StringReader("canvas\n0\nq");
    StringBuilder gameLog = new StringBuilder();

    SoloRedTextController controller = new SoloRedTextController(input, gameLog);
    controller.playGame(game, game.getAllCards(), false, 2, 2);

    assertEquals("Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n"
            + "Invalid move. Try again. Card index in hand must be"
            + " greater than zero and less than the total number of cards in hand!\n"
            + "Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n", gameLog.toString());
  }

  // test playToCanvas output with negative number as inputs
  @Test
  public void testPlayToCanvasWithNegativeNumberInput() throws IOException {
    StringReader input = new StringReader("canvas\n-1\nq");
    StringBuilder gameLog = new StringBuilder();

    SoloRedTextController controller = new SoloRedTextController(input, gameLog);
    controller.playGame(game, game.getAllCards(), false, 2, 2);

    assertEquals("Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n", gameLog.toString());
  }

  // test the user doing playToCanvas with correct index then quitting
  @Test
  public void testPlayToCanvas() throws IOException {
    StringBuilder out = new StringBuilder();
    StringReader in = new StringReader("canvas 4\nq\n");
    SoloRedTextController controller = new SoloRedTextController(in, out);

    controller.playGame(game, game.getAllCards(), false, 4, 7);

    String expectedOutput = "Canvas: R\n"
            + "P1: R1\n"
            + "P2: R2\n"
            + "P3: R3\n"
            + "> P4: R4\n"
            + "Hand: R5 R6 R7 O1 O2 O3 O4\n"
            + "Number of cards in deck: 24\n"
            + "Canvas: O\n"
            + "P1: R1\n"
            + "P2: R2\n"
            + "P3: R3\n"
            + "> P4: R4\n"
            + "Hand: R5 R6 R7 O2 O3 O4\n"
            + "Number of cards in deck: 24\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "Canvas: O\n"
            + "P1: R1\n"
            + "P2: R2\n"
            + "P3: R3\n"
            + "> P4: R4\n"
            + "Hand: R5 R6 R7 O2 O3 O4\n"
            + "Number of cards in deck: 24\n";

    assertEquals(expectedOutput, out.toString());
  }

  // test the user doing playToCanvas with natural number out of range as card index
  // Also tests that playToCanvas Exceptions Do Not Halt Program
  @Test
  public void testPlayToCanvasOutOfRange() throws IOException {
    StringBuilder out = new StringBuilder();
    StringReader in = new StringReader("canvas 500\nq\n");
    SoloRedTextController controller = new SoloRedTextController(in, out);

    controller.playGame(game, game.getAllCards(), false, 4, 7);

    String expectedOutput = "Canvas: R\n"
            + "P1: R1\n"
            + "P2: R2\n"
            + "P3: R3\n"
            + "> P4: R4\n"
            + "Hand: R5 R6 R7 O1 O2 O3 O4\n"
            + "Number of cards in deck: 24\n"
            + "Invalid move. Try again. Card index in hand must be"
            + " greater than zero and less than the total number of cards in hand!\n"
            + "Canvas: R\n"
            + "P1: R1\n"
            + "P2: R2\n"
            + "P3: R3\n"
            + "> P4: R4\n"
            + "Hand: R5 R6 R7 O1 O2 O3 O4\n"
            + "Number of cards in deck: 24\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "Canvas: R\n"
            + "P1: R1\n"
            + "P2: R2\n"
            + "P3: R3\n"
            + "> P4: R4\n"
            + "Hand: R5 R6 R7 O1 O2 O3 O4\n"
            + "Number of cards in deck: 24\n";

    assertEquals(expectedOutput, out.toString());
  }

  // test playToPalette output with negative number as palette index
  @Test
  public void testPlayToPaletteWithNegativeNumberInput() throws IOException {
    StringReader input = new StringReader("palette\n-1\n2\nq");
    StringBuilder gameLog = new StringBuilder();

    SoloRedTextController controller = new SoloRedTextController(input, gameLog);
    controller.playGame(game, game.getAllCards(), false, 2, 2);

    assertEquals("Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n", gameLog.toString());
  }

  // test playToPalette output with negative number as card index
  @Test
  public void testPlayToPaletteWithNegativeNumberCardInput() throws IOException {
    StringReader input = new StringReader("palette\n2\n-2\nq");
    StringBuilder gameLog = new StringBuilder();

    SoloRedTextController controller = new SoloRedTextController(input, gameLog);
    controller.playGame(game, game.getAllCards(), false, 2, 2);

    assertEquals("Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n", gameLog.toString());
  }

  // test playToPalette output with letters as inputs
  @Test
  public void testPlayToPaletteWithLettersAsNumbersInput() throws IOException {
    StringReader input = new StringReader("palette\na\nb");
    StringBuilder gameLog = new StringBuilder();

    SoloRedTextController controller = new SoloRedTextController(input, gameLog);
    controller.playGame(game, game.getAllCards(), false, 2, 2);

    assertEquals("Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n", gameLog.toString());
  }

  // test playToPalette output with 0 as palette index
  @Test
  public void testPlayToPaletteWithZeroAsPaletteInput() throws IOException {
    StringReader input = new StringReader("palette\n0\n2\nq");
    StringBuilder gameLog = new StringBuilder();

    SoloRedTextController controller = new SoloRedTextController(input, gameLog);
    controller.playGame(game, game.getAllCards(), false, 2, 2);

    assertEquals("Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n"
            + "Invalid move. Try again. Palette index is greater"
            + " than 0 but less than number of palettes!\n"
            + "Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n", gameLog.toString());
  }

  // test playToPalette output with 0 as card index
  @Test
  public void testPlayToPaletteWithZeroAsCardInput() throws IOException {
    StringReader input = new StringReader("palette\n1\n0\nq");
    StringBuilder gameLog = new StringBuilder();

    SoloRedTextController controller = new SoloRedTextController(input, gameLog);
    controller.playGame(game, game.getAllCards(), false, 2, 2);

    assertEquals("Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n"
            + "Invalid move. Try again. Card index in hand must be"
            + " greater than 0 and less than number of cards in hand!\n"
            + "Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n", gameLog.toString());
  }

  // test playToPalette output with natural number palette index out of bound
  // Also tests that playToPalette Exceptions Do Not Halt Program
  @Test
  public void testPlayToPaletteWithPaletteInputNaturalOutOfBound() throws IOException {
    StringReader input = new StringReader("palette\n50\n1\nq");
    StringBuilder gameLog = new StringBuilder();

    SoloRedTextController controller = new SoloRedTextController(input, gameLog);
    controller.playGame(game, game.getAllCards(), false, 2, 2);

    assertEquals("Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n"
            + "Invalid move. Try again. Palette index is greater"
            + " than 0 but less than number of palettes!\n"
            + "Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n", gameLog.toString());
  }

  // test playToPalette output with natural number card index out of bound
  // Also tests that playToPalette Exceptions Do Not Halt Program
  @Test
  public void testPlayToPaletteWithCardInputNaturalOutOfBound() throws IOException {
    StringReader input = new StringReader("palette\n1\n100\nq");
    StringBuilder gameLog = new StringBuilder();

    SoloRedTextController controller = new SoloRedTextController(input, gameLog);
    controller.playGame(game, game.getAllCards(), false, 2, 2);

    assertEquals("Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n"
            + "Invalid move. Try again. Card index in hand must"
            + " be greater than 0 and less than number of cards in hand!\n"
            + "Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n", gameLog.toString());
  }

  // test playToPalette output with natural number card and palette index in range
  @Test
  public void testPlayToPaletteWithInRangeCardPaletteInputNatural() throws IOException {
    StringReader input = new StringReader("palette\n1\n1\nq");
    StringBuilder gameLog = new StringBuilder();

    SoloRedTextController controller = new SoloRedTextController(input, gameLog);
    controller.playGame(game, game.getAllCards(), false, 2, 2);

    assertEquals("Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31\n"
            + "Canvas: R\n"
            + "> P1: R1 R3\n"
            + "P2: R2\n"
            + "Hand: R4 R5\n"
            + "Number of cards in deck: 30\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "Canvas: R\n"
            + "> P1: R1 R3\n"
            + "P2: R2\n"
            + "Hand: R4 R5\n"
            + "Number of cards in deck: 30\n", gameLog.toString());
  }


  // test expected first state of game
  @Test
  public void testExpectedFirstStateOfGame() throws IOException {
    StringReader input = new StringReader("q");
    StringBuilder gameLog = new StringBuilder();

    SoloRedTextController controller = new SoloRedTextController(input, gameLog);
    controller.playGame(game, game.getAllCards(), false, 2, 2);

    String[] lines = gameLog.toString().split("\n");
    String firstMsg = String.join("\n",
            Arrays.copyOfRange(lines, 0, 5));

    assertEquals("Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4\n"
            + "Number of cards in deck: 31", firstMsg);
  }

  // test controller when no more user input is given
  @Test(expected = IllegalArgumentException.class)
  public void testPlayGameWithNullArgs() throws IOException {
    StringReader input = new StringReader("");
    StringBuilder gameLog = new StringBuilder();

    SoloRedTextController controller = new SoloRedTextController(input, gameLog);
    controller.playGame(null, game.getAllCards(), false, 2, 2);
  }

  // test controller when no more user input is given
  @Test(expected = IllegalStateException.class)
  public void testReadableFailureBehavior() throws IOException {
    StringReader input = new StringReader("");
    StringBuilder gameLog = new StringBuilder();

    SoloRedTextController controller = new SoloRedTextController(input, gameLog);
    controller.playGame(game, game.getAllCards(), false, 2, 2);
  }

  // test when the appendable is failing
  @Test(expected = IllegalStateException.class)
  public void testFailingAppendable() {
    StringReader input = new StringReader("canvas\n-1 -2 -3 -4");
    Appendable gameLog = new FailingAppendable();
    SoloRedTextController controller = new SoloRedTextController(input, gameLog);
    controller.playGame(game, game.getAllCards(), false, 3, 4);
  }

  // test playGame with startGame Exception of numPalettes < 2
  @Test(expected = IllegalArgumentException.class)
  public void testPlayGameBehaviorWithStartGameExceptions() {
    StringReader input = new StringReader("");
    Appendable gameLog = new FailingAppendable();
    SoloRedTextController controller = new SoloRedTextController(input, gameLog);
    controller.playGame(game, game.getAllCards(), false, 0, 4);
  }

  // test playGame with startGame Exception of handSize < 2
  @Test(expected = IllegalArgumentException.class)
  public void testPlayGameBehaviorWithStartGameHandExceptions() {
    StringReader input = new StringReader("");
    Appendable gameLog = new FailingAppendable();
    SoloRedTextController controller = new SoloRedTextController(input, gameLog);
    controller.playGame(game, game.getAllCards(), false, 5, 0);
  }

  // test playGame with startGame Exception of null deck
  @Test(expected = IllegalArgumentException.class)
  public void testPlayGameBehaviorWithStartGameNullExceptions() {
    StringReader input = new StringReader("");
    Appendable gameLog = new FailingAppendable();
    SoloRedTextController controller = new SoloRedTextController(input, gameLog);
    controller.playGame(game, null, false, 5, 3);
  }

  // test playGame with startGame Exception of non unique cards in deck
  @Test(expected = IllegalArgumentException.class)
  public void testPlayGameBehaviorWithStartGameNotUniqueCardsExceptions() {
    StringReader input = new StringReader("");
    Appendable gameLog = new FailingAppendable();
    List<SoloRedCard> deck = new ArrayList<>();
    deck = this.game.getAllCards();
    deck.add(this.game.getAllCards().get(1));
    SoloRedTextController controller = new SoloRedTextController(input, gameLog);
    controller.playGame(game, deck, false, 5, 3);
  }

  // test playGame with startGame Exception of deck not large enough
  @Test(expected = IllegalArgumentException.class)
  public void testPlayGameBehaviorWithStartGameDeckTooSmallExceptions() {
    StringReader input = new StringReader("");
    Appendable gameLog = new FailingAppendable();
    List<SoloRedCard> deck = new ArrayList<>();
    deck.add(this.game.getAllCards().get(1));
    SoloRedTextController controller = new SoloRedTextController(input, gameLog);
    controller.playGame(game, deck, false, 5, 3);
  }

  // test playGame with startGame Exception of game already started
  @Test(expected = IllegalArgumentException.class)
  public void testPlayGameBehaviorWithStartGameAlreadyStartedExceptions() {
    StringReader input = new StringReader("");
    Appendable gameLog = new FailingAppendable();
    List<SoloRedCard> deck = new ArrayList<>();
    deck.add(this.game.getAllCards().get(1));
    SoloRedTextController controller = new SoloRedTextController(input, gameLog);
    controller.playGame(game, deck, false, 5, 3);
  }

  // test that the game still continues when user does playToCanvas with no draw
  @Test
  public void testNoDrawAfterPlayToCanvas() throws IOException {
    StringReader input = new StringReader("canvas\n1\nq");
    StringBuilder gameLog = new StringBuilder();

    SoloRedTextController controller = new SoloRedTextController(input, gameLog);
    controller.playGame(game, this.game.getAllCards(), false, 3, 31);

    assertEquals("Canvas: R\n"
            + "P1: R1\n"
            + "P2: R2\n"
            + "> P3: R3\n"
            + "Hand: R4 R5 R6 R7 O1 O2 O3 O4 O5 O6 O7 B1 B2 B3 B4 B5 B6 B7 I1"
            + " I2 I3 I4 I5 I6 I7 V1 V2 V3 V4 V5 V6\n"
            + "Number of cards in deck: 1\n"
            + "Canvas: R\n"
            + "P1: R1\n"
            + "P2: R2\n"
            + "> P3: R3\n"
            + "Hand: R5 R6 R7 O1 O2 O3 O4 O5 O6 O7 B1 B2 B3 B4 B5 B6 B7 I1 I2 I3"
            + " I4 I5 I6 I7 V1 V2 V3 V4 V5 V6\n"
            + "Number of cards in deck: 1\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "Canvas: R\n"
            + "P1: R1\n"
            + "P2: R2\n"
            + "> P3: R3\n"
            + "Hand: R5 R6 R7 O1 O2 O3 O4 O5 O6 O7 B1 B2 B3 B4 B5 B6 B7 I1 I2 I3 I4"
            + " I5 I6 I7 V1 V2 V3 V4 V5 V6\n"
            + "Number of cards in deck: 1\n", gameLog.toString());
  }

  // test that the game still continues when
  // user does playToPalette with no available draw left
  @Test
  public void testNoDrawAfterPlayToPaletteSinceNoCardLeft() throws IOException {
    StringReader input = new StringReader("palette\n1\n1\nq");
    StringBuilder gameLog = new StringBuilder();

    SoloRedTextController controller = new SoloRedTextController(input, gameLog);
    controller.playGame(game, this.game.getAllCards(), false, 3, 32);

    assertEquals("Canvas: R\n"
            + "P1: R1\n"
            + "P2: R2\n"
            + "> P3: R3\n"
            + "Hand: R4 R5 R6 R7 O1 O2 O3 O4 O5 O6 O7 B1 B2 B3 B4"
            + " B5 B6 B7 I1 I2 I3 I4 I5 I6 I7 V1 V2 V3 V4 V5 V6 V7\n"
            + "Number of cards in deck: 0\n"
            + "Canvas: R\n"
            + "> P1: R1 R4\n"
            + "P2: R2\n"
            + "P3: R3\n"
            + "Hand: R5 R6 R7 O1 O2 O3 O4 O5 O6 O7 B1 B2 B3 B4 B5 B6 B7 "
            + "I1 I2 I3 I4 I5 I6 I7 V1 V2 V3 V4 V5 V6 V7\n"
            + "Number of cards in deck: 0\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "Canvas: R\n"
            + "> P1: R1 R4\n"
            + "P2: R2\n"
            + "P3: R3\n"
            + "Hand: R5 R6 R7 O1 O2 O3 O4 O5 O6 O7 B1 B2 B3 B4 B5 "
            + "B6 B7 I1 I2 I3 I4 I5 I6 I7 V1 V2 V3 V4 V5 V6 V7\n"
            + "Number of cards in deck: 0\n", gameLog.toString());
  }

  // test that the controller displays game over message when user loses
  // Also tests Integration For Loss
  @Test
  public void testControllerPlayGameOverLoseMessage() throws IOException {
    StringReader input = new StringReader("palette\n1\n1\nq");
    StringBuilder gameLog = new StringBuilder();

    SoloRedTextController controller = new SoloRedTextController(input, gameLog);
    controller.playGame(game, this.game.getAllCards(), false, 32, 3);

    assertEquals("Canvas: R\n"
            + "P1: R1\n"
            + "P2: R2\n"
            + "P3: R3\n"
            + "P4: R4\n"
            + "P5: R5\n"
            + "P6: R6\n"
            + "> P7: R7\n"
            + "P8: O1\n"
            + "P9: O2\n"
            + "P10: O3\n"
            + "P11: O4\n"
            + "P12: O5\n"
            + "P13: O6\n"
            + "P14: O7\n"
            + "P15: B1\n"
            + "P16: B2\n"
            + "P17: B3\n"
            + "P18: B4\n"
            + "P19: B5\n"
            + "P20: B6\n"
            + "P21: B7\n"
            + "P22: I1\n"
            + "P23: I2\n"
            + "P24: I3\n"
            + "P25: I4\n"
            + "P26: I5\n"
            + "P27: I6\n"
            + "P28: I7\n"
            + "P29: V1\n"
            + "P30: V2\n"
            + "P31: V3\n"
            + "P32: V4\n"
            + "Hand: V5 V6 V7\n"
            + "Number of cards in deck: 0\n"
            + "Game lost.\n"
            + "Canvas: R\n"
            + "P1: R1 V5\n"
            + "P2: R2\n"
            + "P3: R3\n"
            + "P4: R4\n"
            + "P5: R5\n"
            + "P6: R6\n"
            + "> P7: R7\n"
            + "P8: O1\n"
            + "P9: O2\n"
            + "P10: O3\n"
            + "P11: O4\n"
            + "P12: O5\n"
            + "P13: O6\n"
            + "P14: O7\n"
            + "P15: B1\n"
            + "P16: B2\n"
            + "P17: B3\n"
            + "P18: B4\n"
            + "P19: B5\n"
            + "P20: B6\n"
            + "P21: B7\n"
            + "P22: I1\n"
            + "P23: I2\n"
            + "P24: I3\n"
            + "P25: I4\n"
            + "P26: I5\n"
            + "P27: I6\n"
            + "P28: I7\n"
            + "P29: V1\n"
            + "P30: V2\n"
            + "P31: V3\n"
            + "P32: V4\n"
            + "Hand: V6 V7\n"
            + "Number of cards in deck: 0\n", gameLog.toString());
  }

  // test that the controller displays game won message when user wins
  // Also tests Integration For Win
  @Test
  public void testControllerPlayGameOverWinMessage() throws IOException {
    StringReader input = new StringReader("palette\n1\n1");
    StringBuilder gameLog = new StringBuilder();

    List<SoloRedCard> deck = new ArrayList<>();
    deck.add(game.getAllCards().get(1));
    deck.add(game.getAllCards().get(2));
    deck.add(game.getAllCards().get(3));
    deck.add(game.getAllCards().get(4));

    SoloRedTextController controller = new SoloRedTextController(input, gameLog);
    controller.playGame(game, deck, false, 3, 1);

    assertEquals("Canvas: R\n"
            + "P1: R2\n"
            + "P2: R3\n"
            + "> P3: R4\n"
            + "Hand: R5\n"
            + "Number of cards in deck: 0\n"
            + "Game won.\n"
            + "Canvas: R\n"
            + "> P1: R2 R5\n"
            + "P2: R3\n"
            + "P3: R4\n"
            + "Hand: \n"
            + "Number of cards in deck: 0\n", gameLog.toString());
  }

  // one more test for startGame exception when game is over
  @Test(expected = IllegalArgumentException.class)
  public void testStartGameExceptionGameOver() throws IOException {
    StringReader input = new StringReader("palette\n1\n1");
    StringBuilder gameLog = new StringBuilder();

    List<SoloRedCard> deck = new ArrayList<>();
    deck.add(game.getAllCards().get(1));
    deck.add(game.getAllCards().get(2));
    deck.add(game.getAllCards().get(3));
    deck.add(game.getAllCards().get(4));

    SoloRedTextController controller = new SoloRedTextController(input, gameLog);
    controller.playGame(game, deck, false, 3, 1);
    controller.playGame(game, deck, false, 3, 1);
  }

  // test the integration of model, view, and controller to use bad inputs, then display a loss
  @Test
  public void testIntegrationForLossWithStudentModelAndBadInput() throws IOException {
    StringReader input = new StringReader("palette\na\nb\nc\n-1\n-2\n-3\n1\n1");
    StringBuilder gameLog = new StringBuilder();

    SoloRedTextController controller = new SoloRedTextController(input, gameLog);
    controller.playGame(game, this.game.getAllCards(), false, 32, 3);

    assertEquals("Canvas: R\n"
            + "P1: R1\n"
            + "P2: R2\n"
            + "P3: R3\n"
            + "P4: R4\n"
            + "P5: R5\n"
            + "P6: R6\n"
            + "> P7: R7\n"
            + "P8: O1\n"
            + "P9: O2\n"
            + "P10: O3\n"
            + "P11: O4\n"
            + "P12: O5\n"
            + "P13: O6\n"
            + "P14: O7\n"
            + "P15: B1\n"
            + "P16: B2\n"
            + "P17: B3\n"
            + "P18: B4\n"
            + "P19: B5\n"
            + "P20: B6\n"
            + "P21: B7\n"
            + "P22: I1\n"
            + "P23: I2\n"
            + "P24: I3\n"
            + "P25: I4\n"
            + "P26: I5\n"
            + "P27: I6\n"
            + "P28: I7\n"
            + "P29: V1\n"
            + "P30: V2\n"
            + "P31: V3\n"
            + "P32: V4\n"
            + "Hand: V5 V6 V7\n"
            + "Number of cards in deck: 0\n"
            + "Game lost.\n"
            + "Canvas: R\n"
            + "P1: R1 V5\n"
            + "P2: R2\n"
            + "P3: R3\n"
            + "P4: R4\n"
            + "P5: R5\n"
            + "P6: R6\n"
            + "> P7: R7\n"
            + "P8: O1\n"
            + "P9: O2\n"
            + "P10: O3\n"
            + "P11: O4\n"
            + "P12: O5\n"
            + "P13: O6\n"
            + "P14: O7\n"
            + "P15: B1\n"
            + "P16: B2\n"
            + "P17: B3\n"
            + "P18: B4\n"
            + "P19: B5\n"
            + "P20: B6\n"
            + "P21: B7\n"
            + "P22: I1\n"
            + "P23: I2\n"
            + "P24: I3\n"
            + "P25: I4\n"
            + "P26: I5\n"
            + "P27: I6\n"
            + "P28: I7\n"
            + "P29: V1\n"
            + "P30: V2\n"
            + "P31: V3\n"
            + "P32: V4\n"
            + "Hand: V6 V7\n"
            + "Number of cards in deck: 0\n", gameLog.toString());
  }

  // test Integration of view, model, and controller for Win message after doing bad inputs
  @Test
  public void testIntegrationForWinWithStudentModelAndBadInput() throws IOException {
    StringReader input = new StringReader("palette\na\nb\nc\n-1\n-2\n1\n1");
    StringBuilder gameLog = new StringBuilder();

    List<SoloRedCard> deck = new ArrayList<>();
    deck.add(game.getAllCards().get(1));
    deck.add(game.getAllCards().get(2));
    deck.add(game.getAllCards().get(3));
    deck.add(game.getAllCards().get(4));

    SoloRedTextController controller = new SoloRedTextController(input, gameLog);
    controller.playGame(game, deck, false, 3, 1);

    assertEquals("Canvas: R\n"
            + "P1: R2\n"
            + "P2: R3\n"
            + "> P3: R4\n"
            + "Hand: R5\n"
            + "Number of cards in deck: 0\n"
            + "Game won.\n"
            + "Canvas: R\n"
            + "> P1: R2 R5\n"
            + "P2: R3\n"
            + "P3: R4\n"
            + "Hand: \n"
            + "Number of cards in deck: 0\n", gameLog.toString());
  }

  // test that the deck is randomized when user wants to in playGame
  @Test
  public void testShuffleStartGame() {
    List<SoloRedCard> deck9 = new ArrayList<>();
    deck9.add(this.game.getAllCards().remove(0)); // r1, r2, r3, r4
    deck9.add(this.game.getAllCards().remove(1));
    deck9.add(this.game.getAllCards().remove(2));
    deck9.add(this.game.getAllCards().remove(3));

    List<SoloRedCard> copyDeck = this.game.getAllCards();
    List<SoloRedCard> copyPal = new ArrayList<>();
    copyPal.add(copyDeck.get(3));

    StringReader input = new StringReader("q");
    StringBuilder gameLog = new StringBuilder();


    SoloRedTextController controller = new SoloRedTextController(input, gameLog);

    controller.playGame(seededGame, deck9, true, 3, 1);

    assertEquals("Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R4\n"
            + "P3: R2\n"
            + "Hand: R3\n"
            + "Number of cards in deck: 0\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R4\n"
            + "P3: R2\n"
            + "Hand: R3\n"
            + "Number of cards in deck: 0\n", gameLog.toString());
  }

  // test the user doing playToPalette with natural number out of range as card index
  // Also tests that playToPalette Exceptions Do Not Halt Program
  @Test
  public void testPlayToPaletteOutOfRange() throws IOException {
    StringBuilder out = new StringBuilder();
    StringReader in = new StringReader("palette 500 500 1 1 q");
    SoloRedTextController controller = new SoloRedTextController(in, out);

    controller.playGame(game, game.getAllCards(), false, 4, 7);

    String expectedOutput = "Canvas: R\n"
            + "P1: R1\n"
            + "P2: R2\n"
            + "P3: R3\n"
            + "> P4: R4\n"
            + "Hand: R5 R6 R7 O1 O2 O3 O4\n"
            + "Number of cards in deck: 24\n"
            + "Invalid move. Try again. Palette index is greater than 0"
            + " but less than number of palettes!\n"
            + "Canvas: R\n"
            + "P1: R1\n"
            + "P2: R2\n"
            + "P3: R3\n"
            + "> P4: R4\n"
            + "Hand: R5 R6 R7 O1 O2 O3 O4\n"
            + "Number of cards in deck: 24\n"
            + "Invalid command. Try again. Commands are: 'q', 'Q', 'palette', or 'canvas'\n"
            + "Canvas: R\n"
            + "P1: R1\n"
            + "P2: R2\n"
            + "P3: R3\n"
            + "> P4: R4\n"
            + "Hand: R5 R6 R7 O1 O2 O3 O4\n"
            + "Number of cards in deck: 24\n"
            + "Invalid command. Try again. Commands are: 'q', 'Q', 'palette', or 'canvas'\n"
            + "Canvas: R\n"
            + "P1: R1\n"
            + "P2: R2\n"
            + "P3: R3\n"
            + "> P4: R4\n"
            + "Hand: R5 R6 R7 O1 O2 O3 O4\n"
            + "Number of cards in deck: 24\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "Canvas: R\n"
            + "P1: R1\n"
            + "P2: R2\n"
            + "P3: R3\n"
            + "> P4: R4\n"
            + "Hand: R5 R6 R7 O1 O2 O3 O4\n"
            + "Number of cards in deck: 24\n";

    assertEquals(expectedOutput, out.toString());
  }

  // test to see the different states in a game:
  // start state, state after playing to canvas
  // , state after playing to palette, quit state.
  @Test
  public void testMultipleStatesInGame() throws IOException {
    StringBuilder out = new StringBuilder();
    StringReader in = new StringReader("canvas 1 palette 1 1 q");
    SoloRedTextController controller = new SoloRedTextController(in, out);

    controller.playGame(game, game.getAllCards(), false, 3, 32);

    String expectedOutput = "Canvas: R\n"
            + "P1: R1\n"
            + "P2: R2\n"
            + "> P3: R3\n"
            + "Hand: R4 R5 R6 R7 O1 O2 O3 O4 O5 O6 O7 B1 B2 B3 B4 B5"
            + " B6 B7 I1 I2 I3 I4 I5 I6 I7 V1 V2 V3 V4 V5 V6 V7\n"
            + "Number of cards in deck: 0\n"
            + "Canvas: R\n"
            + "P1: R1\n"
            + "P2: R2\n"
            + "> P3: R3\n"
            + "Hand: R5 R6 R7 O1 O2 O3 O4 O5 O6 O7 B1 B2 B3 "
            + "B4 B5 B6 B7 I1 I2 I3 I4 I5 I6 I7 V1 V2 V3 V4 V5 V6 V7\n"
            + "Number of cards in deck: 0\n"
            + "Canvas: R\n"
            + "> P1: R1 R5\n"
            + "P2: R2\n"
            + "P3: R3\n"
            + "Hand: R6 R7 O1 O2 O3 O4 O5 O6 O7 B1 B2 B3 B4 B5 B6 B7 I1"
            + " I2 I3 I4 I5 I6 I7 V1 V2 V3 V4 V5 V6 V7\n"
            + "Number of cards in deck: 0\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "Canvas: R\n"
            + "> P1: R1 R5\n"
            + "P2: R2\n"
            + "P3: R3\n"
            + "Hand: R6 R7 O1 O2 O3 O4 O5 O6 O7 B1 B2 B3 B4 B5 B6"
            + " B7 I1 I2 I3 I4 I5 I6 I7 V1 V2 V3 V4 V5 V6 V7\n"
            + "Number of cards in deck: 0\n";

    assertEquals(expectedOutput, out.toString());
  }
}
