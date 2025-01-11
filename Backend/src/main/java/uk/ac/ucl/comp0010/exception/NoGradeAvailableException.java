package uk.ac.ucl.comp0010.exception;

/**
 * A class that throws NoGradeAvailableException if there is no grade available at all or not
 * available for a specific module.
 */
public class NoGradeAvailableException extends Exception {

  private static final long serialVersionUID = 123456789L;

  /**
   * A constructor for NoGradeAvailableException.
   *
   * @param message that explains the reason for exception being thrown
   */
  public NoGradeAvailableException(String message) {
    super(message);
  }
}
