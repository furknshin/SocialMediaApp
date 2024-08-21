package Classes;

import java.util.ArrayList;

public class UserServiceImpl implements UserService
{
    //Yeni kullanıcının oluşturulmasını sağlar
    public ArrayList<String> users = new ArrayList<>();

    @Override
    public void addUser(String username) {
        users.add(username);
    }

    @Override
    public boolean isUsernameTaken(String username) {
        return users.contains(username);
    }
}
