package Classes;

public class PublicUserFactory implements UserFactory{
    //PublicUser nesnesi oluşturur
    @Override
    public User createUser(String name, String surname, String userName, String password) {
        return new PublicUser(name, surname, userName, password);
    }
}
