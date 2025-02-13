package cs3500.solored.model.hw02;

/**
 * Represents the different numbers a card can be.
 */
public enum Number {
  One(1), Two(2), Three(3), Four(4),
  Five(5), Six(6), Seven(7);

  private final int value;

  /**
   * A constructor to construct a card number with an integer.
   * @param value An integer value to represent a value of a card.
   */
  Number(int value) {
    this.value = value;
  }

  int getValue() {
    return value;
  }

  public String toString() {
    return Integer.toString(value);
  }

}
