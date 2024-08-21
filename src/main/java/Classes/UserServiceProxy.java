package Classes;

public class UserServiceProxy implements UserService
{
    //Aynı userName kullanılmış mı diye kontrol eder
    public UserService userService = new UserServiceImpl();
    public boolean control = false;

    @Override
    public void addUser(String username) {
        if (!userService.isUsernameTaken(username)) {
            userService.addUser(username);
        } else {
            control = true;
        }
    }

    @Override
    public boolean isUsernameTaken(String username) {
        return userService.isUsernameTaken(username);
    }
}
