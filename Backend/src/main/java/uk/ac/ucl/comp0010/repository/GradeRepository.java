package uk.ac.ucl.comp0010.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import uk.ac.ucl.comp0010.model.Grade;
import uk.ac.ucl.comp0010.model.Module;
import uk.ac.ucl.comp0010.model.Student;

/**
 * Repository interface for Grade entities.
 */
public interface GradeRepository extends CrudRepository<Grade, Long> {
  /**
   * Method for calculating average grade.
   *
   * @return average grade
   */
  @Query("SELECT AVG(g.score) FROM Grade g")
  public Double calculateAverageGrade();

  /**
   * Method to find grade for a specific student and module.
   *
   * @param student - the student to find grade
   * @param module - the module to find grade
   * @return an Optional if there is a grade, otherwise empty
   */
  Optional<Grade> findByStudentAndModule(Student student, Module module);
}
