package uk.ac.ucl.comp0010.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import uk.ac.ucl.comp0010.model.Module;
import uk.ac.ucl.comp0010.model.Registration;
import uk.ac.ucl.comp0010.model.Student;

/**
 * Repository interface for Registration entities.
 */
public interface RegistrationRepository extends CrudRepository<Registration, Long> {
  /**
   * Method to find registration for a specific student and module.
   *
   * @param student - the student to find registration
   * @param module - the module to find registration
   * @return an Optional if there is a registration, otherwise empty
   */
  Optional<Registration> findByStudentAndModule(Student student, Module module);
}
