package co.edu.eci.arep.Taller;

@RestController
public class MathController {

    @GetMapping("/e")
    public static String e(String noUsada){
        return Double.toString(Math.E);
    }
}
