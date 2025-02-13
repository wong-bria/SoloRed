package cs3500.solored.model.hw04;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cs3500.solored.model.hw02.Color;
import cs3500.solored.model.hw02.SoloRedCard;
import cs3500.solored.model.hw04.AbstractSoloRedGameModel;

/**
 * Static methods to help evaluate the winning palette in a SoloRedGame
 * depending on the color of the canvas.
 */
public class WinningPaletteEvaluator {
  /**
   * Finds the winning palette when the canvas color is Red (highest card wins).
   *
   * @return the index of the winning palette
   */
  protected static int findWinningPaletteByHighestCard(AbstractSoloRedGameModel model) {
    SoloRedCard winningCard = null;
    int winningPaletteIndex = -1;

    for (int i = 0; i < model.numPalettes(); i++) {
      List<SoloRedCard> palette = model.getPalette(i);
      for (SoloRedCard card : palette) {
        if (winningCard == null || isHigher(card, winningCard)) {
          winningCard = card;
          winningPaletteIndex = i;
        }
      }
    }

    return winningPaletteIndex;
  }

  /**
   * Determines if card1 has a higher number than card2.
   *
   * @param card1 the first card to compare
   * @param card2 the second card to compare
   * @return true if card1 is bigger than card2, false otherwise
   */
  private static boolean isHigher(SoloRedCard card1, SoloRedCard card2) {
    // Compare by number first
    if (card1.getNumber() != card2.getNumber()) {
      return card1.getNumber() > card2.getNumber();
    }
    // If numbers are equal, compare by color based on proximity to Red (R > O > B > I > V)
    return getColorRank(card1.getColor()) < getColorRank(card2.getColor());
  }

  /**
   * Returns the rank of the color based on proximity to Red
   * (lower rank means closer to Red).
   *
   * @param color the color to rank
   * @return the rank of the color (Red = 1, Orange = 2, Blue = 3, Indigo = 4, Violet = 5)
   */
  private static int getColorRank(Color color) {
    switch (color) {
      case Red:
        return 1;
      case Orange:
        return 2;
      case Blue:
        return 3;
      case Indigo:
        return 4;
      case Violet:
        return 5;
      default:
        throw new IllegalArgumentException("Unknown color: " + color);
    }
  }


  /**
   * Finds the winning palette when the canvas color is Orange (most cards of a single number).
   *
   * @return the index of the winning palette
   */
  protected static int findWinningPaletteByMostCommonNumber(AbstractSoloRedGameModel model) {
    int winningPaletteIndex = -1;
    int maxCount = -1;
    Map<SoloRedCard, Integer> mostCommonNumbers = new HashMap<>();

    for (int i = 0; i < model.numPalettes(); i++) {
      List<SoloRedCard> palette = model.getPalette(i);
      Map<Integer, Integer> numberCounts = new HashMap<>();

      // Count occurrences of each card number in the current palette
      for (SoloRedCard card : palette) {
        int number = card.getNumber();
        numberCounts.put(number, numberCounts.getOrDefault(number, 0) + 1);
      }

      // Find the maximum count of any single number in this palette
      int paletteMaxCount = Collections.max(numberCounts.values());

      // collects cards that contributed to the max count of a single number in a palette
      contributeToMaxCount(mostCommonNumbers, i, palette, numberCounts, paletteMaxCount);

      // find highest number among mostCommonNumbers
      Map<SoloRedCard, Integer> highestNumberList = new HashMap<>();

      if (paletteMaxCount > maxCount) {
        // dont want to clear HashMap for first palette, indexed 0
        if (maxCount != -1) {
          mostCommonNumbers.clear();
        }
        // collects cards that contributed to the max count of a single number in a palette
        contributeToMaxCount(mostCommonNumbers, i, palette, numberCounts, paletteMaxCount);

        maxCount = paletteMaxCount;
        winningPaletteIndex = i;
      } else if (paletteMaxCount == maxCount) {

        // find highest card among mostCommonNumbers and put in a list
        findHighestCard(mostCommonNumbers, highestNumberList);

        // find closest to red
        SoloRedCard closestToRed = closestToRedForMCN(highestNumberList);
        if (closestToRed != null) {
          winningPaletteIndex = highestNumberList.get(closestToRed);
        }
      }
    }
    return winningPaletteIndex;
  }

  // helper method for findWinningPaletteByMostCommonNumber to find
  // highest value card out of the most common number
  private static void findHighestCard(Map<SoloRedCard, Integer> mostCommonNumbers,
                                      Map<SoloRedCard, Integer> highestNumberList) {
    int highestNum = Integer.MIN_VALUE;

    // Find the highest card number
    for (SoloRedCard card : mostCommonNumbers.keySet()) {
      if (card.getNumber() > highestNum) {
        highestNum = card.getNumber();
      }
    }

    // Put the highest card number in the list
    for (SoloRedCard card : mostCommonNumbers.keySet()) {
      if (card.getNumber() == highestNum) {
        highestNumberList.put(card, mostCommonNumbers.get(card));
      }
    }
  }


  // helper method for findWinningPaletteByMostCommonNumber to find
  // cards that are closest to red in HashMap of mostCommonNumbers
  private static SoloRedCard closestToRedForMCN(Map<SoloRedCard, Integer> highestNumberList) {
    SoloRedCard closestToRed = null;
    int lowestColorRank = 50;

    List<Integer> colorRanks = new ArrayList<>();
    for (SoloRedCard card : highestNumberList.keySet()) {
      int colorRank = getColorRank(card.getColor());
      colorRanks.add(colorRank);
    }

    int lowestNum = Collections.min(colorRanks);
    for (SoloRedCard card : highestNumberList.keySet()) {
      if (getColorRank(card.getColor()) == lowestNum) {
        closestToRed = card;
        lowestColorRank = lowestNum;
      }
    }

    return closestToRed;
  }

  // helper method for findWinningPaletteByMostCommonNumber to find cards
  // that were part of the most common number
  private static void contributeToMaxCount(
          Map<SoloRedCard, Integer> mostCommonNumbers,
          int i, List<SoloRedCard> palette,
          Map<Integer, Integer> numberCounts, int paletteMaxCount) {
    for (Map.Entry<Integer, Integer> entry : numberCounts.entrySet()) {
      if (entry.getValue() == paletteMaxCount) {
        for (SoloRedCard card : palette) {
          if (card.getNumber() == entry.getKey()) {
            mostCommonNumbers.put(card, i);
          }
        }
      }
    }
  }


  // helper method for finding winning palette under Blue rule
  protected static int findWinningPaletteByMostDifferentColors(AbstractSoloRedGameModel model) {
    int winningPaletteIndex = -1;
    int maxUniqueColors = -1;
    List<Integer> tiedPalettes = new ArrayList<>();

    for (int i = 0; i < model.numPalettes(); i++) {
      List<SoloRedCard> palette = model.getPalette(i);
      Set<Color> uniqueColors = new HashSet<>();

      // Count unique colors in the current palette
      for (SoloRedCard card : palette) {
        uniqueColors.add(card.getColor());
      }

      int uniqueColorCount = uniqueColors.size();

      // Check if this palette has more unique colors than the current max
      if (uniqueColorCount > maxUniqueColors) {
        if (maxUniqueColors != -1) {
          tiedPalettes.clear();
        }
        maxUniqueColors = uniqueColorCount;
        winningPaletteIndex = i;
        tiedPalettes.add(i); // Add current palette
      } else if (uniqueColorCount == maxUniqueColors) {
        tiedPalettes.add(i);
      }
    }

    // If there's a tie, find the highest number card among tied palettes
    if (tiedPalettes.size() > 1) {
      Map<SoloRedCard, Integer> highestNumberCards = findHighestNumberCards(tiedPalettes, model);

      // Determine the card closest to red among the highest number cards
      SoloRedCard closestToRed = null;
      for (SoloRedCard card : highestNumberCards.keySet()) {
        if (closestToRed == null || getColorRank(card.getColor())
                < getColorRank(closestToRed.getColor())) {
          closestToRed = card;
          winningPaletteIndex = highestNumberCards.get(closestToRed);
        }
      }
    }

    return winningPaletteIndex;
  }


  // helper method to find the highest numbered card
  private static Map<SoloRedCard, Integer> findHighestNumberCards(
          List<Integer> tiedPalettes, AbstractSoloRedGameModel model) {
    int highestNum = -1;
    Map<SoloRedCard, Integer> highestNumberCards = new HashMap<>();

    for (int index : tiedPalettes) {
      List<SoloRedCard> palette = model.getPalette(index);
      for (SoloRedCard card : palette) {
        if (card.getNumber() > highestNum) {
          highestNum = card.getNumber();
          highestNumberCards.clear(); // Clear previous cards
          highestNumberCards.put(card, index); // Add new highest card
        } else if (card.getNumber() == highestNum) {
          highestNumberCards.put(card, index);
        }
      }
    }
    return highestNumberCards;
  }


  // Helper method for finding winning palette under Indigo rule
  protected static int findWinningPaletteByLongestRun(AbstractSoloRedGameModel model) {
    int winningPaletteIndex = -1;
    int longestRunLength = 0;
    List<Integer> tiedPalettes = new ArrayList<>();

    for (int i = 0; i < model.numPalettes(); i++) {
      List<SoloRedCard> palette = model.getPalette(i);

      // Sort the cards by their number to check for consecutive runs
      palette.sort(Comparator.comparingInt(SoloRedCard::getNumber));

      int currentRunLength = 1;
      int maxRunLengthInPalette = 1;

      // if palette size is 1
      if (palette.size() == 1) {
        currentRunLength = 1;
        // longestRunLength = 1;
        maxRunLengthInPalette = 1;
      }
      // Iterate through the sorted cards to find the longest run (palette size>1)
      for (int j = 1; j < palette.size(); j++) {
        if (palette.get(j).getNumber() == palette.get(j - 1).getNumber() + 1) {
          currentRunLength++;
        } else {
          // Reset the run length if numbers are not consecutive
          currentRunLength = 1;
        }
        // Track the longest run in the current palette
        if (currentRunLength > maxRunLengthInPalette) {
          maxRunLengthInPalette = currentRunLength;
        }
      }
      // Compare the current palette's longest run with the overall longest run
      if (maxRunLengthInPalette > longestRunLength) {
        longestRunLength = maxRunLengthInPalette;
        winningPaletteIndex = i;
        tiedPalettes.clear(); // Clear previous ties
        tiedPalettes.add(i);  // Add the current palette as a tie candidate
      } else if (maxRunLengthInPalette == longestRunLength) {
        tiedPalettes.add(i); // Add this palette to the tie list
      }
    }
    // If there's a tie, break it by the highest number in the run
    if (tiedPalettes.size() > 1) {
      winningPaletteIndex = findHighestNumberIndigoRule(tiedPalettes, winningPaletteIndex, model,
              longestRunLength);
    } else {
      winningPaletteIndex = tiedPalettes.get(0);
    }
    return winningPaletteIndex;
  }

  // helper method for findWinningPaletteByLongestRun to find highest numbered card in longest run
  private static int findHighestNumberIndigoRule(
          List<Integer> tiedPalettes,
          int winningPaletteIndex, AbstractSoloRedGameModel model, int longestRunLength) {
    int highestRunNumber = -1;

    for (int index : tiedPalettes) {
      List<SoloRedCard> palette = model.getPalette(index);

      //added
      List<SoloRedCard> sortedPalette = new ArrayList<>(palette);
      sortedPalette.sort(Comparator.comparingInt(SoloRedCard::getNumber));

      if (longestRunLength == 1) {
        for (SoloRedCard card : sortedPalette) {
          if (card.getNumber() > highestRunNumber) {
            highestRunNumber = card.getNumber();
            winningPaletteIndex = index;
          }
        }
      } else {

        for (int j = 1; j < sortedPalette.size(); j++) {
          if (sortedPalette.get(j).getNumber() == sortedPalette.get(j - 1).getNumber() + 1) {
            if (sortedPalette.get(j).getNumber() > highestRunNumber) {
              highestRunNumber = sortedPalette.get(j).getNumber();
              winningPaletteIndex = index;
            }
          }
        }
      }
    }

    // If still tied, check the color closest to Red
    if (tiedPalettes.size() > 1) {
      winningPaletteIndex = closestToRedIndigoRule(tiedPalettes,
              highestRunNumber, winningPaletteIndex, model);
    }
    return winningPaletteIndex;
  }

  // helper method for findWinningPaletteByLongestRun to find the closest card to red
  private static int closestToRedIndigoRule(
          List<Integer> tiedPalettes,
          int highestRunNumber, int winningPaletteIndex, AbstractSoloRedGameModel model) {
    SoloRedCard closestToRedCard = null;
    int closestColorRank = Integer.MAX_VALUE;

    for (int index : tiedPalettes) {
      List<SoloRedCard> palette = model.getPalette(index);

      for (SoloRedCard card : palette) {
        if (card.getNumber() == highestRunNumber) {
          int currentColorRank = getColorRank(card.getColor());
          if (closestToRedCard == null || currentColorRank < closestColorRank) {
            closestToRedCard = card;
            closestColorRank = currentColorRank;
            winningPaletteIndex = index;
          }
        }
      }
    }
    return winningPaletteIndex;
  }

  // helper method for finding winning palette under Violet rule
  protected static int findWinningPaletteByMostCardsBelowFour(AbstractSoloRedGameModel model) {
    int winningPaletteIndex = -1;
    int maxCardsBelowFour = 0;
    List<Integer> tiedPalettes = new ArrayList<>();

    // Loop through each palette and count how many cards are below 4
    for (int i = 0; i < model.numPalettes(); i++) {
      List<SoloRedCard> palette = model.getPalette(i);
      int cardsBelowFourCount = 0;

      // Count the cards below 4 in the current palette
      for (SoloRedCard card : palette) {
        if (card.getNumber() < 4) {
          cardsBelowFourCount++;
        }
      }

      // Compare the current palette's count with the maximum
      if (cardsBelowFourCount > maxCardsBelowFour) {
        maxCardsBelowFour = cardsBelowFourCount;
        winningPaletteIndex = i;
        tiedPalettes.clear(); // Clear previous ties
        tiedPalettes.add(i); // Add the current palette as a potential winner
      } else if (cardsBelowFourCount == maxCardsBelowFour) {
        tiedPalettes.add(i); // Add this palette to the tie list
      }
    }

    // If there's a tie, break it by the highest number below 4
    if (tiedPalettes.size() > 1) {
      int highestBelowFourNumber = -1;

      for (int index : tiedPalettes) {
        List<SoloRedCard> palette = model.getPalette(index);

        for (SoloRedCard card : palette) {
          if (card.getNumber() < 4 && card.getNumber() > highestBelowFourNumber) {
            highestBelowFourNumber = card.getNumber();
            winningPaletteIndex = index;
          }
        }
      }
      // if still tie, then find closest card to red
      winningPaletteIndex = findClosestToRedCard(tiedPalettes, highestBelowFourNumber, model);
    }
    return winningPaletteIndex;
  }

  // helper method for findWinningPaletteByMostCardsBelowFour
  // to find closest card to red and returning that card's index
  private static int findClosestToRedCard(
            List<Integer> tiedPalettes, int targetNumber, AbstractSoloRedGameModel model) {
    SoloRedCard closestToRedCard = null;
    int winningPaletteIndex = -1;

    for (int index : tiedPalettes) {
      List<SoloRedCard> palette = model.getPalette(index);

      for (SoloRedCard card : palette) {
        if (card.getNumber() == targetNumber) {
          if (closestToRedCard == null || getColorRank(card.getColor())
                  < getColorRank(closestToRedCard.getColor())) {
            closestToRedCard = card;
            winningPaletteIndex = index;
          }
        }
      }
    }
    return winningPaletteIndex;
  }


}

