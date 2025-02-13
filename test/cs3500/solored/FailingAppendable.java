package cs3500.solored;

import java.io.IOException;

/**
 * A mock to simulate a failure to write to the Appendable.
 */
public class FailingAppendable implements Appendable {

  /**
   * Java doc for stub:
   * Need to simulate a failure to write to the Appendable
   * for this mock, so throw an IOException.
   */
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException("Fail!");
  }

  /**
   * Java doc for stub:
   * Need to simulate a failure to write to the Appendable
   * for this mock, so throw an IOException.
   */
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new IOException("Fail!");
  }

  /**
   * Java doc for stub:
   * Need to simulate a failure to write to the Appendable
   * for this mock, so throw an IOException.
   */
  public Appendable append(char c) throws IOException {
    throw new IOException("Fail!");
  }
}
