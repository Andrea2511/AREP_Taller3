package co.edu.eci.arep.appServer;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/")
    public String index(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "Greetings from Andrea Torres!";
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello, %s! You are visitor number %d.", name, counter.incrementAndGet());
    }

    @GetMapping("/pi")
    //soporte retorno String y solo recibe un parametro String
    public static String pi(@RequestParam(value = "name", defaultValue = "World") String name) {
        return Double.toString(Math.PI);
    }
}

