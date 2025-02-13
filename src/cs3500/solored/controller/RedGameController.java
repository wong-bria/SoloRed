package cs3500.solored.controller;

import java.util.List;

import cs3500.solored.model.hw02.Card;
import cs3500.solored.model.hw02.RedGameModel;


/**
 * An interface representing the controller for the game of SoloRedGame.
 * Defines the method for controlling the actions of a game and to handle user input and output.
 */
public interface RedGameController {

  /**
   * Used to "run" a game of SoloRedGame. It  calls helper methods
   *    * to handle different commands given by the user, calls the view
   *    * to render the output to the user, and overall controls the flow of the game,
   *    * such as allowing user to draw after playing to palette but not canvas.
   * @param model The model that represents a game of SoloRedGame that will be played.
   * @param deck The deck to be used for a game.
   * @param shuffle Whether the deck will be shuffled or not.
   * @param numPalettes The number of palettes a game will have. 3 means 3 palettes.
   * @param handSize The max number of cards a hand can have. 3 means 3 cards max.
   * @param <C> The type of cards used.
   */
  <C extends Card> void playGame(
          RedGameModel<C> model, List<C> deck, boolean shuffle, int numPalettes, int handSize);
}
