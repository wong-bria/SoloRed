package cs3500.solored;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw02.SoloRedCard;
import cs3500.solored.model.hw02.SoloRedGameModel;


/**
 * A mock of a SoloRedGameModel to confirm that a game of SoloRedGame ends when
 * game conditions is met: empty hand and empty deck.
 */
public class MockModelEnds implements RedGameModel<SoloRedCard> {
  private final Appendable log;

  // 0-index based where 0 is the first card of the canvas and canvas.size() - 1
  // or the last card of the list represents the current canvas card
  private List<SoloRedCard> canvas;

  // a 0-index based list where 0 is the top of the deck to be drawn first
  private List<SoloRedCard> deck;

  // a 0-index based list where 0 is the left-most card of a hand
  private List<SoloRedCard> hand;

  // 0-index based list of list, where 0 for the outer list is the first palette
  // and 0 for the inner list is the first card of the palette
  private List<List<SoloRedCard>> palettes;

  private boolean gameOver;
  private boolean canvasPlayedThisTurn;
  private Random random;



  /**
   * The constructor for a mock of a SoloRedGame to confirm user input.
   *
   * @param log Represents a gameLog to keep track of what's happening in game.
   */
  public MockModelEnds(Appendable log) {
    this.log = Objects.requireNonNull(log);
    this.canvas = new ArrayList<>();
    this.canvas.add(new SoloRedGameModel().getAllCards().get(1));
    this.deck = new ArrayList<>();
    this.palettes = new ArrayList<>();
    this.hand = new ArrayList<>();
    this.gameOver = false;
    this.canvasPlayedThisTurn = false;
    this.random = new Random();
  }

  /**
   * Simplified version of playToPalette in actual model. It puts selected
   * card in selected palette and removes the card from the hand.
   * @param paletteIdx a 0-index number representing which palette to play to
   * @param cardIdxInHand a 0-index number representing the card to play from the hand
   */
  public void playToPalette(int paletteIdx, int cardIdxInHand) {
    // Play the card to the chosen palette
    SoloRedCard cardToPlay = hand.remove(cardIdxInHand);
    palettes.get(paletteIdx).add(cardToPlay);

    // Reset canvasPlayedThisTurn for the turn if needed
    if (canvasPlayedThisTurn) {
      canvasPlayedThisTurn = false;
    }
  }

  /**
   * Simplified version of playToCanvas in actual model. It puts selected
   * card to canvas and removes it from hand.
   * @param cardIdxInHand a 0-index number representing the card to play from the hand
   */
  public void playToCanvas(int cardIdxInHand) {
    SoloRedCard cardToPlay = hand.get(cardIdxInHand);
    canvas.add(cardToPlay);
    hand.remove(cardIdxInHand);
    canvasPlayedThisTurn = true;
  }

  @Override
  public void drawForHand() {
    // don't need for mock since I'm doing a short game that won't require drawing
  }

  /**
   * Simplified version of startGame in actual model. It sets the deck,
   * shuffles if needed, and initializes the palettes and the hand.
   * @param deck the cards used to set up and play the game
   * @param shuffle whether the deck should be shuffled prior to setting up the game
   * @param numPalettes number of palettes in the game
   * @param handSize the maximum number of cards allowed in the hand
   */
  public void startGame(List<SoloRedCard> deck, boolean shuffle, int numPalettes, int handSize) {
    this.deck = new ArrayList<>(deck);
    if (shuffle) {
      Collections.shuffle(this.deck, random);
    }
    palettes = new ArrayList<>();
    for (int i = 0; i < numPalettes; i++) {
      palettes.add(new ArrayList<>());
      palettes.get(i).add(this.deck.remove(0));
    }
    hand = new ArrayList<>();
    for (int i = 0; i < handSize && !deck.isEmpty(); i++) {
      hand.add(this.deck.remove(0));
    }
  }

  @Override
  public int numOfCardsInDeck() {
    return 0; // don't need for mock, so random int
  }

  @Override
  public int numPalettes() {
    return 0; // don't need for mock, so random int
  }

  @Override
  public int winningPaletteIndex() {
    return 0;
    // don't need for mock since I'm only doing small game
    // that ends when hand is empty and deck is empty, so random int
  }

  /**
   * Java doc for stub:
   * appends to the log that the game is over if it is over:
   * only having empty hand and empty deck since this is a mock
   * for a small game.
   * @return true if game is over. false is otherwise.
   */
  public boolean isGameOver() {
    if (!gameOver && (getHand().isEmpty() && deck.isEmpty())) {
      try {
        log.append("Game has ended\n");
        gameOver = true;
        return true;
      } catch (IOException ignored) {
      }
    }
    return false;
  }

  @Override
  public boolean isGameWon() {
    return false; // don't need for mock, so random boolean
  }

  @Override
  public List<SoloRedCard> getHand() {
    return new ArrayList<>(); // don't need for mock, so empty ArrayList
  }

  @Override
  public List<SoloRedCard> getPalette(int paletteNum) {
    return new ArrayList<>(); // don't need for mock, so empty ArrayList
  }

  /**
   * Java doc for stub:
   *  provide the canvas so view won't complain when trying
   *      to render the mock for controller output.
   */
  public SoloRedCard getCanvas() {
    SoloRedCard copy = this.canvas.get(this.canvas.size() - 1);
    return copy;
  }

  @Override
  public List<SoloRedCard> getAllCards() {
    return new ArrayList<>(); // don't need for mock, so empty ArrayList
  }
}
