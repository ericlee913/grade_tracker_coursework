package uk.ac.ucl.comp0010.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Registration class.
 */
public class RegistrationTest {

  /**
   * Tests the Registration constructor.
   */
  @Test
  public void testDefaultConstructor() {
    Registration registration = new Registration();
    assertNotNull(registration);
  }

  /**
   * Tests the Registration constructor with registered student to a module.
   */
  @Test
  public void testParameterizedConstructor() {
    Student student = new Student();
    Module module = new Module();
    Registration registration = new Registration(student, module);

    assertEquals(student, registration.getStudent());
    assertEquals(module, registration.getModule());
  }

  /**
   * Tests the setId and getId methods.
   */
  @Test
  public void testSetAndGetId() {
    Registration registration = new Registration();
    registration.setId(100L);
    assertEquals(100L, registration.getId());
  }

  /**
   * Tests the setStudent and getStudent methods.
   */
  @Test
  public void testSetAndGetStudent() {
    Registration registration = new Registration();
    Student student = new Student();
    student.setFirstName("John");
    student.setLastName("Smith");

    registration.setStudent(student);
    assertEquals(student, registration.getStudent());
    assertEquals("John", registration.getStudent().getFirstName());
    assertEquals("Smith", registration.getStudent().getLastName());
  }

  /**
   * Tests the setModule and getModule methods.
   */
  @Test
  public void testSetAndGetModule() {
    Registration registration = new Registration();

    Module module = new Module("COMP0010", "Software Engineering", true);

    registration.setModule(module);
    assertEquals(module, registration.getModule());
    assertEquals("COMP0010", registration.getModule().getCode());
    assertEquals("Software Engineering", registration.getModule().getName());
    assertTrue(registration.getModule().getMnc());
  }

}
