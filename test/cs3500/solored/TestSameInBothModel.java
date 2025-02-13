package cs3500.solored;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cs3500.solored.model.hw02.SoloRedCard;
import cs3500.solored.model.hw04.AbstractSoloRedGameModel;
import cs3500.solored.view.hw02.SoloRedGameTextView;


/**
 * This is the abstract test class to store duplicate tests between
 * TestBasicSoloRedGameModel and TestAdvancedSoloRedGameModel. Tests the common
 * public methods between SoloRedGame and AdvancedSoloRedGame.
 * @param <M> The model of a RedGameModel: SoloRedGameModel for basic
 *           or AdvancedSoloRedGameModel for advanced
 */
public abstract class TestSameInBothModel<M extends AbstractSoloRedGameModel> {
  protected M game;
  protected M seededGame;
  protected List<SoloRedCard> deckWithAllCards;

  @Before
  public abstract void init();

  // Helper method to start the game with default parameters, no shuffle to simplify testing
  // since used to only test methods
  protected void startGameWithDefaults() {
    game.startGame(deckWithAllCards, false, 4, 7);
  }

  // Ensure that getAllCards returns a new list
  @Test
  public void testGetAllCardsReturnNewList() {
    Assert.assertEquals("Expected a new list of cards", deckWithAllCards, game.getAllCards());
  }

  // Ensure that modifying the returned list from
  //  getAllCards doesn't affect the game itself
  @Test
  public void testGetAllCardsModifyNoAffectGame() {
    List<SoloRedCard> deckWithAllCards1 = game.getAllCards();

    deckWithAllCards1.remove(0);
    List<SoloRedCard> deckWithAllCards2 = game.getAllCards();
    Assert.assertEquals("Game's card list should remain unchanged",
            35, deckWithAllCards2.size());
  }

  // test startGame with empty deck.
  @Test(expected = IllegalArgumentException.class)
  public void testStartGameEmptyDeck() {
    List<SoloRedCard> empty = new ArrayList<>();
    game.startGame(empty, false, 4, 7);
  }

  // test startGame if numPalettes < 2
  @Test(expected = IllegalArgumentException.class)
  public void testStartGameNumPalLessThanTwo() {
    game.startGame(deckWithAllCards, true, 1, 2);
  }

  // test startGame if handSize <= 0
  @Test(expected = IllegalArgumentException.class)
  public void testStartGameHandLessThanOne() {
    game.startGame(deckWithAllCards, true, 5, 0);
  }

  // test startGame if deck has null cards
  @Test(expected = IllegalArgumentException.class)
  public void testStartGameNullCards() {
    List<SoloRedCard> deck = new ArrayList<>();
    deck.add(null);
    deck.add(null);
    deck.add(null);
    deck.add(null);
    deck.add(null);
    game.startGame(deck, true, 3, 1);
  }

  // test numOfCardsInDeck when game not started
  @Test(expected = IllegalStateException.class)
  public void testNumOfCardsInDeckGameNotStart() {
    game.numOfCardsInDeck();
  }

  // test numOfCardsInDeck when game just started
  @Test
  public void testNumOfCardsInDeckGameJustStarted() {
    startGameWithDefaults();
    Assert.assertEquals("There should be 9 cards left",
            24, game.numOfCardsInDeck());
  }

  // test numOfCardsInDeck after drawing one card / drawForHand when haven't played to canvas
  @Test
  public void testNumOfCardsInDeckAfterOneDraw() {
    startGameWithDefaults();
    game.playToPalette(0, 0);
    game.drawForHand();
    Assert.assertEquals("Deck should have 23 cards.",
            23, game.numOfCardsInDeck());
  }

  // test drawForHand when game not started
  @Test(expected = IllegalStateException.class)
  public void testDrawForHandGameNotStart() {
    game.drawForHand();
  }

  // test starting game with null deck
  @Test(expected = IllegalArgumentException.class)
  public void testStartGameNullDeck() {
    List<SoloRedCard> nullDeck = null;
    game.startGame(nullDeck, false, 5, 7);
  }

  // test isGameOver at start of game / when not over
  @Test
  public void testIsGameOverAtStart() {
    startGameWithDefaults();
    Assert.assertEquals("test", false, game.isGameOver());
  }

  // test playToCanvas same color
  @Test
  public void testPlayToCanvasSameColor() {
    startGameWithDefaults();
    game.playToCanvas(0);
    Assert.assertEquals("Canvas is R5.", deckWithAllCards.get(4), game.getCanvas());
  }

  // test playToCanvas changes canvas color and if getCanvas returns top card of canvas
  @Test
  public void testPlayToCanvasChangeColorAndGetCanvas() {
    List<SoloRedCard> deck9 = new ArrayList<>();
    deck9.add(deckWithAllCards.remove(0)); // r1, b2, o3, i4, b1, b3
    deck9.add(deckWithAllCards.remove(14));
    deck9.add(deckWithAllCards.remove(8));
    deck9.add(deckWithAllCards.remove(21));
    deck9.add(deckWithAllCards.remove(12));
    deck9.add(deckWithAllCards.remove(12));

    List<SoloRedCard> extraDeck = game.getAllCards();

    game.startGame(deck9, false, 4, 2);

    // check canvas color before canvas changed
    Assert.assertEquals("Canvas color should be red",
            "R", game.getCanvas().toString().substring(0, 1));

    // change canvas color
    game.playToCanvas(0);

    // check canvas color after canvas changed
    Assert.assertEquals("Canvas color should be blue",
            extraDeck.remove(14), game.getCanvas());
  }

  // test getCanvas when game not started yet
  @Test(expected = IllegalStateException.class)
  public void testGetCanvasGameNotStart() {
    game.getCanvas();
  }

  // test winningPaletteIndex at start of game / Red behavior
  @Test
  public void testWinningPalIdxAtStart() {
    startGameWithDefaults();
    Assert.assertEquals("Palette4 is winning", 3, game.winningPaletteIndex());
  }

  // test winningPaletteIndex when canvas changed to orange and played a card
  @Test
  public void testWinningPalIdxOrange() {
    startGameWithDefaults();
    game.playToCanvas(6);
    game.playToPalette(0, 3);
    Assert.assertEquals("Palette1 is winning", 0, game.winningPaletteIndex());
  }

  // test winningPaletteIndex after changing rule
  @Test
  public void testWinningPalIdxAfterRuleChange() {
    startGameWithDefaults();
    game.playToCanvas(3);
    Assert.assertEquals("Palette4 is still winning",
            3, game.winningPaletteIndex());
  }

  // test startGame if deck's size is not large enough to setup the game
  @Test(expected = IllegalArgumentException.class)
  public void testStartGameDeckNotLargeEnough() {
    List<SoloRedCard> deck = new ArrayList<>();
    deck.add(deckWithAllCards.remove(0));
    deck.add(deckWithAllCards.remove(0));
    deck.add(deckWithAllCards.remove(0));

    game.startGame(deck, true, 5, 7);
  }

  // test startGame if deck has non-unique cards
  @Test(expected = IllegalArgumentException.class)
  public void testStartGameNonUniqueCards() {
    List<SoloRedCard> deck = new ArrayList<>();
    deck.add(deckWithAllCards.remove(0));
    List<SoloRedCard> singleCardDeck = game.getAllCards();
    deck.add(singleCardDeck.remove(0));
    List<SoloRedCard> singleCardDeck2 = game.getAllCards();
    deck.add(singleCardDeck2.remove(0));
    List<SoloRedCard> singleCardDeck3 = game.getAllCards();
    deck.add(singleCardDeck3.remove(0));
    game.startGame(deck, true, 3, 1);
  }

  // test playToPalette resulting in new palette winning
  @Test
  public void testPlayToPalette() {
    startGameWithDefaults();
    game.playToPalette(0, 0);
    List<SoloRedCard> palette0 = new ArrayList<>();
    palette0.add(deckWithAllCards.remove(0));
    palette0.add(deckWithAllCards.remove(3));
    Assert.assertEquals("Palette0 should have r1, r5",
            palette0, game.getPalette(0));
  }

  // test playing after doing playToCanvas
  @Test
  public void testPlayingAfterPlayToCanvasSameColor() {
    startGameWithDefaults();
    game.playToCanvas(0);
    game.drawForHand();
    game.playToPalette(0, 0);
    List<SoloRedCard> palette0 = new ArrayList<>();
    palette0.add(deckWithAllCards.remove(0));
    palette0.add(deckWithAllCards.remove(4));
    Assert.assertEquals("Palette1 is r1, r6", palette0, game.getPalette(0));
  }

  // test getHand at start of game
  @Test
  public void testGetHandAtStart() {
    startGameWithDefaults();
    List<SoloRedCard> hand = new ArrayList<>();
    hand.add(deckWithAllCards.remove(4));
    hand.add(deckWithAllCards.remove(4));
    hand.add(deckWithAllCards.remove(4));
    hand.add(deckWithAllCards.remove(4));
    hand.add(deckWithAllCards.remove(4));
    hand.add(deckWithAllCards.remove(4));
    hand.add(deckWithAllCards.remove(4));
    Assert.assertEquals("Hand at start", hand, game.getHand());
  }

  // test modifying hand from getHand does not affect hand in game
  @Test
  public void testGetHandModified() {
    startGameWithDefaults();

    List<SoloRedCard> hand = new ArrayList<>();
    hand = game.getHand();
    hand.remove(0);

    List<SoloRedCard> originalHand = new ArrayList<>();

    originalHand.add(deckWithAllCards.remove(4));
    originalHand.add(deckWithAllCards.remove(4));
    originalHand.add(deckWithAllCards.remove(4));
    originalHand.add(deckWithAllCards.remove(4));
    originalHand.add(deckWithAllCards.remove(4));
    originalHand.add(deckWithAllCards.remove(4));
    originalHand.add(deckWithAllCards.remove(4));

    Assert.assertEquals("Hand should not be modified", originalHand, game.getHand());
  }

  // test one turn game wins / if isGameWon return true when game won
  @Test
  public void oneTurnGameWins() {
    List<SoloRedCard> deck9 = new ArrayList<>();
    deck9.add(deckWithAllCards.remove(0)); // r1, r2, r3, r4
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));

    game.startGame(deck9, false, 3, 1);
    game.playToPalette(0, 0);

    game.isGameOver();
    game.isGameWon();

    Assert.assertEquals("game should be won", true, game.isGameWon());
  }

  // test blue behavior
  @Test
  public void testBlueBehavior() {
    List<SoloRedCard> deck9 = new ArrayList<>();
    deck9.add(deckWithAllCards.remove(0)); // r1, b2, o3, i4, b1, b3
    deck9.add(deckWithAllCards.remove(14));
    deck9.add(deckWithAllCards.remove(8));
    deck9.add(deckWithAllCards.remove(21));
    deck9.add(deckWithAllCards.remove(12));
    deck9.add(deckWithAllCards.remove(12));

    game.startGame(deck9, false, 4, 2);
    game.playToCanvas(0);
    Assert.assertEquals("winning palette is P4", 3, game.winningPaletteIndex());
  }

  // test orange behavior
  @Test
  public void testOrangeBehavior() {
    List<SoloRedCard> deck = new ArrayList<>();
    deck.add(deckWithAllCards.remove(28)); // v1, i2, i3, v3, o1, v5, r3, b3, i5
    deck.add(deckWithAllCards.remove(22));
    deck.add(deckWithAllCards.remove(22));
    deck.add(deckWithAllCards.remove(27));
    deck.add(deckWithAllCards.remove(7));
    deck.add(deckWithAllCards.remove(27));
    deck.add(deckWithAllCards.remove(2));
    deck.add(deckWithAllCards.remove(14));
    deck.add(deckWithAllCards.remove(20));

    game.startGame(deck, false, 4, 5);
    game.playToCanvas(0);
    game.playToPalette(1, 0);
    game.playToPalette(2, 0);
    game.playToPalette(3, 0);
    Assert.assertEquals("P3 is winning", 2, game.winningPaletteIndex());
  }

  // test getPalette when game not started
  @Test(expected = IllegalStateException.class)
  public void testGetPaletteGameNotStart() {
    game.getPalette(0);
  }

  // test getPalette when given index less than 0
  @Test(expected = IllegalArgumentException.class)
  public void testGetPaletteLessThanZero() {
    List<SoloRedCard> deck = new ArrayList<>();
    deck.add(deckWithAllCards.remove(28)); // v1, i2, i3, v3, o1, v5, r3, b3, i5
    deck.add(deckWithAllCards.remove(22));
    deck.add(deckWithAllCards.remove(22));
    deck.add(deckWithAllCards.remove(27));
    deck.add(deckWithAllCards.remove(7));
    deck.add(deckWithAllCards.remove(27));
    deck.add(deckWithAllCards.remove(2));
    deck.add(deckWithAllCards.remove(14));
    deck.add(deckWithAllCards.remove(20));

    game.startGame(deck, false, 4, 5);
    game.getPalette(-1);
  }

  // test getPalette when given index more than number of palettes
  @Test(expected = IllegalArgumentException.class)
  public void testGetPaletteMoreThanNumPalette() {
    List<SoloRedCard> deck = new ArrayList<>();
    deck.add(deckWithAllCards.remove(28)); // v1, i2, i3, v3, o1, v5, r3, b3, i5
    deck.add(deckWithAllCards.remove(22));
    deck.add(deckWithAllCards.remove(22));
    deck.add(deckWithAllCards.remove(27));
    deck.add(deckWithAllCards.remove(7));
    deck.add(deckWithAllCards.remove(27));
    deck.add(deckWithAllCards.remove(2));
    deck.add(deckWithAllCards.remove(14));
    deck.add(deckWithAllCards.remove(20));

    game.startGame(deck, false, 4, 5);
    game.getPalette(100);
  }

  // test modifying list from getPalette has no effect on game
  @Test
  public void testModifyGetPalette() {
    List<SoloRedCard> deck9 = new ArrayList<>();
    deck9.add(deckWithAllCards.remove(0)); // r1, b2, o3, i4, b1, b3
    deck9.add(deckWithAllCards.remove(14));
    deck9.add(deckWithAllCards.remove(8));
    deck9.add(deckWithAllCards.remove(21));
    deck9.add(deckWithAllCards.remove(12));
    deck9.add(deckWithAllCards.remove(12));

    game.startGame(deck9, false, 4, 2);
    game.getPalette(0).remove(0);

    List<SoloRedCard> copyOfDeck = game.getAllCards();
    List<SoloRedCard> copyPalette = new ArrayList<>();
    copyPalette.add(copyOfDeck.remove(0));

    Assert.assertEquals("Modifying getPalette list no effect",
            copyPalette, game.getPalette(0));
  }

  // test getHand when game not started
  @Test(expected = IllegalStateException.class)
  public void testGetHandGameNotStart() {
    game.getHand();
  }

  // test isGameWon when game not won
  @Test(expected = IllegalStateException.class)
  public void testIsGameWonWhenNotWon() {
    List<SoloRedCard> deck9 = new ArrayList<>();
    deck9.add(deckWithAllCards.remove(0)); // r1, b2, o3, i4, b1, b3
    deck9.add(deckWithAllCards.remove(14));
    deck9.add(deckWithAllCards.remove(8));
    deck9.add(deckWithAllCards.remove(21));
    deck9.add(deckWithAllCards.remove(12));
    deck9.add(deckWithAllCards.remove(12));

    game.startGame(deck9, false, 4, 2);

    Assert.assertEquals("Game has not been won yet",
            false, game.isGameWon());
  }

  // test isGameWon when game not started
  @Test(expected = IllegalStateException.class)
  public void testIsGameWonGameNotStart() {
    game.isGameWon();
  }

  // testIsGameOverWhenWon
  @Test
  public void testIsGameOverWhenWon() {
    List<SoloRedCard> deck9 = new ArrayList<>();
    deck9.add(deckWithAllCards.remove(0)); // r1, r2, r3, r4
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));

    game.startGame(deck9, false, 3, 1);
    game.playToPalette(0, 0);

    Assert.assertEquals("game should be won", true, game.isGameOver());
  }

  // test isGameOver when game not started
  @Test(expected = IllegalStateException.class)
  public void testIsGameOverGameNotStart() {
    game.isGameOver();
  }

  // test winningPaletteIndex when game not started
  @Test(expected = IllegalStateException.class)
  public void testWinningPaletteIndexGameNotStart() {
    game.winningPaletteIndex();
  }

  // test numPalette to find number of palettes
  @Test
  public void testNumPalette() {
    List<SoloRedCard> deck9 = new ArrayList<>();
    deck9.add(deckWithAllCards.remove(0)); // r1, r2, r3, r4
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));

    game.startGame(deck9, false, 3, 1);

    Assert.assertEquals("Should be 3 palettes", 3, game.numPalettes());
  }

  // test numPalette when game not started
  @Test(expected = IllegalStateException.class)
  public void testNumPaletteGameNotStart() {
    game.numPalettes();
  }

  // test modifying deck given to startGame no effect
  @Test
  public void testModifyDeckStartGame() {
    List<SoloRedCard> deck9 = new ArrayList<>();
    deck9.add(deckWithAllCards.remove(0)); // r1, r2, r3, r4
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));

    game.startGame(deck9, false, 3, 1);

    // check if deck has one card
    Assert.assertEquals("Deck should have 1 card",
            1, game.numOfCardsInDeck());

    // remove a card from deck
    deck9.remove(0);

    // check if deck still have one card
    Assert.assertEquals("Deck should have 1 card",
            1, game.numOfCardsInDeck());
  }

  // test startGame when game is over
  @Test(expected = IllegalStateException.class)
  public void testStartGameWhenOver() {
    List<SoloRedCard> deck9 = new ArrayList<>();
    deck9.add(deckWithAllCards.remove(0)); // r1, r2, r3, r4
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));

    game.startGame(deck9, false, 3, 1);
    game.playToPalette(0, 0);

    game.isGameOver();

    game.startGame(deck9, false, 3, 1);
  }

  // test startGame when game has started
  @Test(expected = IllegalStateException.class)
  public void testStartGameWhenStarted() {
    List<SoloRedCard> deck9 = new ArrayList<>();
    deck9.add(deckWithAllCards.remove(0)); // r1, r2, r3, r4
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));

    game.startGame(deck9, false, 3, 1);

    game.startGame(deck9, false, 3, 1);
  }

  // test drawForHand when game is over
  @Test(expected = IllegalStateException.class)
  public void testDrawForHandGameOver() {
    List<SoloRedCard> deck9 = new ArrayList<>();
    deck9.add(deckWithAllCards.remove(0)); // r1, r2, r3, r4
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));

    game.startGame(deck9, false, 3, 1);
    game.playToPalette(0, 0);

    game.isGameOver();

    game.drawForHand();
  }

  // test drawForHand allowing player to play to canvas again
  @Test
  public void testDrawForHandAllowPlayCanvasAgain() {
    startGameWithDefaults();
    // check the canvas at start
    Assert.assertEquals("Canvas is red",
            "R", game.getCanvas().toString().substring(0, 1));


    game.playToCanvas(0);

    // check canvas after changing once
    Assert.assertEquals("Canvas is now still red, specifically R6",
            deckWithAllCards.remove(4), game.getCanvas());

    game.playToPalette(0, 0);
    game.drawForHand();
    game.playToCanvas(0);

    // check canvas again after changing second time
    Assert.assertEquals("Canvas is now still red, specifically R7",
            deckWithAllCards.remove(5), game.getCanvas());
  }


  // test drawForHand when deck is empty
  @Test
  public void testDrawForHandDeckEmpty() {
    List<SoloRedCard> deck9 = new ArrayList<>();
    deck9.add(deckWithAllCards.remove(0)); // r1, r2, r3, r4
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));

    game.startGame(deck9, false, 3, 1);
    game.drawForHand();

    List<SoloRedCard> copyDeck = game.getAllCards();
    List<SoloRedCard> copyHand = new ArrayList<>();
    copyHand.add(copyDeck.remove(3));

    Assert.assertEquals("hand is still only R4", copyHand, game.getHand());
  }

  // test playToCanvas when game not started
  @Test(expected = IllegalStateException.class)
  public void testPlayToCanvasGameNotStart() {
    game.playToCanvas(0);
  }

  // test playToCanvas when game is over
  @Test(expected = IllegalStateException.class)
  public void testPlayToCanvasGameOver() {
    List<SoloRedCard> deck9 = new ArrayList<>();
    deck9.add(deckWithAllCards.remove(4)); // r5, r1, r2, r3, r4, r6
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));

    game.startGame(deck9, false, 3, 1);
    game.playToPalette(0, 0);

    game.isGameOver();

    game.playToCanvas(0);
  }

  // test playToCanvas when given card index less than zero
  @Test(expected = IllegalArgumentException.class)
  public void testPlayToCanvasGivenIndexLessThanZero() {
    List<SoloRedCard> deck9 = new ArrayList<>();
    deck9.add(deckWithAllCards.remove(4)); // r5, r1, r2, r3, r4, r6
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));

    game.startGame(deck9, false, 3, 1);

    game.playToCanvas(-1);
  }

  // test playToCanvas when given card index greater than number of cards in hand
  @Test(expected = IllegalArgumentException.class)
  public void testPlayToCanvasGivenIndexGreaterThanHandSize() {
    List<SoloRedCard> deck9 = new ArrayList<>();
    deck9.add(deckWithAllCards.remove(4)); // r5, r1, r2, r3, r4, r6
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));

    game.startGame(deck9, false, 3, 1);

    game.playToCanvas(100);
  }

  // test playToCanvas twice in one turn
  @Test(expected = IllegalStateException.class)
  public void testPlayToCanvasTwiceInOneTurn() {
    startGameWithDefaults();
    game.playToCanvas(0);
    game.playToCanvas(0);
  }

  // test playToCanvas when only one card in hand
  @Test(expected = IllegalStateException.class)
  public void testPlayToCanvasOneCardInHand() {
    List<SoloRedCard> deck9 = new ArrayList<>();
    deck9.add(deckWithAllCards.remove(0)); // r1, r2, r3, r4
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));

    game.startGame(deck9, false, 3, 1);

    game.playToCanvas(0);
  }

  // test playToPalette puts card at end of palette
  @Test
  public void testPlayToPalettePutsCardAtEndOfPalette() {
    List<SoloRedCard> deck9 = new ArrayList<>();
    deck9.add(deckWithAllCards.remove(0)); // r1, r2, r3, r4
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));

    game.startGame(deck9, false, 3, 1);

    game.playToPalette(0, 0);

    List<SoloRedCard> copyDeck = game.getAllCards();

    Assert.assertEquals("Last card of palette1 should be R4",
            copyDeck.remove(3), game.getPalette(0).get(game.getPalette(0).size() - 1));
  }

  // test playToPalette when game not started
  @Test(expected = IllegalStateException.class)
  public void testPlayToPaletteGameNotStart() {
    game.playToPalette(0, 0);
  }

  // test playToPalette when game is over
  @Test(expected = IllegalStateException.class)
  public void testGetCanvasGameOver() {
    List<SoloRedCard> deck9 = new ArrayList<>();
    deck9.add(deckWithAllCards.remove(4)); // r5, r1, r2, r3, r4, r6
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));

    game.startGame(deck9, false, 3, 1);
    game.playToPalette(1, 0);

    game.isGameOver();

    game.playToPalette(0, 0);
  }

  // test playToPalette when given palette index less than zero
  @Test(expected = IllegalArgumentException.class)
  public void testPlayToPaletteGivenPalIndexLessThanZero() {
    List<SoloRedCard> deck9 = new ArrayList<>();
    deck9.add(deckWithAllCards.remove(4)); // r5, r1, r2, r3, r4, r6
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));

    game.startGame(deck9, false, 3, 1);
    game.playToPalette(-1, 0);
  }

  // test playToPalette when given palette index more than number of palettes
  @Test(expected = IllegalArgumentException.class)
  public void testPlayToPaletteGivenPalIndeMoreThanNumPal() {
    List<SoloRedCard> deck9 = new ArrayList<>();
    deck9.add(deckWithAllCards.remove(4)); // r5, r1, r2, r3, r4, r6
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));

    game.startGame(deck9, false, 3, 1);
    game.playToPalette(100, 0);
  }

  // test playToPalette when given card index less than zero
  @Test(expected = IllegalArgumentException.class)
  public void testPlayToPaletteGivenCardIndexLessThanZero() {
    List<SoloRedCard> deck9 = new ArrayList<>();
    deck9.add(deckWithAllCards.remove(4)); // r5, r1, r2, r3, r4, r6
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));

    game.startGame(deck9, false, 3, 1);
    game.playToPalette(0, -1);
  }

  // test playToPalette when given card index more than size of hand
  @Test(expected = IllegalArgumentException.class)
  public void testPlayToPaletteGivenCardIndeMoreThanHandSize() {
    List<SoloRedCard> deck9 = new ArrayList<>();
    deck9.add(deckWithAllCards.remove(4)); // r5, r1, r2, r3, r4, r6
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));

    game.startGame(deck9, false, 3, 1);
    game.playToPalette(0, 100);
  }

  // test playToPalette when given palette index is winning palette
  @Test(expected = IllegalStateException.class)
  public void testPlayToPaletteGivenIndexIsWinningPalette() {
    List<SoloRedCard> deck9 = new ArrayList<>();
    deck9.add(deckWithAllCards.remove(4)); // r5, r1, r2, r3, r4, r6
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));

    game.startGame(deck9, false, 3, 1);
    game.playToPalette(0, 0);
  }

  // test toString to view the game
  @Test
  public void testToStringToViewGame() {
    List<SoloRedCard> deck9 = new ArrayList<>();
    deck9.add(deckWithAllCards.remove(0)); // r1, r2, r3, r4
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));

    game.startGame(deck9, false, 3, 1);

    SoloRedGameTextView viewGame2 = new SoloRedGameTextView(game);

    Assert.assertEquals("Canvas: R\n"
            + "P1: R1\n"
            + "P2: R2\n"
            + "> P3: R3\n"
            + "Hand: R4", viewGame2.toString());
  }

  // test that startGame shuffles deck when user wants to shuffle
  // also test that SoloRedGameModel/AdvancedSoloRedGameModel with Random object as argument
  // can randomize deck when user wants to shuffle
  @Test
  public void testShuffleStartGame() {
    List<SoloRedCard> deck9 = new ArrayList<>();
    deck9.add(deckWithAllCards.remove(0)); // r1, r2, r3, r4
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));

    List<SoloRedCard> copyDeck = game.getAllCards();
    List<SoloRedCard> copyPal = new ArrayList<>();
    copyPal.add(copyDeck.get(3));

    seededGame.startGame(deck9, true, 3, 1);
    Assert.assertEquals("Order of cards should be random since want shuffle:"
                    + "palette 1 has r4, not r2, so shuffle worked",
            copyPal, seededGame.getPalette(1));
  }

  // test that startGame won't shuffle deck when user doesn't wants to shuffle
  // also test that SoloRedGameModel/AdvancedSoloRedGameModel constructor with no arguments
  // allows user to initialize game to startGame later on
  @Test
  public void testNoShuffleStartGame() {
    List<SoloRedCard> deck9 = new ArrayList<>();
    deck9.add(deckWithAllCards.remove(0)); // r1, r2, r3, r4
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));

    List<SoloRedCard> copyDeck = game.getAllCards();
    List<SoloRedCard> copyPal = new ArrayList<>();
    copyPal.add(copyDeck.get(1));

    game.startGame(deck9, false, 3, 1);
    Assert.assertEquals("Order of cards should be same as given deck order:"
                    + "palette 1 has r2",
            copyPal, game.getPalette(1));
  }

  // test Indigo behavior
  @Test
  public void testIndigoBehavior() {
    List<SoloRedCard> deck = new ArrayList<>();
    deck.add(deckWithAllCards.remove(28)); // v1, i2, i3, v3
    // Hand: o1, v5, r3, b3, i5 --> i5 to canvas
    deck.add(deckWithAllCards.remove(22));
    deck.add(deckWithAllCards.remove(22));
    deck.add(deckWithAllCards.remove(27));
    deck.add(deckWithAllCards.remove(7));
    deck.add(deckWithAllCards.remove(27));
    deck.add(deckWithAllCards.remove(2));
    deck.add(deckWithAllCards.remove(14));
    deck.add(deckWithAllCards.remove(20));

    game.startGame(deck, false, 4, 5);
    game.playToCanvas(4);

    Assert.assertEquals("P3 is winning", 2, game.winningPaletteIndex());
  }

  // test violet behavior
  @Test
  public void testVioletBehavior() {
    List<SoloRedCard> deck = new ArrayList<>();
    deck.add(deckWithAllCards.remove(28)); // v1, i2, i3, v3,
    // Hand: o1, v5, r3, b3, i5 --> v5 to canvas
    deck.add(deckWithAllCards.remove(22));
    deck.add(deckWithAllCards.remove(22));
    deck.add(deckWithAllCards.remove(27));
    deck.add(deckWithAllCards.remove(7));
    deck.add(deckWithAllCards.remove(27));
    deck.add(deckWithAllCards.remove(2));
    deck.add(deckWithAllCards.remove(14));
    deck.add(deckWithAllCards.remove(20));

    game.startGame(deck, false, 4, 5);
    game.playToCanvas(1);

    Assert.assertEquals("P3 is winning", 2, game.winningPaletteIndex());
  }

  // test the SoloRedGameTextView constructor when model is null
  @Test (expected = IllegalArgumentException.class)
  public void testSoloRedGameTextViewNullModel() {
    Appendable ap = new StringBuilder();
    SoloRedGameTextView view = new SoloRedGameTextView(null, ap);
  }

  // test the SoloRedGameTextView constructor when appendable is null
  @Test (expected = IllegalArgumentException.class)
  public void testSoloRedGameTextViewNullAppendable() {
    SoloRedGameTextView view = new SoloRedGameTextView(this.game, null);
  }

  // test that render() correctly assembles the game state as a string
  @Test
  public void testRenderWithRealModel() throws IOException {
    List<SoloRedCard> deck9 = new ArrayList<>();
    deck9.add(deckWithAllCards.remove(0)); // r1, r2, r3, r4
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));

    game.startGame(deck9, false, 3, 1);

    Appendable log = new StringBuilder();
    SoloRedGameTextView view = new SoloRedGameTextView(game, log);

    // Render the game state
    view.render();

    String expectedOutput = "Canvas: R\n"
            + "P1: R1\n"
            + "P2: R2\n"
            + "> P3: R3\n"
            + "Hand: R4";

    Assert.assertEquals(expectedOutput, log.toString());
  }

  // test that render throws an exception when appendable is null
  @Test (expected = IllegalArgumentException.class)
  public void testRenderThrowExceptionNullAppendable() {
    List<SoloRedCard> deck9 = new ArrayList<>();
    deck9.add(deckWithAllCards.remove(0)); // r1, r2, r3, r4
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));
    deck9.add(deckWithAllCards.remove(0));

    game.startGame(deck9, false, 3, 1);

    SoloRedGameTextView view = new SoloRedGameTextView(game, null);
  }

}
