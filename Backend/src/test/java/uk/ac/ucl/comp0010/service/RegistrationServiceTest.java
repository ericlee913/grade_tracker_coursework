package uk.ac.ucl.comp0010.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import uk.ac.ucl.comp0010.model.Module;
import uk.ac.ucl.comp0010.model.Registration;
import uk.ac.ucl.comp0010.model.Student;
import uk.ac.ucl.comp0010.repository.ModuleRepository;
import uk.ac.ucl.comp0010.repository.RegistrationRepository;
import uk.ac.ucl.comp0010.repository.StudentRepository;

/**
 * Unit tests for RegistrationService class.
 */
public class RegistrationServiceTest {

  private StudentRepository studentRepository;
  private ModuleRepository moduleRepository;
  private RegistrationRepository registrationRepository;

  private RegistrationService registrationService;

  /**
   * Set up new RegistrationService instance and mock StudentRepository, Module Repository, and
   * RegistrationRepository for testing before each test case.
   */
  @BeforeEach
  public void setUp() {
    studentRepository = mock(StudentRepository.class);
    moduleRepository = mock(ModuleRepository.class);
    registrationRepository = mock(RegistrationRepository.class);
    registrationService =
        new RegistrationService(studentRepository, registrationRepository, moduleRepository);
  }

  /**
   * Tests the registerModule method.
   */
  @Test
  public void testRegisterModuleSuccess() {
    Long studentId = 1L;
    String moduleCode = "COMP0010";

    Student mockStudent = new Student();
    mockStudent.setId(studentId);

    Module mockModule = new Module();
    mockModule.setCode(moduleCode);

    Registration mockRegistration = new Registration();
    mockRegistration.setStudent(mockStudent);
    mockRegistration.setModule(mockModule);


    when(studentRepository.findById(studentId)).thenReturn(Optional.of(mockStudent));
    when(moduleRepository.findById(moduleCode)).thenReturn(Optional.of(mockModule));
    when(registrationRepository.save(Mockito.any(Registration.class))).thenReturn(mockRegistration);

    Registration result = registrationService.registerModule(studentId, moduleCode);

    assertNotNull(result);
    assertEquals(mockStudent, result.getStudent());
    assertEquals(mockModule, result.getModule());

    verify(studentRepository, times(1)).findById(studentId);
    verify(moduleRepository, times(1)).findById(moduleCode);
    verify(registrationRepository, times(1)).save(Mockito.any(Registration.class));

  }

  /**
   * Tests the registerModule method does not work when student Id cannot be found or invalid.
   */
  @Test
  public void testRegisterModuleStudentNotFound() {
    Long studentId = 999L;
    String moduleCode = "COMP0010";

    when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> registrationService.registerModule(studentId, moduleCode));

    assertEquals("Student not found.", exception.getMessage());
    verify(studentRepository, times(1)).findById(studentId);
    verify(moduleRepository, never()).findById(anyString());
    verify(registrationRepository, never()).save(any(Registration.class));
  }

  /**
   * Tests registerModule method does not work when module Code cannot be found or invalid.
   */
  @Test
  public void testRegisterModuleModuleNotFound() {
    Long studentId = 1L;
    String moduleCode = "INVALID_MODULE";

    Student mockStudent = new Student();
    mockStudent.setId(studentId);

    when(studentRepository.findById(studentId)).thenReturn(Optional.of(mockStudent));
    when(moduleRepository.findById(moduleCode)).thenReturn(Optional.empty());

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> registrationService.registerModule(studentId, moduleCode));

    assertEquals("Module not found.", exception.getMessage());
    verify(studentRepository, times(1)).findById(studentId);
    verify(moduleRepository, times(1)).findById(moduleCode);
    verify(registrationRepository, never()).save(any(Registration.class));
  }

  /**
   * Tests the registerModule method updates an existing registration.
   */
  @Test
  public void testRegisterModuleUpdateExistingRegistration() {
    // Arrange
    Long studentId = 1L;
    String moduleCode = "COMP0010";

    // Mocking student, module, and registration
    Student mockStudent = mock(Student.class);
    Module mockModule = mock(Module.class);
    Registration mockRegistration = mock(Registration.class);

    when(studentRepository.findById(studentId)).thenReturn(Optional.of(mockStudent));
    when(moduleRepository.findById(moduleCode)).thenReturn(Optional.of(mockModule));
    when(registrationRepository.findByStudentAndModule(mockStudent, mockModule))
        .thenReturn(Optional.of(mockRegistration));
    when(registrationRepository.save(mockRegistration)).thenReturn(mockRegistration);

    // Act
    Registration result = registrationService.registerModule(studentId, moduleCode);

    // Assert
    assertEquals(mockRegistration, result,
        "The existing registration should be saved and returned.");
  }



}
