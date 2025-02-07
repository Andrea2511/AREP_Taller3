package co.edu.eci.arep.Taller;

@RestController
public class GreetingController {
    @GetMapping("/greeting")
    //soporte retorno String y solo recibe un parametro String
    public static String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "Hola " + name;
    }

    @GetMapping("/pi")
    //soporte retorno String y solo recibe un parametro String
    public static String pi(@RequestParam(value = "name", defaultValue = "World") String name) {
        return Double.toString(Math.PI);
    }
}

