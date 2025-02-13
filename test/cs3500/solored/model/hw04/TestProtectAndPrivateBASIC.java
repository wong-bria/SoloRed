package cs3500.solored.model.hw04;

import cs3500.solored.model.hw02.SoloRedGameModel;

/**
 * This is the concrete test class for SoloRedGameModel
 * to test protected and private methods.
 */
public class TestProtectAndPrivateBASIC extends TestAbstractProtectAndPrivate<SoloRedGameModel> {

  @Override
  public void init() {
    game = new SoloRedGameModel();
  }
}
