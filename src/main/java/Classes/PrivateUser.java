package Classes;

import java.util.ArrayList;

public class PrivateUser implements User
{
    String name;
    String surname;
    String userName;
    String password;

    ArrayList<String> friends = new ArrayList<>();

    public PrivateUser(String name, String surname, String userName, String password)
    {
        this.name = name;
        this.surname = surname;
        this.userName = userName;
        this.password = password;
    }
    public void addFriend(String userName)
    {
        friends.add(userName);
    }

    @Override
    public String toString() {
        return "" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", friends=" + friends +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }
}
