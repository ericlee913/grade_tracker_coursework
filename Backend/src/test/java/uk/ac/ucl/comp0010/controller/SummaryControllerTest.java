package uk.ac.ucl.comp0010.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import uk.ac.ucl.comp0010.dto.SummaryDto;
import uk.ac.ucl.comp0010.service.SummaryService;

/**
 * Unit tests for SummaryController class.
 */
@WebMvcTest(SummaryController.class)
public class SummaryControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private SummaryService summaryService;

  /**
   * Tests the getSummaryData method returns summary data of total students, modules, and overall
   * average grade.
   *
   * @throws Exception when HTTP request fails
   */
  @Test
  @WithMockUser
  public void getSummaryData_ShouldReturnSummary() throws Exception {
    SummaryDto mockSummary = new SummaryDto(150, 30, 75.1);
    when(summaryService.getSummaryData()).thenReturn(mockSummary);

    mockMvc.perform(get("/summary").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(jsonPath("$.totalStudents").value(150))
        .andExpect(jsonPath("$.totalModules").value(30))
        .andExpect(jsonPath("$.overallGrade").value(75.1));
  }
}
