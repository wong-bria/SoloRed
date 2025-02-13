package cs3500.solored.model.hw04;


import java.util.Random;
import cs3500.solored.model.hw02.SoloRedCard;

/**
 * Represents the state and behaviors of a SoloRedGame advanced version. The advanced
 * version is the same as the basic version, where the only difference
 * is that in this version, after playing to a palette that is losing, the
 * user is able to draw one or two card depending on if they met a certain condition
 * and if the deck is not empty.
 * Condition to draw two cards:
 * if user plays card to canvas and the number is greater than the number of cards
 * in the current winning palette, then the user gets to draw two cards the next time
 * after they play to palette. Otherwise, user draws one card. After drawing two cards,
 * the user must play to canvas and meet same condition to draw two cards again.
 */
public class AdvancedSoloRedGameModel extends AbstractSoloRedGameModel {
  // true if canvas.number() > palettes.get(winningPaletteIndex()).size
  // false otherwise, and after you do drawForHand
  private boolean drawTwoCards;


  /**
   * Zero-argument constructor. Initializes the game but does not start it.
   */
  public AdvancedSoloRedGameModel() {
    super();
    this.drawTwoCards = false; // boolean flag to determine if user gets to draw 2 cards
  }

  /**
   * Constructor with a Random object for shuffling.
   *
   * @param random the Random object to use for shuffling
   * @throws IllegalArgumentException if the Random object is null
   */
  public AdvancedSoloRedGameModel(Random random) {
    super(random);
    this.drawTwoCards = false;
  }



  /**
   * Play the given card from the hand to the canvas.
   * This changes the rules of the game for all palettes.
   * The method can only be called once per turn.
   * Allows user to draw two card if the number on the card
   * is greater than the number of cards in the winning palette.
   *
   * @param cardIdxInHand a 0-index number representing the card to play from the hand
   * @throws IllegalStateException    if the game has not started or the game is over
   * @throws IllegalArgumentException if cardIdxInHand < 0
   *                                  or greater/equal to the number of cards in hand
   * @throws IllegalStateException    if this method was already called once in a given turn
   * @throws IllegalStateException    if there is exactly one card in hand
   */
  public void playToCanvas(int cardIdxInHand) {
    if (!gameStarted || gameOver) {
      throw new IllegalStateException("Game has not started or is over!");
    }
    if (cardIdxInHand < 0 || cardIdxInHand >= getHand().size()) {
      throw new IllegalArgumentException("Card index in hand must be greater than zero"
              + " and less than the total number of cards in hand!");
    }
    if (canvasPlayedThisTurn) {
      throw new IllegalStateException("This method was already called once this turn!");
    }
    if (getHand().size() == 1) {
      throw new IllegalStateException("Can't play to canvas when exactly one card in hand!");
    }

    SoloRedCard cardToPlay = hand.get(cardIdxInHand);
    canvas.add(cardToPlay);
    hand.remove(cardIdxInHand);
    canvasPlayedThisTurn = true;

    if (cardToPlay.getNumber() > palettes.get(winningPaletteIndex()).size()) {
      drawTwoCards = true;
    }
  }

  /**
   * Draw two cards if user played to canvas and the number is greater
   * than the number of cards in the winning palette, otherwise draw one card
   * OR until the deck is empty, whichever occurs first. Newly drawn cards
   * are added to the end of the hand (far-right conventionally).
   * SIDE-EFFECT: Allows the player to play to the canvas again.
   *
   * @throws IllegalStateException if the game has not started or the game is over
   */
  public void drawForHand() {
    if (!gameStarted || gameOver) {
      throw new IllegalStateException("Game has not started or is over!");
    }

    int cardsToDraw;
    if (drawTwoCards) {
      cardsToDraw = 2;
    } else {
      cardsToDraw = 1;
    }

    for (int i = 0; (i < cardsToDraw) && (hand.size() < maxHandSize) && !deck.isEmpty(); i++) {
      SoloRedCard card = deck.remove(0);
      hand.add(card);
    }

    // reset back to drawing to one card after drawing
    drawTwoCards = false;

    // Allow player to play to the canvas again
    canvasPlayedThisTurn = false;
  }
}
