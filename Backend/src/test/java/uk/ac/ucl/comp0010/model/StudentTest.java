package uk.ac.ucl.comp0010.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.ucl.comp0010.exception.NoGradeAvailableException;
import uk.ac.ucl.comp0010.exception.NoRegistrationException;

/**
 * Unit tests for Student class.
 */
public class StudentTest {

  private Student student;
  private Module module1;
  private Module module2;
  private Grade grade1;
  private Grade grade2;
  private Registration registration1;
  private Registration registration2;

  /**
   * Set up new Student, Module, Grade, and Registration instances for testing before each test
   * case.
   */
  @BeforeEach
  public void setUp() {
    student = new Student(123, "John", "Smith", "jsmith", "jsmith@example.com");
    module1 = new Module("COMP0010", "Software Engineering", true);
    module2 = new Module("COMP0009", "Logic", true);
    grade1 = new Grade(85, module1);
    grade2 = new Grade(90, module2);
    registration1 = new Registration(student, module1);
    registration2 = new Registration(student, module2);
  }

  /**
   * Tests the Student Constructor and getter methods.
   */
  @Test
  public void testStudentConstructorAndGetters() {
    assertEquals(123, student.getId());
    assertEquals("John", student.getFirstName());
    assertEquals("Smith", student.getLastName());
    assertEquals("jsmith", student.getUserName());
    assertEquals("jsmith@example.com", student.getEmail());
  }

  /**
   * Tests the setId method.
   */
  @Test
  public void testSetId() {
    student.setId(2);
    assertEquals(2, student.getId());
  }

  /**
   * Tests the setFirstName method.
   */
  @Test
  public void testSetFirstName() {
    student.setFirstName("Jane");
    assertEquals("Jane", student.getFirstName());
  }

  /**
   * Tests the setLastName method.
   */
  @Test
  public void testSetLastName() {
    student.setLastName("Olivia");
    assertEquals("Olivia", student.getLastName());
  }

  /**
   * Tests the setUserName method.
   */
  @Test
  public void testSetUserName() {
    student.setUserName("jolivia");
    assertEquals("jolivia", student.getUserName());
  }

  /**
   * Tests the setEmail method.
   */
  @Test
  public void testSetEmail() {
    student.setEmail("jolivia@example.com");
    assertEquals("jolivia@example.com", student.getEmail());
  }

  /**
   * Tests the addGrade and getGrade methods.
   *
   * @throws NoRegistrationException when user try to access grades for unregistered modules
   * @throws NoGradeAvailableException when there is no grade available at all or not available for
   *         a specific module
   */
  @Test
  public void testAddGradeAndGetGrade() throws NoRegistrationException, NoGradeAvailableException {
    student.registerModule(module1);
    student.addGrade(grade1);

    Grade retrievedGrade = student.getGrade(module1);
    assertEquals(grade1, retrievedGrade);

    // getGrade for registered module without grade
    student.registerModule(module2);
    Exception exception = assertThrows(NoGradeAvailableException.class, () -> {
      student.getGrade(module2);
    });
    assertEquals("No grades available", exception.getMessage());
  }

  /**
   * Tests the NoRegistrationException throws when user try to access grades for unregistered
   * modules.
   */
  @Test
  public void testGetGradeForUnregisteredModuleThrowsException() {
    Exception exception = assertThrows(NoRegistrationException.class, () -> {
      student.getGrade(module1);
    });
    assertEquals("Module is not registered", exception.getMessage());
  }

  /**
   * Tests the computeAverage method works.
   *
   * @throws NoGradeAvailableException when there is no grade available at all or not available for
   *         a specific module
   */
  @Test
  public void testComputeAverage() throws NoGradeAvailableException {
    student.addGrade(grade1);
    student.addGrade(grade2);
    float average = student.computeAverage();
    assertEquals(87.5, average, 0.01);
  }

  /**
   * Tests the NoGradeAvailableException throws when there is no grade available at all or not
   * available for a specific module.
   */
  @Test
  public void testComputeAverageNoGradesThrowsException() {
    Exception exception = assertThrows(NoGradeAvailableException.class, () -> {
      student.computeAverage();
    });
    assertEquals("No grades available", exception.getMessage());
  }

  /**
   * Tests the registerModule method.
   */
  @Test
  public void testRegisterModule() {
    student.registerModule(module1);
    student.registerModule(module2);
    assertEquals(2, student.getRegistrations().size());
  }

  /**
   * Tests the setRegistrations method works.
   */
  @Test
  public void testSetRegisteredModules() {
    List<Registration> registrations = new ArrayList<>();
    registrations.add(registration1);
    registrations.add(registration2);

    student.setRegistrations(registrations);
    assertEquals(2, student.getRegistrations().size());
    assertTrue(registrations.contains(registration1));
    assertTrue(registrations.contains(registration2));

    // registering same module results in no changes to student's registered modules
    student.registerModule(module2);
    assertEquals(2, student.getRegistrations().size());
    assertTrue(registrations.contains(registration1));
    assertTrue(registrations.contains(registration2));
  }

  /**
   * Tests the getGrades method work.
   */
  @Test
  public void testGetGrades() {
    student.addGrade(grade1);
    student.addGrade(grade2);
    List<Grade> grades = student.getGrades();

    assertEquals(2, grades.size());
    assertTrue(grades.contains(grade1));
    assertTrue(grades.contains(grade2));
  }
}
