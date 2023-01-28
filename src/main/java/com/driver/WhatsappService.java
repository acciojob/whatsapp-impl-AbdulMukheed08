package com.driver;

import java.util.List;

public class WhatsappService {

    WhatsappRepository repo = new WhatsappRepository();
    public String createUser(String name, String mobile)throws RuntimeException {

        return repo.saveUser(name,mobile);
    }

    public Group createGroup(List<User> users) {

        return repo.saveGroup(users);

    }

    public int createMessage(String content) {
        return repo.saveMessage(content);
    }

    public int sendMessage(Message message, User sender, Group group)throws RuntimeException{
        //try{
        return repo.sendMessage(message,sender,group);
//        }
//        catch(Exception e){
//            return e.toString();
//        }
    }

    public String changeAdmin(User approver, User user, Group group) throws RuntimeException{
        return repo.changeAdmin(approver, user, group);
    }
}
