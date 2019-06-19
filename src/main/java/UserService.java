//Declaring CRUD operations for the User Entity

import java.util.Collection;

public interface UserService {

    //1
    public void addUser(User user);

    //2
    public Collection<User> getUsers();
    public User getUser(String id);

    //3
    public User editUser(User user)
        throws UserException;

    //4
    public void deleteUser(String id);

    //5
    public boolean userExist(String id);

}
