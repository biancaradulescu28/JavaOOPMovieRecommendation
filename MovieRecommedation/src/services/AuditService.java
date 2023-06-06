package services;


import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditService
{
    private static AuditService auditService;

    FileWriter writer;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private AuditService() {
        try {
            String path = "C:\\Bianca\\SC\\Facultate\\an 2\\sem 2\\PAO\\PAO\\MovieRecommedation\\src\\Audit.csv";
            this.writer = new FileWriter(path);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static AuditService getInstance()
    {
        if (auditService == null) auditService = new AuditService();

        return auditService;
    }

    public void logAction(String action) throws IOException
    {
        writer.append(action);
        writer.append(" - ");
        writer.append(formatter.format(LocalDateTime.now()));
        writer.append("\n");
        writer.flush();
    }
}