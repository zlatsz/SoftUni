package store.error;

public class BaseException extends RuntimeException {

  private int status;

  public BaseException(int status, String message) {
    super(message);
    this.status = status;
  }

  public BaseException(int status) {
  }

  public int getStatus() {
    return this.status;
  }

  public void setStatus(int status) {
    this.status = status;
  }
}
