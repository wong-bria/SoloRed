package cs3500.solored;

import java.io.InputStreamReader;

import cs3500.solored.controller.SoloRedTextController;
import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw02.SoloRedCard;
import cs3500.solored.model.hw04.RedGameCreator;

/**
 * The main class to play a game of SoloRedGame as either the
 * "basic" or "advanced" version. All games' deck will contain
 * all 35 playable card, which will be shuffled.
 */
public final class SoloRed {
  /**
   * The main method to run a game of SoloRedGame.
   * @param args Takes in the game type the first argument to set the game
   *             variation. The second and third arguments are optional. The
   *             second argument is the number of palettes to be played with,
   *             and the third argument is the max number of cards for the
   *             hand to be played with.
   */
  public static void main(String[] args) {
    RedGameCreator.GameType gameType = RedGameCreator.GameType.BASIC;
    int palettes = 4;
    int handSize = 7;

    boolean invalidInput = false;


    if (args.length > 0) {
      String gameTypeStr = args[0];
      if (gameTypeStr.equals("advanced")) {
        gameType = RedGameCreator.GameType.ADVANCED;
      } else if (gameTypeStr.equals("basic")) {
        gameType = RedGameCreator.GameType.BASIC;
      } else {
        throw new IllegalArgumentException("Invalid game type, must be basic or advanced");
      }
    }

    if (args.length > 1) {
      try {
        palettes = Integer.parseInt(args[1]);
        if (palettes < 2) {
          invalidInput = true;
        }
      } catch (NumberFormatException e) {
        palettes = 4;
      }
    }

    if (args.length > 2) {
      try {
        handSize = Integer.parseInt(args[2]);
        if (handSize < 1) {
          invalidInput = true;
        }
      } catch (NumberFormatException e) {
        handSize = 7;
      }
    }

    if (palettes + handSize > 35) {
      palettes = 4;
      handSize = 7;
      invalidInput = true;
    }

    if (invalidInput) {
      return;
    }

    RedGameModel<SoloRedCard> game = RedGameCreator.createGame(gameType);
    Readable rd = new InputStreamReader(System.in);
    Appendable ap = System.out;
    SoloRedTextController controller = new SoloRedTextController(rd, ap);
    controller.playGame(game, game.getAllCards(), true, palettes, handSize);
  }
}