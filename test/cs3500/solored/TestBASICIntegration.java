package cs3500.solored;


import java.util.Random;
import cs3500.solored.model.hw02.SoloRedGameModel;


/**
 * This is the concrete test class for SoloRedGameModel to test
 * integration between the view, model, and controller.
 */
public class TestBASICIntegration extends AbstractIntegrationTest<SoloRedGameModel> {

  @Override
  public void init() {
    game = new SoloRedGameModel();
    seededGame = new SoloRedGameModel(new Random(4));
  }




}
