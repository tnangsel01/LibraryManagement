import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ErrorLog {
    private static final String LOG_FILE_PATH = "error_log.txt";

    public static void logException(Exception e) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE_PATH, true))) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            writer.println("Timestamp: " + timestamp);
            writer.println("Exception: " + e.getClass().getName());
            writer.println("Message: " + e.getMessage());

            // Print the stack trace to the file
            e.printStackTrace(writer);
            writer.println(); // Add a newline for better readability in the log file

            writer.println("------------------------------------------------------------");

        } catch (IOException ioException) {
            ioException.printStackTrace();
            ErrorLog.logException(ioException);
        }
    }
}

