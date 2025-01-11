package uk.ac.ucl.comp0010.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for Grade class.
 */
public class GradeTest {

  private Module module;
  private Grade grade;
  private Student student;

  /**
   * Set up new Module, Grade, and Student instance for testing before each test case.
   */
  @BeforeEach
  public void setUp() {
    module = new Module("COMP0010", "Software Engineering", true);
    grade = new Grade(85, module);
    student = new Student(123, "John", "Smith", "jsmith", "jsmith@example.com");
  }

  /**
   * Tests the constructor and getScore method.
   */
  @Test
  public void testConstructorAndGetter() {
    assertEquals(85, grade.getScore());
  }

  /**
   * Tests the setScore method.
   */
  @Test
  public void testSetter() {
    grade.setScore(95);
    assertEquals(95, grade.getScore());
  }

  /**
   * Tests the getStudent method.
   */
  @Test
  public void testGetStudent() {
    grade.setStudent(student);
    assertEquals(student, grade.getStudent());
  }

  /**
   * Tests the setModule method.
   */
  @Test
  public void testSetModule() {
    grade.setModule(module);
    assertEquals(module, grade.getModule());
  }
}
