package co.edu.eci.arep.appServer;

@RestController
public class MathController {

    @GetMapping("/e")
    public static String e() {
        return Double.toString(Math.E);
    }
}
