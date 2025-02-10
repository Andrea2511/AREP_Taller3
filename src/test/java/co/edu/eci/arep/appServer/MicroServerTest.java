package co.edu.eci.arep.appServer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MicroServerTest {

    @Test
    void testMathControllerE() {
        // Llamamos directamente al método e() del MathController
        String resultado = MathController.e();
        assertEquals("2.718281828459045", resultado, "El método e() debería retornar el valor de 'e'.");
    }

    @Test
    void testGreetingController() {
        GreetingController greetingController = new GreetingController();

        // Llamamos al método greeting() con un nombre específico
        String resultado = greetingController.greeting("Andrea");
        assertEquals("Hello, Andrea! You are visitor number 1.", resultado, "El método greeting() debería retornar 'Hola Andrea'.");
    }

    @Test
    void testGreetingControllerWithDefault() {
        GreetingController greetingController = new GreetingController();

        // Probamos el método sin parámetros (esto simula el valor por defecto "World")
        String resultado = greetingController.greeting("World");
        assertEquals("Hello, World! You are visitor number 1.", resultado, "El método greeting() debería retornar 'Hola World' por defecto.");
    }

    @Test
    void testMultipleVisitors() {
        GreetingController greetingController = new GreetingController();

        String resultado1 = greetingController.greeting("User1");
        String resultado2 = greetingController.greeting("User2");
        assertEquals("Hello, User2! You are visitor number 2.", resultado2);
    }

    @Test
    void testPiMethod() {
        String response = GreetingController.pi("anything");
        assertEquals("3.141592653589793", response);
    }

    @Test
    void testIndexMethod() {
        GreetingController greetingController = new GreetingController();
        String response = greetingController.index("Andrea");
        assertEquals("Greetings from Andrea Torres!", response);
    }
}



