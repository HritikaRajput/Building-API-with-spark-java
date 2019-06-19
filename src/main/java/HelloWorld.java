import org.apache.log4j.BasicConfigurator;
import spark.Request;
import spark.Response;
import spark.Route;

import static spark.Spark.*;

public class HelloWorld {
    public static void main(String[] args) {
        BasicConfigurator.configure();
        get("/hello", new Route() {
            public Object handle(Request req, Response res) {
                return "Hello World";
            }
        });

        get("/hello/:name", new Route() {
            public Object handle(Request request, Response response) throws Exception {
                return  "Hello, "  + request.params(":name");

            }
        });
    }
}