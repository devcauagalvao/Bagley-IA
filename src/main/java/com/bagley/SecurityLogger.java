package com.bagley;

import java.io.*;

public class SecurityLogger {
    public void monitorLogs(String logFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(logFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("error") || line.contains("failed")) {
                    System.out.println("Alerta: possível ataque detectado - " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
