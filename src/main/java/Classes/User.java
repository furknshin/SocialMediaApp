package Classes;

import java.util.ArrayList;

public interface User
{

    void addFriend(String userName);

    String getName();
    String getSurname();
    String getUserName();
    String getPassword();
    ArrayList<String> getFriends();

    void setName(String string);
    void setSurname(String string);
    void setUserName(String string);
    void setPassword(String string);
    void setFriends(ArrayList<String> friends);

}
