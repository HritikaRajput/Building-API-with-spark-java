import com.google.gson.Gson;
import org.apache.log4j.BasicConfigurator;
import spark.Request;
import spark.Response;
import spark.Route;

import static spark.Spark.*;

public class SparkRest {
    public static void main(String[] args) {
        BasicConfigurator.configure();
        final UserService userService= new UserServiceMapImpl();

        post("/users", new Route() {
            public Object handle(Request req, Response res) throws Exception {
                res.type("application/json");
                User user = new Gson().fromJson(req.body(), User.class);

                userService.addUser(user);
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
            }
        });

        get("/users", new Route(){
            public Object handle(Request req,Response res) throws Exception {
                res.type("application/json");
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,new Gson().toJsonTree(userService.getUsers())));
            }
        });

        get("/users/:id",(request,response)->{
            response.type("application/json");
            User ab=userService.getUser(request.params(":id"));

            if(ab !=null)
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,new Gson().toJson(userService.getUser(request.params(":id")))));
            else
                 return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,new Gson().toJson("User does not exist")));

        });

        put("/users/:id", new Route() {
            public Object handle(Request request, Response response) throws Exception {
                response.type("application/json");

                User toEdit = new Gson().fromJson(request.body(), User.class);
                User editedUser = userService.editUser(toEdit);

                if (editedUser != null) {
                    return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(editedUser)));
                } else {
                    return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, new Gson().toJson("User not found or error in edit")));
                }
            }
        });

        delete("/users/:id", new Route() {
            public Object handle(Request request, Response response) throws Exception {
                response.type("application/json");
                userService.deleteUser(request.params(":id"));
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,"User Deleted" ));
            }
        });

        options("/users/:id", new Route() {
            public Object handle(Request request, Response response) throws Exception {
                response.type("application/json");
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, (userService.userExist(request.params(":id"))) ? "Users exists": "User does not exist"));
            }
        });

    }
}
