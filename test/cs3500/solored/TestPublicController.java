package cs3500.solored;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw02.SoloRedCard;
import cs3500.solored.model.hw02.SoloRedGameModel;
import cs3500.solored.controller.SoloRedTextController;

import static org.junit.Assert.assertEquals;

/**
 * Test the public methods of the controller.
 * Also tests the constructor of the controller, and
 * confirms proper input is received and output is displayed
 * through unit tests as mocks. This class contains all unit
 * tests for the controller.
 */
public class TestPublicController {

  private SoloRedGameModel model;

  @Before
  public void init() {
    model = new SoloRedGameModel();
  }

  // test Controller constructor when given null readable
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNullReadable() {
    new SoloRedTextController(null, new StringBuilder());
  }

  // test Controller constructor when given null appendable
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNullAppendable() {
    new SoloRedTextController(new StringReader("input"), null);
  }

  // test Controller constructor when given both null readable and appendable
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithBothNull() {
    new SoloRedTextController(null, null);
  }


  // Verify that inputs are not read after quitting
  @Test
  public void testInputNotReadAfterQuit() throws IOException {
    Appendable mockLog = new StringBuilder();
    RedGameModel<SoloRedCard> gameModel = new MockConfirmInputs(mockLog);

    Reader in = new StringReader("q\ncanvas\n2");

    Appendable log = new StringBuilder();
    SoloRedTextController controller = new SoloRedTextController(in, log);

    controller.playGame(gameModel, model.getAllCards(), false, 3, 5);

    assertEquals("startGame called with deck size = 35, shuffle = false,"
            + " numPalettes = 3, handSize = 5\n", mockLog.toString());
  }

  // Verify that the controller passed the correct input to the game model when user
  // provides valid canvas command and index
  @Test
  public void testInputsPlayToCanvas() throws IOException {
    Appendable mockLog = new StringBuilder();
    RedGameModel<SoloRedCard> gameModel = new MockConfirmInputs(mockLog);

    Reader in = new StringReader("canvas\n2\nq");

    Appendable log = new StringBuilder();
    SoloRedTextController controller = new SoloRedTextController(in, log);

    controller.playGame(gameModel, model.getAllCards(), false, 3, 5);

    assertEquals("startGame called with deck size = 35, shuffle = false,"
            + " numPalettes = 3, handSize = 5\n"
            + "playToCanvas cardIndex = 1\n", mockLog.toString());
  }

  // Verify that the controller passed no input to game model when user
  // provides valid canvas command but invalid index (a letter).
  @Test
  public void testLetterInputsPlayToCanvas() throws IOException {
    Appendable mockLog = new StringBuilder();
    RedGameModel<SoloRedCard> gameModel = new MockConfirmInputs(mockLog);

    Reader in = new StringReader("canvas\na\nq");

    Appendable log = new StringBuilder();
    SoloRedTextController controller = new SoloRedTextController(in, log);

    controller.playGame(gameModel, model.getAllCards(), false, 3, 5);

    assertEquals("startGame called with deck size = 35, shuffle = false,"
            + " numPalettes = 3, handSize = 5\n", mockLog.toString());
  }

  // Verify that the controller passed no input to game model when user
  // provides valid canvas command but invalid index (negative number).
  @Test
  public void testNegativeNumberInputsPlayToCanvas() throws IOException {
    Appendable mockLog = new StringBuilder();
    RedGameModel<SoloRedCard> gameModel = new MockConfirmInputs(mockLog);

    Reader in = new StringReader("canvas\n-1\nq");

    Appendable log = new StringBuilder();
    SoloRedTextController controller = new SoloRedTextController(in, log);

    controller.playGame(gameModel, model.getAllCards(), false, 3, 5);

    assertEquals("startGame called with deck size = 35, shuffle = false,"
            + " numPalettes = 3, handSize = 5\n", mockLog.toString());
  }

  // Verify that the controller passed no input to game model when user
  // provides valid canvas command but a valid out of range number as index
  // Also tests that playToCanvas Exceptions Do Not Halt Program
  @Test
  public void testValidOutOfRangeNumberInputsPlayToCanvas() throws IOException {
    Appendable mockLog = new StringBuilder();
    RedGameModel<SoloRedCard> gameModel = new MockConfirmInputs(mockLog);

    Reader in = new StringReader("canvas\n500\nq");

    Appendable log = new StringBuilder();
    SoloRedTextController controller = new SoloRedTextController(in, log);

    controller.playGame(gameModel, model.getAllCards(), false, 3, 5);

    assertEquals("startGame called with deck size = 35, shuffle = false,"
            + " numPalettes = 3, handSize = 5\n"
            + "playToCanvas cardIndex = 499\n", mockLog.toString());
  }

  // Verify that the controller passed input of 0 to game model when user
  // provides valid canvas command and 0 index
  @Test
  public void testZeroAsInputPlayToCanvas() throws IOException {
    Appendable mockLog = new StringBuilder();
    RedGameModel<SoloRedCard> gameModel = new MockConfirmInputs(mockLog);

    Reader in = new StringReader("canvas\n0\nq");

    Appendable log = new StringBuilder();
    SoloRedTextController controller = new SoloRedTextController(in, log);

    controller.playGame(gameModel, model.getAllCards(), false, 3, 5);

    assertEquals("startGame called with deck size = 35, shuffle = false,"
            + " numPalettes = 3, handSize = 5\n"
            + "playToCanvas cardIndex = -1\n", mockLog.toString());
  }

  // Verify that the controller passed input of 1 to game model when user
  // provides valid canvas command and 1 index
  @Test
  public void testOneAsInputPlayToCanvas() throws IOException {
    Appendable mockLog = new StringBuilder();
    RedGameModel<SoloRedCard> gameModel = new MockConfirmInputs(mockLog);

    Reader in = new StringReader("canvas\n1\nq");

    Appendable log = new StringBuilder();
    SoloRedTextController controller = new SoloRedTextController(in, log);

    controller.playGame(gameModel, model.getAllCards(), false, 3, 5);

    assertEquals("startGame called with deck size = 35, shuffle = false,"
            + " numPalettes = 3, handSize = 5\n"
            + "playToCanvas cardIndex = 0\n", mockLog.toString());
  }

  // Verify that the controller throws an exception when there is no more input after calling canvas
  @Test(expected = IllegalStateException.class)
  public void testEndOfInputPlayToCanvas() throws IOException {
    Appendable mockLog = new StringBuilder();
    RedGameModel<SoloRedCard> gameModel = new MockConfirmInputs(mockLog);

    Reader in = new StringReader("canvas\n500");

    Appendable log = new StringBuilder();
    SoloRedTextController controller = new SoloRedTextController(in, log);

    controller.playGame(gameModel, model.getAllCards(), false, 3, 5);
  }

  // Verify that the controller throws an exception when
  // there is no more input after calling palette
  @Test(expected = IllegalStateException.class)
  public void testEndOfInputPlayToPalette() throws IOException {
    Appendable mockLog = new StringBuilder();
    RedGameModel<SoloRedCard> gameModel = new MockConfirmInputs(mockLog);

    Reader in = new StringReader("palette\n2\n2");

    Appendable log = new StringBuilder();
    SoloRedTextController controller = new SoloRedTextController(in, log);

    controller.playGame(gameModel, model.getAllCards(), false, 3, 5);
  }

  // Verify that the controller passed no input to game model when user
  // provides valid palette command but a valid out of range number as palette and card index
  // Also tests that playToPalette Exceptions Do Not Halt Program
  @Test
  public void testValidOutOfRangeNumberInputsPlayToPalette() throws IOException {
    Appendable mockLog = new StringBuilder();
    RedGameModel<SoloRedCard> gameModel = new MockConfirmInputs(mockLog);

    Reader in = new StringReader("palette\n500\n500\nq");

    Appendable log = new StringBuilder();
    SoloRedTextController controller = new SoloRedTextController(in, log);

    controller.playGame(gameModel, model.getAllCards(), false, 3, 5);

    assertEquals("startGame called with deck size = 35, shuffle = false,"
            + " numPalettes = 3, handSize = 5\n"
            + "playToPalette paletteIndex = 499, cardIndex = 499\n", mockLog.toString());
  }

  // Verify that the controller passed no input to game model when user
  // provides valid palette command but invalid palette index (negative number).
  @Test
  public void testNegativePaletteIndexInputsPlayToPalette() throws IOException {
    Appendable mockLog = new StringBuilder();
    RedGameModel<SoloRedCard> gameModel = new MockConfirmInputs(mockLog);

    Reader in = new StringReader("palette\n-1\n3\nq");

    Appendable log = new StringBuilder();
    SoloRedTextController controller = new SoloRedTextController(in, log);

    controller.playGame(gameModel, model.getAllCards(), false, 3, 5);

    assertEquals("startGame called with deck size = 35, shuffle = false,"
            + " numPalettes = 3, handSize = 5\n", mockLog.toString());
  }

  // Verify that the controller passed no input to game model when user
  // provides valid palette command and palette index
  // but invalid card index (negative number).
  @Test
  public void testNegativeCardIndexInputsPlayToPalette() throws IOException {
    Appendable mockLog = new StringBuilder();
    RedGameModel<SoloRedCard> gameModel = new MockConfirmInputs(mockLog);

    Reader in = new StringReader("palette\n1\n-3\nq");

    Appendable log = new StringBuilder();
    SoloRedTextController controller = new SoloRedTextController(in, log);

    controller.playGame(gameModel, model.getAllCards(), false, 3, 5);

    assertEquals("startGame called with deck size = 35, shuffle = false,"
            + " numPalettes = 3, handSize = 5\n", mockLog.toString());
  }

  // Verify that the controller passed the correct input to the game model when user
  // provides valid palette command and palette/card index
  // Also, Verify that the controller passed the correct input to the game model when user
  // provides valid inputs to be passed to startGame
  @Test
  public void testInputsPlayToPalette() throws IOException {
    Appendable mockLog = new StringBuilder();
    RedGameModel<SoloRedCard> gameModel = new MockConfirmInputs(mockLog);

    Reader in = new StringReader("palette\n2\n2\nq");

    Appendable log = new StringBuilder();
    SoloRedTextController controller = new SoloRedTextController(in, log);

    controller.playGame(gameModel, model.getAllCards(), false, 3, 5);

    assertEquals("startGame called with deck size = 35, shuffle = false,"
            + " numPalettes = 3, handSize = 5\n"
            + "playToPalette paletteIndex = 1, cardIndex = 1\n", mockLog.toString());
  }

  // Verify that the controller passed no input to game model when user
  // provides valid palette command but invalid palette index (a letter).
  @Test
  public void testLetterInputsPlayToPalette() throws IOException {
    Appendable mockLog = new StringBuilder();
    RedGameModel<SoloRedCard> gameModel = new MockConfirmInputs(mockLog);

    Reader in = new StringReader("palette\na\n2\nq");

    Appendable log = new StringBuilder();
    SoloRedTextController controller = new SoloRedTextController(in, log);

    controller.playGame(gameModel, model.getAllCards(), false, 3, 5);

    assertEquals("startGame called with deck size = 35, shuffle = false,"
            + " numPalettes = 3, handSize = 5\n", mockLog.toString());
  }

  // verify that a game has ended properly when only legal inputs for palette are used
  // on a mock model
  @Test
  public void testGameEndsMockModelOnlyValidLegalInputOnlyPalette() throws IOException {
    Appendable mockLog = new StringBuilder();
    RedGameModel<SoloRedCard> gameModel = new MockModelEnds(mockLog);

    Reader in = new StringReader("palette\n1\n1\n");

    Appendable log = new StringBuilder();
    SoloRedTextController controller = new SoloRedTextController(in, log);

    List<SoloRedCard> deck = new ArrayList<>();
    deck.add(model.getAllCards().get(1));
    deck.add(model.getAllCards().get(2));
    deck.add(model.getAllCards().get(3));
    deck.add(model.getAllCards().get(4));

    controller.playGame(gameModel, deck, false, 3, 1);

    assertEquals("Game has ended\n", mockLog.toString());
  }

  // verify that a game has ended properly when invalid inputs used for palette
  // on a mock model
  @Test
  public void testGameEndsMockModelWithInvalidControllerInputPalette() throws IOException {
    Appendable mockLog = new StringBuilder();
    RedGameModel<SoloRedCard> gameModel = new MockModelEnds(mockLog);

    Reader in = new StringReader("palette\na\n-1\n1\n1\n");

    Appendable log = new StringBuilder();
    SoloRedTextController controller = new SoloRedTextController(in, log);

    List<SoloRedCard> deck = new ArrayList<>();
    deck.add(model.getAllCards().get(1));
    deck.add(model.getAllCards().get(2));
    deck.add(model.getAllCards().get(3));
    deck.add(model.getAllCards().get(4));

    controller.playGame(gameModel, deck, false, 3, 1);

    assertEquals("Game has ended\n", mockLog.toString());
  }

  // verify that a game has ended properly when only legal inputs for canvas
  // and palette are used on a mock model
  @Test
  public void testGameEndMockModelOnlyValidLegalInputPaletteAndCanvas() throws IOException {
    Appendable mockLog = new StringBuilder();
    RedGameModel<SoloRedCard> gameModel = new MockModelEnds(mockLog);

    Reader in = new StringReader("canvas\n1\npalette\n1\n1\n");

    Appendable log = new StringBuilder();
    SoloRedTextController controller = new SoloRedTextController(in, log);

    List<SoloRedCard> deck = new ArrayList<>();
    deck.add(model.getAllCards().get(1));
    deck.add(model.getAllCards().get(2));
    deck.add(model.getAllCards().get(3));
    deck.add(model.getAllCards().get(4));
    deck.add(model.getAllCards().get(5));

    controller.playGame(gameModel, deck, false, 3, 2);

    assertEquals("Game has ended\n", mockLog.toString());
  }

  // verify that a game has ended properly when invalid inputs used for canvas
  // and palette on a mock model
  @Test
  public void testGameEndsMockModelWithInvalidControllerInputPaletteAndCanvas() throws IOException {
    Appendable mockLog = new StringBuilder();
    RedGameModel<SoloRedCard> gameModel = new MockModelEnds(mockLog);

    Reader in = new StringReader("canvas\na\n-1\n1\npalette\na\n-1\n1\na\n-1\n1\n");

    Appendable log = new StringBuilder();
    SoloRedTextController controller = new SoloRedTextController(in, log);

    List<SoloRedCard> deck = new ArrayList<>();
    deck.add(model.getAllCards().get(1));
    deck.add(model.getAllCards().get(2));
    deck.add(model.getAllCards().get(3));
    deck.add(model.getAllCards().get(4));
    deck.add(model.getAllCards().get(5));

    controller.playGame(gameModel, deck, false, 3, 2);

    assertEquals("Game has ended\n", mockLog.toString());
  }

  // verify that a game has ended properly when after trying to play to canvas twice
  @Test
  public void testSmallGameThatEndsWithMockModelWithModelExceptions() throws IOException {
    Appendable mockLog = new StringBuilder();
    RedGameModel<SoloRedCard> gameModel = new MockModelEnds(mockLog);

    Reader in = new StringReader("canvas\n1\ncanvas\n1\npalette\n1\n1\n");

    Appendable log = new StringBuilder();
    SoloRedTextController controller = new SoloRedTextController(in, log);

    List<SoloRedCard> deck = new ArrayList<>();
    deck.add(model.getAllCards().get(1));
    deck.add(model.getAllCards().get(2));
    deck.add(model.getAllCards().get(3));
    deck.add(model.getAllCards().get(4));
    deck.add(model.getAllCards().get(5));

    controller.playGame(gameModel, deck, false, 3, 2);

    assertEquals("Game has ended\n", mockLog.toString());
  }

  // verify that a game has ended properly when after trying to play to canvas
  // with one card in hand
  @Test
  public void testGameThatEndsMockModelWithModelExceptionsCanvasOneCard() throws IOException {
    Appendable mockLog = new StringBuilder();
    RedGameModel<SoloRedCard> gameModel = new MockModelEnds(mockLog);

    Reader in = new StringReader("canvas\n1\npalette\n1\n1\n");

    Appendable log = new StringBuilder();
    SoloRedTextController controller = new SoloRedTextController(in, log);

    List<SoloRedCard> deck = new ArrayList<>();
    deck.add(model.getAllCards().get(1));
    deck.add(model.getAllCards().get(2));
    deck.add(model.getAllCards().get(3));
    deck.add(model.getAllCards().get(4));

    controller.playGame(gameModel, deck, false, 3, 1);

    assertEquals("Game has ended\n", mockLog.toString());
  }

  // verify that a game has ended properly when after trying to play to canvas
  // with negative index
  @Test
  public void testGameThatEndsMockModelWithModelExceptionsCanvasNegIndex() throws IOException {
    Appendable mockLog = new StringBuilder();
    RedGameModel<SoloRedCard> gameModel = new MockModelEnds(mockLog);

    Reader in = new StringReader("canvas\n-1\n1\npalette\n1\n1\n");

    Appendable log = new StringBuilder();
    SoloRedTextController controller = new SoloRedTextController(in, log);

    List<SoloRedCard> deck = new ArrayList<>();
    deck.add(model.getAllCards().get(1));
    deck.add(model.getAllCards().get(2));
    deck.add(model.getAllCards().get(3));
    deck.add(model.getAllCards().get(4));



    controller.playGame(gameModel, deck, false, 3, 1);

    assertEquals("Game has ended\n", mockLog.toString());
  }

  // verify that a game has ended properly when after trying to play to canvas
  // with zero index
  @Test
  public void testGameThatEndsMockModelWithModelExceptionsCanvasZeroIndex() throws IOException {
    Appendable mockLog = new StringBuilder();
    RedGameModel<SoloRedCard> gameModel = new MockModelEnds(mockLog);

    Reader in = new StringReader("canvas\n-1\n0\n1\npalette\n1\n1\n");

    Appendable log = new StringBuilder();
    SoloRedTextController controller = new SoloRedTextController(in, log);

    List<SoloRedCard> deck = new ArrayList<>();
    deck.add(model.getAllCards().get(1));
    deck.add(model.getAllCards().get(2));
    deck.add(model.getAllCards().get(3));
    deck.add(model.getAllCards().get(4));

    controller.playGame(gameModel, deck, false, 3, 1);

    assertEquals("Game has ended\n", mockLog.toString());
  }

}
