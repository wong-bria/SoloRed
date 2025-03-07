package cs3500.solored.model.hw02;


import java.util.Random;
import cs3500.solored.model.hw04.AbstractSoloRedGameModel;

/**
 * Represents the state and behaviors of a SoloRedGame basic version. The basic
 * version is the same as the advanced version, where the only difference
 * is that in this version, after playing to a palette that is losing, the
 * user is able to draw until the specified max hand size or until the deck is
 * empty.
 */
public class SoloRedGameModel extends AbstractSoloRedGameModel {

  /**
   * Zero-argument constructor. Initializes the game but does not start it.
   */
  public SoloRedGameModel() {
    super();
  }

  /**
   * Constructor with a Random object for shuffling.
   *
   * @param random the Random object to use for shuffling
   * @throws IllegalArgumentException if the Random object is null
   */
  public SoloRedGameModel(Random random) {
    super(random);
  }

  /**
   * Play the given card from the hand to the canvas.
   * This changes the rules of the game for all palettes.
   * The method can only be called once per turn.
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
  }

  /**
   * Draws cards from the deck until the hand is full
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

    // Draw cards until the hand is full or deck is empty
    while (hand.size() < maxHandSize && !deck.isEmpty()) {
      SoloRedCard card = deck.remove(0);
      hand.add(card);
    }

    // Allow player to play to the canvas again
    canvasPlayedThisTurn = false;
  }
}
