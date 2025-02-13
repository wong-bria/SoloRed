package cs3500.solored.model.hw02;

import java.util.Objects;

/**
 * A concrete card class representing a card in the Solo Red game.
 * A SoloRedCard can be a special red card for the starting canvas with only a color,
 * or it could be a regular card used in the game with a color and number.
 */
public class SoloRedCard implements SoloRedCardInterface {

  private final Color color;
  private final Number number;


  /**
   * Constructor to create a regular SoloRedCard for playing.
   * @param color The color of a card.
   * @param number The number of a card.
   */
  public SoloRedCard(Color color, Number number) {
    this.color = color;
    this.number = number;
  }


  /**
   * Constructor to create the special card for the canvas at the start of the game.
   * @param color The color of a card.
   */
  public SoloRedCard(Color color) {
    this.color = color;
    this.number = null;
  }

  // gets the color of a SoloRedColor
  public Color getColor() {
    return color;
  }

  // gets the number of a SoloRedColor as an integer
  public int getNumber() {
    return number.getValue();
  }

  // gets the number of a SoloRedColor as a Number
  public Number getNumberValue() {
    return number;
  }

  /**
   * Writes a SoloRedColor as a string.
   *
   * @return a String representing a card in the deck or a special card
   */
  public String toString() {
    if (number == null) {
      return color.toString().substring(0, 1); // Special case for the red card at start
    }
    return color.toString().substring(0, 1) + number.toString();
  }

  /**
   * Checks if a SoloRedCard is equal to a SoloRedCard.
   *
   * @param obj an Object
   * @return true if given Object equal to what is calling the method, false otherwise
   */
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof SoloRedCard)) {
      return false;
    }
    SoloRedCard other = (SoloRedCard) obj;
    return color == other.color && number == other.number;
  }

  // Returns hash code
  public int hashCode() {
    return Objects.hash(color, number);
  }


}
