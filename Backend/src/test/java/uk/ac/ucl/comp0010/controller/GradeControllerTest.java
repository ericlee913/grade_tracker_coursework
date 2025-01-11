package uk.ac.ucl.comp0010.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.ac.ucl.comp0010.exception.NoRegistrationException;
import uk.ac.ucl.comp0010.model.Grade;
import uk.ac.ucl.comp0010.model.Module;
import uk.ac.ucl.comp0010.model.Registration;
import uk.ac.ucl.comp0010.model.Student;
import uk.ac.ucl.comp0010.repository.ModuleRepository;
import uk.ac.ucl.comp0010.repository.RegistrationRepository;
import uk.ac.ucl.comp0010.repository.StudentRepository;

/**
 * Unit test for GradeController class.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class GradeControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private ModuleRepository moduleRepository;

  @Autowired
  private RegistrationRepository registrationRepository;

  private Student student;

  private Module module;

  private Registration registration;

  /**
   * Set up new student and module and saving to their respective repositories before each test.
   */
  @BeforeEach
  public void setUp() {
    student = new Student();
    student.setId(1278);
    student.setFirstName("Alexasndar");
    student.setLastName("Zinchesnko");
    student.setUserName("alexazin");
    student.setEmail("alexandar@gmail.com");
    student = studentRepository.save(student);
    module = new Module();
    module.setCode("COMP0010");
    module.setName("SoftwareEngineering");
    module.setMnc(true);
    module = moduleRepository.save(module);
    Optional<Registration> existingRegistration =
        registrationRepository.findByStudentAndModule(student, module);
    existingRegistration.ifPresent(registrationRepository::delete);
    registration = new Registration();
    registration.setStudent(student);
    registration.setModule(module);
    registration = registrationRepository.save(registration);
  }

  /**
   * Tests the /grades/addGrade of GradeController.
   *
   * @throws JsonProcessingException when JSON cannot be processed
   * @throws Exception when HTTP request fails
   * @throws NoRegistrationException when user try to access grades for unregistered modules
   */
  @Test
  public void addGradeTest() throws JsonProcessingException, Exception, NoRegistrationException {
    Map<String, Object> params = new HashMap<>();
    params.put("student_id", student.getId());
    params.put("module_code", module.getCode());
    params.put("score", 40);

    MvcResult action = mockMvc.perform(MockMvcRequestBuilders.post("/grades/addGrade")
        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(params)))
        .andReturn();

    assertEquals(HttpStatus.OK.value(), action.getResponse().getStatus());

    Grade grade = objectMapper.readValue(action.getResponse().getContentAsString(), Grade.class);
    assertEquals(student.getId(), grade.getStudent().getId());
    assertEquals(student.getEmail(), grade.getStudent().getEmail());
    assertEquals(student.getFirstName(), grade.getStudent().getFirstName());
    assertEquals(student.getLastName(), grade.getStudent().getLastName());
    assertEquals(student.getUserName(), grade.getStudent().getUserName());
    assertEquals(40, grade.getScore());
    assertNotNull(grade.getId());

  }


  /**
   * Tests that NoRegistrationException throws when user try to access grades for unregistered
   * modules.
   *
   * @throws JsonProcessingException when JSON cannot be processed
   * @throws Exception when HTTP request fails
   * @throws NoRegistrationException when user try to access grades for unregistered modules
   */
  @Test
  public void addGradeNoRegistrationExceptionTest()
      throws JsonProcessingException, Exception, NoRegistrationException {
    Student newStudent = new Student();
    newStudent.setId(9999); // ID not previously created in setUp()
    newStudent.setFirstName("John");
    newStudent.setLastName("Doe");
    newStudent.setUserName("johndoe");
    newStudent.setEmail("john.doe@example.com");
    studentRepository.save(newStudent);

    Map<String, Object> params = new HashMap<>();
    params.put("student_id", newStudent.getId());
    params.put("module_code", module.getCode());
    params.put("score", 50);

    MvcResult action = mockMvc.perform(MockMvcRequestBuilders.post("/grades/addGrade")
        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(params)))
        .andReturn();

    assertEquals(HttpStatus.BAD_REQUEST.value(), action.getResponse().getStatus());
    assertEquals("No registration found for the student and module.",
        action.getResponse().getContentAsString().trim());
  }
}
