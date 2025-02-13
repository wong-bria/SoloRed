package cs3500.solored.model.hw04;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import cs3500.solored.model.hw02.Color;
import cs3500.solored.model.hw02.Number;
import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw02.SoloRedCard;

/**
 * Abstract class containing duplicate code between SoloRedGameModel and
 * AdvancedSoloRedGameModel. Overall, this class represents the state
 * and behaviors that are shared between SoloRedGameModel and
 * AdvancedSoloRedGameModel.
 */
public abstract class AbstractSoloRedGameModel implements RedGameModel<SoloRedCard> {
  // a 0-index based list where 0 is the top of the deck to be drawn first
  protected List<SoloRedCard> deck;

  // a 0-index based list where 0 is the left-most card of a hand
  protected List<SoloRedCard> hand;

  // The number of cards in a hand: 0 means 0 cards in hand
  protected int maxHandSize;

  // 0-index based list of list, where 0 for the outer list is the first palette
  // and 0 for the inner list is the first card of the palette
  protected List<List<SoloRedCard>> palettes;

  // 0-index based where 0 represents the first palette/palette1
  protected int palettePicked;

  // 0-index based where 0 is the first card of the canvas and canvas.size() - 1
  // or the last card of the list represents the current canvas card
  protected List<SoloRedCard> canvas;

  // 0-index based where 0 is the first card of all possible card that a
  // user can play with
  protected List<SoloRedCard> allCards;
  protected Random random;
  protected boolean gameStarted;
  protected boolean gameOver;
  protected boolean canvasPlayedThisTurn;

  /**
   * Zero-argument constructor. Initializes the game but does not start it.
   */
  public AbstractSoloRedGameModel() {
    this.deck = new ArrayList<>();
    this.hand = new ArrayList<>();
    this.palettes = new ArrayList<>();
    this.palettePicked = -1;
    this.canvas = new ArrayList<>();
    this.canvas.add(new SoloRedCard(Color.Red));
    this.allCards = new ArrayList<>();
    this.random = new Random();
    this.gameStarted = false;
    this.gameOver = false;
    this.canvasPlayedThisTurn = false;
  }

  /**
   * Constructor with a Random object for shuffling.
   *
   * @param random the Random object to use for shuffling
   * @throws IllegalArgumentException if the Random object is null
   */
  public AbstractSoloRedGameModel(Random random) {
    if (random == null) {
      throw new IllegalArgumentException("Random object cannot be null");
    }
    this.deck = new ArrayList<>();
    this.hand = new ArrayList<>();
    this.palettes = new ArrayList<>();
    this.palettePicked = -1;
    this.canvas = new ArrayList<>();
    this.canvas.add(new SoloRedCard(Color.Red));
    this.allCards = new ArrayList<>();
    this.random = random;
    this.gameStarted = false;
    this.gameOver = false;
    this.canvasPlayedThisTurn = false;
  }

  /**
   * Play the given card from the hand to the losing palette chosen.
   * The card is removed from the hand and placed at the far right
   * end of the palette.
   *
   * @param paletteIdx    a 0-index number representing which palette to play to
   * @param cardIdxInHand a 0-index number representing the card to play from the hand
   * @throws IllegalStateException    if the game has not started or the game is over
   * @throws IllegalArgumentException if paletteIdx < 0 or more than the number of palettes
   * @throws IllegalArgumentException if cardIdxInHand < 0
   *                                  or greater/equal to the number of cards in hand
   * @throws IllegalStateException    if the palette referred to by paletteIdx is winning
   */
  public void playToPalette(int paletteIdx, int cardIdxInHand) {
    if (!gameStarted || gameOver) {
      throw new IllegalStateException("Game has not started or is over!");
    }
    if (paletteIdx < 0 || paletteIdx >= numPalettes()) {
      throw new IllegalArgumentException("Palette index is greater than 0 but less "
              + "than number of palettes!"); // might be wrong????
    }
    if (cardIdxInHand < 0 || cardIdxInHand >= hand.size()) {
      throw new IllegalArgumentException("Card index in hand must be greater than 0 "
              + "and less than number of cards in hand!");
    }
    if (paletteIdx == winningPaletteIndex()) {
      throw new IllegalStateException("Palette referred to must be losing!");
    }
    palettePicked = paletteIdx;

    // Play the card to the chosen palette
    SoloRedCard cardToPlay = hand.remove(cardIdxInHand);
    palettes.get(paletteIdx).add(cardToPlay);

    // Reset canvasPlayedThisTurn for the turn if needed
    if (canvasPlayedThisTurn) {
      canvasPlayedThisTurn = false;
    }
  }

  /**
   * Starts the game with the given options. The deck given is used
   * to set up the palettes and hand. Modifying the deck given to this method
   * will not modify the game state in any way.
   *
   * @param deck        the cards used to set up and play the game
   * @param shuffle     whether the deck should be shuffled prior to setting up the game
   * @param numPalettes number of palettes in the game
   * @param handSize    the maximum number of cards allowed in the hand
   * @throws IllegalStateException    if the game has started or the game is over
   * @throws IllegalArgumentException if numPalettes < 2 or handSize <= 0
   * @throws IllegalArgumentException if deck's size is not large enough to setup the game
   * @throws IllegalArgumentException if deck has non-unique cards or null cards
   */
  public void startGame(List<SoloRedCard> deck, boolean shuffle, int numPalettes, int handSize) {
    if (gameStarted || gameOver) {
      throw new IllegalStateException("Game has already started or is over!");
    }
    if (numPalettes < 2 || handSize <= 0) {
      throw new IllegalArgumentException("Number of palettes must be 2 or more "
              + "and hand size must be greater than zero!");
    }
    if (deck == null || deck.size() < numPalettes + handSize) {
      throw new IllegalArgumentException("Deck's size is not large enough to setup "
              + "the game!");
    }
    this.deck = new ArrayList<>(deck);

    // Created a set since sets must have unique elements, thus
    // preventing non-unique cards.
    Set<SoloRedCard> cardSet = new HashSet<>();
    for (SoloRedCard card : deck) {
      if (card == null) {  // Check for null cards
        throw new IllegalArgumentException("A deck can't contain null cards!");
      }
      if (!cardSet.add(card)) {
        throw new IllegalArgumentException("A deck must contain unique cards!");
      }
    }

    if (shuffle) {
      Collections.shuffle(this.deck, random);
    }
    this.maxHandSize = handSize;
    this.allCards = getAllCards();
    // Initialize the palettes and pass a single card to each palette
    palettes = new ArrayList<>();
    for (int i = 0; i < numPalettes; i++) {
      palettes.add(new ArrayList<>());
      palettes.get(i).add(this.deck.remove(0));
    }

    // Pass cards to the hand until it is full
    hand = new ArrayList<>();
    for (int i = 0; i < handSize && !deck.isEmpty(); i++) {
      hand.add(this.deck.remove(0));
    }

    this.gameStarted = true;
  }

  /**
   * Returns the number of cards remaining in the deck used in the game.
   *
   * @return the number of cards in the deck
   * @throws IllegalStateException if the game has not started
   */
  public int numOfCardsInDeck() {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started");
    }
    return deck.size();
  }

  /**
   * Returns the number of palettes in the running game.
   *
   * @return the number of palettes in the game
   * @throws IllegalStateException if the game has not started
   */
  public int numPalettes() {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started");
    }
    return palettes.size();
  }

  /**
   * Returns the index of the winning palette in the game.
   *
   * @return the 0-based index of the winning palette
   * @throws IllegalStateException if the game has not started
   */
  public int winningPaletteIndex() {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started");
    }
    int winningPaletteIdx = -1;
    Color canvasColor = getCanvas().getColor();
    switch (canvasColor) {
      case Red:
        winningPaletteIdx = WinningPaletteEvaluator.findWinningPaletteByHighestCard(this);
        break;
      case Orange:
        winningPaletteIdx = WinningPaletteEvaluator.findWinningPaletteByMostCommonNumber(this);
        break;
      case Blue:
        winningPaletteIdx = WinningPaletteEvaluator.findWinningPaletteByMostDifferentColors(this);
        break;
      case Indigo:
        winningPaletteIdx = WinningPaletteEvaluator.findWinningPaletteByLongestRun(this);
        break;
      case Violet:
        winningPaletteIdx = WinningPaletteEvaluator.findWinningPaletteByMostCardsBelowFour(this);
        break;
      default: // invalid canvas color
        break;
    }
    return winningPaletteIdx;
  }

  /**
   * Returns if the game is over as specified by the implementation.
   * The player played to a losing palette, but that palette did not win or
   * if the player has no cards in hand and the deck is empty.
   *
   * @return true if the game has ended and false otherwise
   * @throws IllegalStateException if the game has not started
   */
  public boolean isGameOver() {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started");
    }
    int winningPalette = winningPaletteIndex();

    if ((((palettePicked != winningPalette) && !canvasPlayedThisTurn)
            || (getHand().isEmpty() && deck.isEmpty()))
            && palettePicked > -1) {
      gameOver = true;
      return true;
    }
    return false;
  }

  /**
   * Returns if the game is won by the player as specified by the implementation.
   * Player won if the player has no cards in hand and the deck is empty,
   * and the player played card to winning palette.
   *
   * @return true if the game has been won or false if the game has not
   * @throws IllegalStateException if the game has not started or the game is not over
   */
  public boolean isGameWon() {
    if (!gameStarted || !gameOver) {
      throw new IllegalStateException("Game has not started or is not over");
    }
    return getHand().isEmpty() && deck.isEmpty() && palettePicked == winningPaletteIndex();
  }

  /**
   * Returns a copy of the hand in the game. This means modifying the returned list
   * or the cards in the list has no effect on the game.
   *
   * @return a new list containing the cards in the player's hand in the same order
   *         as in the current state of the game.
   * @throws IllegalStateException if the game has not started
   */
  public List<SoloRedCard> getHand() {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started!");
    }
    List<SoloRedCard> copyOfHand = new ArrayList<>();
    for (SoloRedCard card : hand) {
      copyOfHand.add(card);
    }
    return copyOfHand;
  }

  /**
   * Returns a copy of the specified palette. This means modifying the returned list
   * or the cards in the list has no effect on the game.
   *
   * @param paletteNum 0-based index of a particular palette
   * @return a new list containing the cards in specified palette in the same order
   *         as in the current state of the game.
   * @throws IllegalStateException    if the game has not started
   * @throws IllegalArgumentException if paletteIdx < 0 or more than the number of palettes
   */
  public List<SoloRedCard> getPalette(int paletteNum) {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started");
    }
    if (paletteNum < 0 || paletteNum > numPalettes()) {
      throw new IllegalArgumentException("Palette Index should be greater than zero"
              + "but less than the total number of palettes");
    }
    List<SoloRedCard> copyOfPalette = new ArrayList<>();
    for (SoloRedCard card : palettes.get(paletteNum)) {
      copyOfPalette.add(card);
    }
    return copyOfPalette;
  }

  /**
   * Return the top card of the canvas.
   * Modifying this card has no effect on the game.
   *
   * @return the top card of the canvas
   * @throws IllegalStateException if the game has not started
   */
  public SoloRedCard getCanvas() {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started!");
    }

    SoloRedCard copy = new SoloRedCard(canvas.get(canvas.size() - 1).getColor(),
            canvas.get(canvas.size() - 1).getNumberValue());
    return copy;
  }

  /**
   * Get a NEW list of all cards that can be used to play the game.
   * Editing this list should have no effect on the game itself.
   * Repeated calls to this method should produce a list of cards in the same order.
   * Modifying the cards in this list should have no effect on any returned list
   * or the game itself.
   *
   * @return a new list of all possible cards that can be used for the game
   */
  public List<SoloRedCard> getAllCards() {
    List<SoloRedCard> allCards = new ArrayList<>();
    allCards.add(new SoloRedCard(Color.Red, Number.One));
    allCards.add(new SoloRedCard(Color.Red, Number.Two));
    allCards.add(new SoloRedCard(Color.Red, Number.Three));
    allCards.add(new SoloRedCard(Color.Red, Number.Four));
    allCards.add(new SoloRedCard(Color.Red, Number.Five));
    allCards.add(new SoloRedCard(Color.Red, Number.Six));
    allCards.add(new SoloRedCard(Color.Red, Number.Seven));
    allCards.add(new SoloRedCard(Color.Orange, Number.One));
    allCards.add(new SoloRedCard(Color.Orange, Number.Two));
    allCards.add(new SoloRedCard(Color.Orange, Number.Three));
    allCards.add(new SoloRedCard(Color.Orange, Number.Four));
    allCards.add(new SoloRedCard(Color.Orange, Number.Five));
    allCards.add(new SoloRedCard(Color.Orange, Number.Six));
    allCards.add(new SoloRedCard(Color.Orange, Number.Seven));
    allCards.add(new SoloRedCard(Color.Blue, Number.One));
    allCards.add(new SoloRedCard(Color.Blue, Number.Two));
    allCards.add(new SoloRedCard(Color.Blue, Number.Three));
    allCards.add(new SoloRedCard(Color.Blue, Number.Four));
    allCards.add(new SoloRedCard(Color.Blue, Number.Five));
    allCards.add(new SoloRedCard(Color.Blue, Number.Six));
    allCards.add(new SoloRedCard(Color.Blue, Number.Seven));

    allCards.add(new SoloRedCard(Color.Indigo, Number.One));
    allCards.add(new SoloRedCard(Color.Indigo, Number.Two));
    allCards.add(new SoloRedCard(Color.Indigo, Number.Three));
    allCards.add(new SoloRedCard(Color.Indigo, Number.Four));
    allCards.add(new SoloRedCard(Color.Indigo, Number.Five));
    allCards.add(new SoloRedCard(Color.Indigo, Number.Six));
    allCards.add(new SoloRedCard(Color.Indigo, Number.Seven));
    allCards.add(new SoloRedCard(Color.Violet, Number.One));
    allCards.add(new SoloRedCard(Color.Violet, Number.Two));
    allCards.add(new SoloRedCard(Color.Violet, Number.Three));
    allCards.add(new SoloRedCard(Color.Violet, Number.Four));
    allCards.add(new SoloRedCard(Color.Violet, Number.Five));
    allCards.add(new SoloRedCard(Color.Violet, Number.Six));
    allCards.add(new SoloRedCard(Color.Violet, Number.Seven));
    return new ArrayList<>(allCards);
  }

  /**
   * Play the given card from the hand to the canvas.
   * This changes the rules of the game for all palettes.
   * The method can only be called once per turn.
   * @param cardIdxInHand a 0-index number representing the card to play from the hand
   */
  public abstract void playToCanvas(int cardIdxInHand);

  /**
   * Draws cards from the deck, or until the deck is empty, whichever occurs first.
   * Newly drawn cards are added to the end of the hand (far-right conventionally).
   * SIDE-EFFECT: Allows the player to play to the canvas again.
   */
  public abstract void drawForHand();
}
