### Solo Red Game
A text-based application that allows for a single player card game.

## Features ✨
- Developed using the model-view-controller pattern using Java 📱
- Utilized abstraction and object-oriented design patterns 🖥️
- Validated methods work as specified using mocks and JUnit tests 🧪

## Game Instructions
- There are two versions of the game a user can play: "basic" and "advanced".
- All games will have a deck of 35 playable cards.
- Each card has 1 of 5 card colors: Red (R), Orange (O), Blue (B), Indigo (I), and Violet (V).
- Each card also has a number value (1-7).
- There is a canvas that dictates the winning rule, and the starting canvas is a 
  special Red (R) card with no number.
- The goal of the game is to use all cards in the deck while ensuring exactly one palette is winning each turn.
- In a given turn, a user can either play a card to a losing palette then have a card be drawn from the deck
  automatically, or play a card to the canvas then play a card to a losing palette and have a card
  be drawn from the deck automatically.
- A winning palette is indicated by the '>' symbol before a palette's name.
- A user can type 'palette' followed by a number to indicate the palette they want to 
  play to, followed by the index of the card from the hand they would like to play onto
  the specified palette. (Index starts at 1)
- If a user plays to a losing palette, but the winning palette doesn't switch, then the player loses.
- A user can type 'canvas' to indicate they want to play a card to the canvas, followed by the index of the card
  from the hand they would like to play onto the canvas. (Index starts at 1)
- A user can type 'q' or 'Q' to quit.

# Canvas Rules Based on Color
- Red (R): The palette with the highest numbered card is the winning palette.
- Orange (O): The palette with the most cards of a single number is the winning palette.
- Blue (B): The palette with the most different colors is the winning palette.
- Indigo (I): The palette with the longest run is the winning palette.
  - Longest run: Most consecutive card numbers in a row.
  - Example: A run of 1, 2, 3, 4, 5 has a run of 5 compared to 2, 3, 4 with a run of 3. 
- Violet (V): The palette with the most card that has a number below 4 is the winning palette.
- In case of ties, consider only the cards contributing to winning palette decision then find the card
  with the closest proximity to Red.
  - Order of proximity to Red: Red (R) > Orange (O) > Blue (B) > Indigo (I) > Violet (V) 

# Difference between the "basic" and "advanced" verison
- In the "basic" version, after playing a card to a losing palette, the user is able to draw
  until the specified max hand size or until the deck is empty.
- In the "advanced" version, after playing a card to a losing palette, the user is able to draw
  one or two cards depending on if they met a certain condition and if the deck is not empty.
  - Conditon to draw two cards: 
    - If the user plays a card to the canvas and the number is greater than the number of cards
    in the current winning palette, then the user gets to draw two cards the next time after they
    play to a palette. Otherwise, the user draws one card. After drawing two cards, the user must
    play to the canvas and meet the same condition to draw two cards again.

## Example

## How to Run 🚀
- The first argument is the version of the game: basic or advanced
- The second argument is optional and represents the number of palettes
- The third argument is optional and represents the max number of cards in a hand
- If no arguments are given, then the basic version of the game with 4 palettes 
  and a hand size of 7 will be used.