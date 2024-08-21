package joseph.ciaravella.TeeTimeFinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.TimeZone;

@SpringBootApplication
// @EntityScan(basePackages = "joseph.ciaravella.TeeTimeFinder.model")
public class TeeTimeFinderApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		SpringApplication.run(TeeTimeFinderApplication.class, args);
	}

}
