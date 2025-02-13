package cs3500.solored.model.hw04;

import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw02.SoloRedCard;
import cs3500.solored.model.hw02.SoloRedGameModel;

/**
 * Factory class to create instances of RedGameModel based on given game type.
 * Includes an enumeration of GameType to define available game types:
 * BASIC, and ADVANCED.
 * Creates and returns a game with a certain type depending on requested game type.
 */
public class RedGameCreator {

  /**
   * Enum representing the different types of games that can be created.
   */
  public enum GameType {
    BASIC, ADVANCED;
  }

  /**
   * Creates an instance of RedGameModel based on given game type.
   * @param type The type of game to create. Valid game types include: BASIC and
   *             ADVANCED
   * @return an instance of RedGameModel with game type that is specified
   *         by the given game type.
   */
  public static RedGameModel<SoloRedCard> createGame(GameType type) {
    if (type == null) {
      throw new IllegalArgumentException("Game types can't be null. Valid types "
              + "are: basic, advanced");
    }
    switch (type) {
      case BASIC:
        return new SoloRedGameModel();
      case ADVANCED:
        return new AdvancedSoloRedGameModel();
      default:
        throw new IllegalArgumentException("Game types are: basic, advanced");
    }
  }

}
