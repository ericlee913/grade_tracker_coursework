package uk.ac.ucl.comp0010.repository;

import org.springframework.data.repository.CrudRepository;
import uk.ac.ucl.comp0010.model.Student;

/**
 * Repository interface for Student entities.
 */
public interface StudentRepository extends CrudRepository<Student, Long> {

}
