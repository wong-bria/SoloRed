package cs3500.solored.controller;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import cs3500.solored.model.hw02.Card;
import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.view.hw02.RedGameView;
import cs3500.solored.view.hw02.SoloRedGameTextView;

/**
 * Represents the controller for a game of SoloRedGame. This class handles the interactions
 * between the game model, view, and user input/output. It also controls the flow of the game
 * by processing commands, updating game states, and creates output to be displayed.
 */
public class SoloRedTextController implements RedGameController {
  private final Appendable ap;
  private final Scanner sc;
  private RedGameView view;
  private boolean canDraw;

  /**
   * A constructor that will create an object to handle user input
   * and output.
   * @param rd Object where user input will be read.
   * @param ap Object where game output will be written.
   * @throws IllegalArgumentException If rd or ap is null.
   */
  public SoloRedTextController(Readable rd, Appendable ap) throws IllegalArgumentException {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("rd and ap can't be null!");
    }
    this.ap = ap;
    this.sc = new Scanner(rd);
    this.canDraw = false;
  }

  /**
   * Used to "run" a game of SoloRedGame. It  calls helper methods
   * to handle different commands given by the user, calls the view
   * to render the output to the user, and overall controls the flow of the game,
   * such as allowing user to draw after playing to palette but not canvas.
   * @param model The model that represents a game of SoloRedGame that will be played.
   * @param deck The deck to be used for a game.
   * @param shuffle Whether the deck will be shuffled or not.
   * @param numPalettes The number of palettes a game will have. 3 means 3 palettes.
   * @param handSize The max number of cards a hand can have. 3 means 3 cards max.
   * @param <C> The type of cards used.
   */
  public <C extends Card> void playGame(
          RedGameModel<C> model, List<C> deck, boolean shuffle, int numPalettes, int handSize) {
    if (model == null) {
      throw new IllegalArgumentException("Model can't be null!");
    }
    try {
      // Attempt to start the game
      try {
        model.startGame(deck, shuffle, numPalettes, handSize);
      } catch (IllegalStateException | IllegalArgumentException e) {
        throw new IllegalArgumentException("Can't start game. " + e.getMessage());
      }
      this.view = new SoloRedGameTextView(model, ap);

      while (!model.isGameOver()) {
        if (canDraw) {
          model.drawForHand();
          canDraw = false;
        }
        view.render();
        displayDeckCount(model);

        String command = readNextCommand(model);

        if (command == null) {
          break;
        }

        if (handleTurn(model, command)) {
          return;
        }
      }
      renderGameResult(model);
    } catch (IOException e) {
      throw new IllegalStateException("Unable to successfully receive"
              + " input or transmit output.", e);
    }
  }

  // Redirects to helper methods to perform certain actions depending on given command.
  private <C extends Card> boolean handleTurn(
          RedGameModel<C> model, String command) throws IOException {
    switch (command) {
      case "q":
      case "Q":
        renderQuit(model);
        return true;
      case "palette":
        return handlePalette(model);
      case "canvas":
        return handleCanvas(model);
      default:
        transmitMessage("Invalid command. Try again. Commands are: 'q',"
                + " 'Q', 'palette', or 'canvas'");
        return false;
    }
  }

  // Determines what to do when given command is canvas.
  private <C extends Card> boolean handleCanvas(RedGameModel<C> model) throws IOException {
    Integer cardIndex = readNextPositiveInt();
    if (handleQuit(model, cardIndex)) {
      return true;
    }
    int cardIdx = cardIndex - 1;

    try {
      model.playToCanvas(cardIdx);
    } catch (IllegalArgumentException | IllegalStateException exception) {
      transmitMessage("Invalid move. Try again. " + exception.getMessage());
    }
    return false;
  }

  // Determines what to do when given command is palette.
  private <C extends Card> boolean handlePalette(RedGameModel<C> model) throws IOException {
    Integer paletteIndex = readNextPositiveInt();
    if (handleQuit(model, paletteIndex)) {
      return true;
    }
    Integer cardIndex = readNextPositiveInt();
    if (handleQuit(model, cardIndex)) {
      return true;
    }
    int paletteIdx = paletteIndex - 1;
    int cardIdx = cardIndex - 1;

    try {
      model.playToPalette(paletteIdx, cardIdx);
      canDraw = true;
    } catch (IllegalArgumentException | IllegalStateException exception) {
      transmitMessage("Invalid move. Try again. " + exception.getMessage());
    }
    return false;
  }

  // Displays the number of cards left in deck.
  private <C extends Card> void displayDeckCount(RedGameModel<C> model) throws IOException {
    transmitMessage("");
    transmitMessage("Number of cards in deck: " + model.numOfCardsInDeck());
  }

  // Displays the quit message, state, and remaining amount of cards in deck
  private <C extends Card> void renderQuit(RedGameModel<C> model) throws IOException {
    transmitMessage("Game quit!");
    transmitMessage("State of game when quit:");

    view.render();
    displayDeckCount(model);
  }

  // Displays the win/lost message, state, and remaining amount of cards in deck
  private <C extends Card> void renderGameResult(RedGameModel<C> model) throws IOException {
    if (model.isGameWon() && model.isGameWon()) {
      transmitMessage("Game won.");
    } else if (model.isGameOver() && !model.isGameWon()) {
      transmitMessage("Game lost.");
    }
    view.render();
    displayDeckCount(model);
  }

  // Determines if a user has quit or not.
  private <C extends Card> boolean handleQuit(
          RedGameModel<C> model, Integer paletteIndex) throws IOException {
    if (paletteIndex == null) {
      renderQuit(model);
      return true;
    }
    return false;
  }

  /**
   * Reads the next command from the input.
   *
   * @return the next command as a string, or null if the input stream ends
   * @throws IOException if there's an error with the Appendable
   */
  private String readNextCommand(RedGameModel model) throws IOException {
    try {
      if (!sc.hasNext()) {
        throw new IllegalStateException("Input failure: no more commands available.");
      }
      while (sc.hasNext()) {
        String token = sc.next();

        if (token.equalsIgnoreCase("palette")
                || token.equalsIgnoreCase("canvas")
                || token.equalsIgnoreCase("q")) {
          return token;
        } else {
          transmitMessage("Invalid command. Try again."
                  + " Commands are: 'q', 'Q', 'palette', or 'canvas'");
          view.render();
          displayDeckCount(model);
        }
      }
    } catch (IllegalStateException e) {
      throw new IllegalStateException("Input failure: unable to read the next command.", e);
    }
    return null;
  }


  /**
   * Reads the next integer from the input.
   * If the next token is 'q' or 'Q', returns null to indicate quitting.
   *
   * @return the next integer, or null if the user chose to quit
   * @throws IOException if there's an error with the Appendable
   */
  private Integer readNextInt() throws IOException {
    try {
      if (!sc.hasNext()) {
        throw new IllegalStateException("Input failure: no more input available.");
      }
      while (sc.hasNext()) {
        String token = sc.next();

        if (token.equalsIgnoreCase("q")) {
          return null;
        }

        try {
          return Integer.parseInt(token);
        } catch (NumberFormatException e) {
          continue;
        }
      }
    } catch (IllegalStateException e) {
      throw new IllegalStateException("Input failure: unable to read the next integer.", e);
    }
    return null;
  }

  // This reads an integer and ensures it's >= 0 / a natural number
  private Integer readNextPositiveInt() throws IOException {
    while (true) {
      Integer input = readNextInt();
      if (input == null) {
        return null;
      }
      if (input >= 0) {
        return input;
      }
      continue;
    }
  }

  /**
   * Transmits a message to the Appendable.
   *
   * @param message The message to transmit.
   * @throws IllegalStateException If there is an I/O failure.
   */
  private void transmitMessage(String message) {
    try {
      ap.append(message + "\n");
    } catch (IOException e) {
      throw new IllegalStateException("Unable to transmit message.", e);
    }
  }

}
