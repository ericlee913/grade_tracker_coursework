package uk.ac.ucl.comp0010.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import uk.ac.ucl.comp0010.model.Grade;
import uk.ac.ucl.comp0010.model.Module;
import uk.ac.ucl.comp0010.model.Student;

/**
 * A rest configuration class for revealing the identifier of Student, Module, and Grade object over
 * REST.
 */
@Configuration
public class RestConfiguration implements RepositoryRestConfigurer {

  /**
   * A method that exposes the Ids of Student, Module, Grade.
   *
   * @param config - a RepositoryRestConfiguration object
   * @param cors - a CorsRegistry object
   */
  @Override
  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config,
      CorsRegistry cors) {
    config.exposeIdsFor(Student.class);
    config.exposeIdsFor(Module.class);
    config.exposeIdsFor(Grade.class);
  }
}
