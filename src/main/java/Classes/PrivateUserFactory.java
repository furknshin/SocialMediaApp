package Classes;

public class PrivateUserFactory implements UserFactory
{
    //PrivateUser nesnesi oluşturur
    @Override
    public User createUser(String name, String surname, String userName, String password) {
        return new PrivateUser(name, surname, userName, password);
    }
}
