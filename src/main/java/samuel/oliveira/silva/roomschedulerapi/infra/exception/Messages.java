package samuel.oliveira.silva.roomschedulerapi.infra.exception;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class reads messages.properties values and maps.
 */
public class Messages {

  private static final String BASENAME = "messages";

  private static final ResourceBundle resourceBundle =
      ResourceBundle.getBundle(BASENAME, Locale.getDefault());

  /**
   * Gets the personalized message of the key received.
   *
   * @param key key representing the exception code
   * @param params values to substitute placeholders in the message
   * @return the personalized exception message
   */
  public static String get(String key, Object... params) {
    try {
      var message = resourceBundle.getString(key);
      return MessageFormat.format(message, params);
    } catch (Exception e) {
      return key;
    }
  }

  /**
   * Gets the personalized message of the key received, or null if the key does not exist.
   *
   * @param key key representing the exception code
   * @param params values to substitute placeholders in the message
   * @return the personalized exception message
   */
  public static String getOrNull(String key, Object... params) {
    try {
      var message = resourceBundle.getString(key);
      return MessageFormat.format(message, params);
    } catch (Exception e) {
      return null;
    }
  }
}
