package cs3500.solored.view.hw02;

import cs3500.solored.model.hw02.RedGameModel;

import java.io.IOException;
import java.util.List;

/**
 * Provides a view of the SoloRedGame.
 */
public class SoloRedGameTextView implements RedGameView {
  private final RedGameModel<?> model;
  private Appendable appendable;

  /**
   * Constructs a view for the SoloRedGame, allowing states of the game to be displayed.
   * (hw2 constructor)
   * @param model The model that represents a game of SoloRedGame that will be played.
   */
  public SoloRedGameTextView(RedGameModel<?> model) {
    this.model = model;
  }

  /**
   * Constructs a view for the SoloRedGame, allowing states of the game to be displayed.
   * @param model The model that represents a game of SoloRedGame that will be played.
   * @param appendable Where the output destination where the game state will be rendered.
   */
  public SoloRedGameTextView(RedGameModel<?> model, Appendable appendable) {
    if (model == null || appendable == null) {
      throw new IllegalArgumentException("Model and Appendable cannot be null!");
    }
    this.model = model;
    this.appendable = appendable;
  }

  /**
   * Displays the state of the game.
   */
  public String toString() {
    StringBuilder buildState = new StringBuilder();

    // display canvas
    Object canvas = model.getCanvas();
    buildState.append("Canvas: ").append(canvas.toString().substring(0, 1)).append("\n");

    // display palettes
    for (int i = 0; i < model.numPalettes(); i++) {
      List<?> palette = model.getPalette(i);
      if (i == model.winningPaletteIndex()) {
        buildState.append("> P").append(i + 1).append(": ");
      } else {
        buildState.append("P").append(i + 1).append(": ");
      }

      for (int j = 0; j < palette.size(); j++) {
        buildState.append(palette.get(j).toString());
        if (j < palette.size() - 1) {
          buildState.append(" ");
        }
      }
      buildState.append("\n");
    }


    // Display the hand of the player
    List<?> hand = model.getHand();
    buildState.append("Hand: ").append(formatCards(hand));
    return buildState.toString();
  }

  // helper method to format the player's hand as a String
  private String formatCards(List<?> cards) {
    if (cards.isEmpty()) {
      return "";
    }
    StringBuilder formattedCards = new StringBuilder();

    for (Object card : cards) {
      if (formattedCards.length() > 0) {
        formattedCards.append(" ");
      }
      formattedCards.append(card.toString());
    }

    return formattedCards.toString();
  }

  /**
   * Renders a model in some manner (e.g. as text, or as graphics, etc.).
   * @throws IOException if the rendering fails for some reason
   */
  public void render() throws IOException {
    if (appendable == null) {
      throw new IOException("Appendable has not been set for rendering.");
    }
    appendable.append(this.toString());
  }

}