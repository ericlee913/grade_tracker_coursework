package uk.ac.ucl.comp0010.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for Module class.
 */
public class ModuleTest {

  private Module module;

  /**
   * Set up new Module instance for testing before each test case.
   */
  @BeforeEach
  public void setUp() {
    module = new Module("COMP0010", "Software Engineering", true);
  }

  /**
   * Tests the Module constructor and getter methods.
   */
  @Test
  public void testModuleConstructorAndGetters() {
    assertEquals("COMP0010", module.getCode());
    assertEquals("Software Engineering", module.getName());
    assertEquals(true, module.getMnc());
  }

  /**
   * Tests the Module setCode method.
   */
  @Test
  public void testSetCode() {
    module.setCode("COMP0001");
    assertEquals("COMP0001", module.getCode());
  }

  /**
   * Tests the Module setName method.
   */
  @Test
  public void testSetName() {
    module.setName("Machine Learning");
    assertEquals("Machine Learning", module.getName());
  }


  /**
   * Tests the Module setMnc method.
   */
  @Test
  public void testSetMnc() {
    module.setMnc(false);
    assertFalse(module.getMnc());
  }
}
