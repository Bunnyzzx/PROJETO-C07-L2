package factory;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SQLLogger {

    private static final String SQL_FILE_PATH = "C07-PROJETO.sql";
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void log(String sql) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(SQL_FILE_PATH, true))) {
            pw.println("-- [" + LocalDateTime.now().format(FMT) + "] " + sql + ";");
        } catch (IOException e) {
            System.err.println("Aviso: nao foi possivel registrar no arquivo SQL: " + e.getMessage());
        }
    }
}
