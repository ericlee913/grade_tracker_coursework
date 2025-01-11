package uk.ac.ucl.comp0010.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.HashMap;
import java.util.Map;
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
import uk.ac.ucl.comp0010.model.Module;
import uk.ac.ucl.comp0010.model.Registration;
import uk.ac.ucl.comp0010.model.Student;
import uk.ac.ucl.comp0010.repository.ModuleRepository;
import uk.ac.ucl.comp0010.repository.StudentRepository;

/**
 * Unit tests for RegistrationController class.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class RegistrationControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private ModuleRepository moduleRepository;

  Student student;
  Module module;

  /**
   * Set up new student and module and saving to their respective repositories before each test.
   */
  @BeforeEach
  public void beforeEach() {
    student = new Student();
    student.setId(23628);;
    student.setFirstName("NumPy");
    student.setLastName("Pandas");
    student.setUserName("PanUmpy");
    student.setEmail("PanUmpy@gmail.com");
    student = studentRepository.save(student);
    module = new Module();
    module.setCode("COMP0010");
    module.setName("SoftwareEngineering");
    module.setMnc(true);
    module = moduleRepository.save(module);
  }

  /**
   * Tests the registerModule method to register student to module.
   *
   * @throws JsonProcessingException when JSON cannot be processed
   * @throws Exception when HTTP request fails
   */
  @Test
  public void registerModuleTest() throws JsonProcessingException, Exception {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("student_id", student.getId());
    params.put("module_code", module.getCode());

    MvcResult action = mockMvc.perform(MockMvcRequestBuilders.post("/registrations/registerModule")
        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(params)))
        .andReturn();
    assertEquals(HttpStatus.OK.value(), action.getResponse().getStatus());

    Registration registration =
        objectMapper.readValue(action.getResponse().getContentAsString(), Registration.class);
    assertEquals(student.getId(), registration.getStudent().getId());
    assertEquals(student.getEmail(), registration.getStudent().getEmail());
    assertEquals(student.getFirstName(), registration.getStudent().getFirstName());
    assertEquals(student.getLastName(), registration.getStudent().getLastName());
    assertEquals(student.getUserName(), registration.getStudent().getUserName());
    assertEquals(module.getCode(), registration.getModule().getCode());
    assertEquals(module.getMnc(), registration.getModule().getMnc());
    assertEquals(module.getName(), registration.getModule().getName());
    assertNotNull(registration.getId());

  }

}
