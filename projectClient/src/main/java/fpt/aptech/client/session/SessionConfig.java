import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.SessionTrackingMode;
import java.util.EnumSet;

@Configuration
public class SessionConfig {

    @Bean
    public ServletContextInitializer servletContextInitializer() {
        return new ServletContextInitializer() {
            @Override
            public void onStartup(ServletContext servletContext) throws ServletException {
                // Disable URL-based session tracking by setting session tracking mode to COOKIE
                servletContext.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE));
            }
        };
    }
}