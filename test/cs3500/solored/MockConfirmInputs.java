package cs3500.solored;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw02.SoloRedCard;
import cs3500.solored.model.hw02.SoloRedGameModel;

/**
 * A mock of a SoloRedGameModel to confirm user inputs.
 */
public class MockConfirmInputs implements RedGameModel<SoloRedCard> {
  private final Appendable log;

  // 0-index based where 0 is the first card of the canvas and canvas.size() - 1
  // or the last card of the list represents the current canvas card
  private SoloRedCard canvas;

  /**
   * The constructor for a mock of a SoloRedGame to confirm user input.
   *
   * @param log Represents a gameLog to keep track of what's happening in game.
   */
  public MockConfirmInputs(Appendable log) {
    this.log = Objects.requireNonNull(log);
    this.canvas = new SoloRedGameModel().getAllCards().get(1);
    // a 0-index based list where 0 is the top of the deck to be drawn first
    List<SoloRedCard> deck = new ArrayList<>();
  }


  /**
   * Java doc for stub:
   * - try:
   * append to the log the palette index and card index that is passed as user input
   * to the model to check what numbers (should be one less than what user inputted)
   * are passed to the model as a mock.
   * - catch:
   * Intentionally ignored in mock.
   */
  public void playToPalette(int paletteIdx, int cardIdxInHand) {
    try {
      log.append(String.format(
              "playToPalette paletteIndex = %d, cardIndex = %d\n", paletteIdx, cardIdxInHand));
    } catch (IOException ignored) {

    }

  }

  /**
   * Java doc for stub:
   * - try:
   *  append to the log the card index that is passed as user input
   *        to the model to check what number (should be one less than what user inputted)
   *        is passed to the model as a mock.
   * - catch:
   * Intentionally ignored in mock
   */
  public void playToCanvas(int cardIdxInHand) {
    try {
      log.append(String.format("playToCanvas cardIndex = %d\n", cardIdxInHand));
    } catch (IOException ignored) {

    }
  }

  @Override
  public void drawForHand() {
    // don't need for confirming input
  }

  /**
   * Java doc for stub:
   * - try:
   *  append to the log the deck, whether or not to shuffle, number of palettes,
   *  and handsize as user input to the model to check what is passed
   *  to the model as a mock.
   * - catch:
   * Intentionally ignored in mock
   */
  public void startGame(List<SoloRedCard> deck, boolean shuffle, int numPalettes, int handSize) {
    try {
      log.append(String.format("startGame called with deck size = %d,"
                      + " shuffle = %b, numPalettes = %d, handSize = %d\n",
              deck.size(), shuffle, numPalettes, handSize));
    } catch (IOException ignored) {

    }
  }

  @Override
  public int numOfCardsInDeck() {
    return 0; // don't need for confirming input, so random int
  }

  @Override
  public int numPalettes() {
    return 0; // don't need for confirming input, so random int
  }

  @Override
  public int winningPaletteIndex() {
    return 0; // don't need for confirming input, so random int
  }

  @Override
  public boolean isGameOver() {
    return false; // don't need for confirming input, so random boolean
  }

  @Override
  public boolean isGameWon() {
    return false; // don't need for confirming input, so random boolean
  }

  @Override
  public List<SoloRedCard> getHand() {
    return new ArrayList<>(); // don't need for confirming input, so empty ArrayList
  }

  @Override
  public List<SoloRedCard> getPalette(int paletteNum) {
    return new ArrayList<>(); // don't need for confirming input, so empty ArrayList
  }

  /**
   * Java doc for stub:
   *  provide the canvas so view won't complain when trying
   *      to render the mock for controller output.
   */
  public SoloRedCard getCanvas() {
    return this.canvas;
  }

  @Override
  public List<SoloRedCard> getAllCards() {
    return new ArrayList<>(); // don't need for confirming input, so empty ArrayList
  }
}
