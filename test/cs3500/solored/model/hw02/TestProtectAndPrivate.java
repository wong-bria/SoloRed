package cs3500.solored.model.hw02;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test protected and private methods.
 */
public class TestProtectAndPrivate {
  // test getValue from Number enum
  @Test
  public void testGetValue() {
    Assert.assertEquals(1, Number.One.getValue());
  }

  // test toString from Number enum
  @Test
  public void testToStringNumber() {
    Assert.assertEquals("1", Number.One.toString());
  }

  // test getColor from SoloRedCard
  @Test
  public void testGetColorSoloRedCard() {
    SoloRedCard red1 = new SoloRedCard(Color.Red, Number.One);
    Assert.assertEquals(Color.Red, red1.getColor());
  }

  // test getNumber from SoloRedCard
  @Test
  public void testGetNumberSoloRedCard() {
    SoloRedCard red1 = new SoloRedCard(Color.Red, Number.One);
    Assert.assertEquals(1, red1.getNumber());
  }

  // test getNumberValue from SoloRedCard
  @Test
  public void testGetNumberValueSoloRedCard() {
    SoloRedCard red1 = new SoloRedCard(Color.Red, Number.One);
    Assert.assertEquals(Number.One, red1.getNumberValue());
  }

  // test toString from SoloRedCard
  @Test
  public void testToStringSoloRedCard() {
    SoloRedCard red1 = new SoloRedCard(Color.Red, Number.One);
    Assert.assertEquals("R1", red1.toString());
  }

  // test toString from SoloRedCard for special red card
  @Test
  public void testToStringSoloRedCardSpecialRedCard() {
    SoloRedCard red = new SoloRedCard(Color.Red);
    Assert.assertEquals("R", red.toString());
  }

  // test equals method in SoloRedCard when give same object
  @Test
  public void testEqualsSame() {
    SoloRedCard red1 = new SoloRedCard(Color.Red, Number.One);
    SoloRedCard red1Again = new SoloRedCard(Color.Red, Number.One);

    Assert.assertEquals("Both are same since both R1",
            true, red1.equals(red1Again));
  }

  // test equals method in SoloRedCard when give two different objects of same type
  @Test
  public void testEqualsDifObjSameType() {
    SoloRedCard red1 = new SoloRedCard(Color.Red, Number.One);
    SoloRedCard red2 = new SoloRedCard(Color.Red, Number.Two);

    Assert.assertEquals("Both are different since one R1 and other R2",
            false, red1.equals(red2));
  }

  // test equals method in SoloRedCard when give two different objects of different type
  @Test
  public void testEqualsDifObjDifType() {
    SoloRedCard red1 = new SoloRedCard(Color.Red, Number.One);
    SoloRedGameModel model = new SoloRedGameModel();

    Assert.assertEquals("Both are different since one is a SoloRedCard"
                    + "and other is a SoloRedGameModel",
            false, red1.equals(model));
  }


  // test if same object returns same hashcode when called multiple times
  @Test
  public void testSameObjHashCodeMultipleTimes() {
    SoloRedCard red1 = new SoloRedCard(Color.Red, Number.One);
    Assert.assertEquals(1839162858, red1.hashCode());
    Assert.assertEquals(1839162858, red1.hashCode());
    Assert.assertEquals(1839162858, red1.hashCode());
  }

  // test if different equal object returns same hashcode
  @Test
  public void testDifEqualObjSameHashCode() {
    SoloRedCard red1 = new SoloRedCard(Color.Red, Number.One);
    SoloRedCard red1Again = new SoloRedCard(Color.Red, Number.One);
    Assert.assertEquals(1839162858, red1.hashCode());
    Assert.assertEquals(1839162858, red1Again.hashCode());
  }

  // test if different object returns different hashcode
  @Test
  public void testDifObjDifHashCode() {
    SoloRedCard red1 = new SoloRedCard(Color.Red, Number.One);
    SoloRedCard red2 = new SoloRedCard(Color.Red, Number.Two);
    Assert.assertEquals(1839162858, red1.hashCode());
    Assert.assertEquals(869613146, red2.hashCode());
  }

  // test that SoloRedCard constructor creates correct card given Color and Number
  @Test
  public void testSoloRedCardConstructor() {
    SoloRedCard red1 = new SoloRedCard(Color.Red, Number.One);
    Assert.assertEquals("Should create a R1 card", "R1", red1.toString());
  }

  // test that SoloRedCard constructor creates correct special card given Color
  @Test
  public void testSoloRedCardSpecialCardConstructor() {
    SoloRedCard red = new SoloRedCard(Color.Red);
    Assert.assertEquals("Should create a R card", "R", red.toString());
  }
}
