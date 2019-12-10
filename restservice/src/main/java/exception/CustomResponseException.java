package exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "Error encountred")
public class CustomResponseException extends RuntimeException {
	
  private static final long serialVersionUID = 1L;
  
  /**
   * Methods to manage customs exceptions response
   */
    public CustomResponseException(String message) {
        super(message);
    }
    public CustomResponseException(String message, Throwable cause) {
        super(message, cause);
    }
}
