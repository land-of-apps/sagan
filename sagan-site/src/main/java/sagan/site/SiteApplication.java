package sagan.site;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.lang.reflect.InvocationTargetException;

/**
 * The entry point for the Sagan web application.
 */
@SpringBootApplication
@EnableConfigurationProperties(SiteProperties.class)
public class SiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(SiteApplication.class, args);
    }

    public static String checkpoint(String filePath) {
        try {
            Object recorder = Class.forName("com.appland.appmap.record.Recorder").getMethod("getInstance").invoke(null);
            Boolean hasActiveSession = (Boolean) recorder.getClass().getMethod("hasActiveSession").invoke(recorder);
            if ( !hasActiveSession.booleanValue() ) {
                return "There's no AppMap recording in progress. Start an AppMap recording in order to take a snapshot.";
            }

            Object recording = recorder.getClass().getMethod("checkpoint").invoke(recorder);
            recording.getClass().getMethod("moveTo", String.class).invoke(recording, filePath);
            return filePath;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
