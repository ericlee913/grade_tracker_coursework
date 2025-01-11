package uk.ac.ucl.comp0010.exception;

/**
 * A class that throws NoRegistrationException if a user try to access grades for unregistered
 * modules.
 */
public class NoRegistrationException extends Exception {

  private static final long serialVersionUID = 123456789L;

  /**
   * A constructor for NoRegistrationException.
   *
   * @param message that explains the reason for exception being thrown
   */
  public NoRegistrationException(String message) {
    super(message);
  }
}
