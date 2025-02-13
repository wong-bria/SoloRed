package cs3500.solored.model.hw04;

/**
 * This is the concrete test class for AdvancedSoloRedGameModel
 * to test protected and private methods.
 */
public class TestProtectAndPrivateADVANCED extends
        TestAbstractProtectAndPrivate<AdvancedSoloRedGameModel> {
  @Override
  public void init() {
    game = new AdvancedSoloRedGameModel();
  }

}
