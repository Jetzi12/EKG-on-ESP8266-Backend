package hello;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();



	@RequestMapping("/greeting")
	public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
		return new Greeting(counter.incrementAndGet(),
							String.format(template, name));
	}

	@CrossOrigin
	@RequestMapping(value = "/ekg", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Measurement ekg(@RequestParam(value="name", defaultValue="World") String name) {
		PiontRandomGenerator piontRandomGenerator = new PiontRandomGenerator();

		return piontRandomGenerator.generate(1000);
	}

	@CrossOrigin
	@RequestMapping(value = "/ekgESP", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Measurement ekgESP(@RequestParam(value="name", defaultValue="World") String name) {
		ESPConnection espConnection = new ESPConnection();
		return espConnection.getData();
	}

	@CrossOrigin
	@RequestMapping(value = "/ekgESP5min", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Measurement ekgESP5min(@RequestParam(value="name", defaultValue="World") String name) {
		ESPConnection espConnection = new ESPConnection();
		espConnection.dlugoscPomiaru=100;
		return espConnection.getData();
	}
}
