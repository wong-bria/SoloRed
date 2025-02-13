Changes hw4:
- I changed the constructors for SoloRedCard from protected to public because
I needed to be able to create instances of SoloRedCard outside the package that SoloRedCard
is in for the new homework in order to initialize the special red card, and create
the getAllCards() method my new AdvancedSoloRedGame/AbstractSoloRedGame class.
- I also changed the SoloRedGameModel constructors to use super instead of
initializing its fields in the class since I abstracted them.
- Since I abstracted SoloRedGameModel and AdvancedSoloRedGameModel into AbstractSoloRedGameModel,
and for the method winningPaletteIndex(), it used static methods from WinningPaletteEvaluator,
then I had to move the entire WinningPaletteEvaluator class into the new cs3500.model.solored.hw04
package so that I'm still able to use the protected static methods.
- For the method winningPaletteIndex(), it used static methods from WinningPaletteEvaluator to find
the winning palette, and each static method took in 'this', however since before 'this' only
referred to SoloRedGameModel, but now it has been abstracted, each static method
in WinningPaletteEvaluator now take in AbstractSoloRedGameModel instead of SoloRedGameModel.
- I also included a new helper in SoloRedTextController to transmit any messages I had instead of
calling append, writing my message, and then doing \n everywhere. With this new helper,
it also makes my code more readable and concise.




Changes hw3:
- I changed SoloRedGameModel's winningPaletteIndex() to call static methods from a
different class--WinningPaletteEvaluator--instead of using helper methods in the model to reduce
the complexity of the model.
- I moved all helper methods from winningPaletteIndex() to WinningPaletteEvaluator
for the reason above.
- I also changed the logic to determine the winner palette when the canvas color is indigo
because my previous implementation was wrong when there was more than one card
in a palette.
- Also fixed some messages in the exceptions to include a space between words.
- isGameOver() was incorrectly making games over: if user picked palette 1 which is now the winning
palette, but then played to canvas and made palette 2 the winning palette, isGameOver() would
incorrectly mark the game as over without giving the user a chance to play a card when they are
allowed to. I fixed it to not do this anymore.
- Added comments for the fields in SoloRedGameModel to indicate what the indices for a list
means.
- Added javadoc for the constructor of number class
- Made class javadoc for SoloRedCard to be more informative and helpful.
- I also changed the RedGameView interface to only have render, and not toString as
homework 3 wanted. I did keep the toString method in the SoloRedGameTextView class
though.
- I included a new constructor in SoloRedGameTextView as homework3 specified, and also
included render() to that class as the homework specified.
