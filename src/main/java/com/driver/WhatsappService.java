package com.driver;

import java.util.List;

public class WhatsappService {
    public String createUser(String name, String mobile)throws RuntimeException {

        return WhatsappRepository.saveUser(name,mobile);
    }

    public Group createGroup(List<User> users) {

        return WhatsappRepository.saveGroup(users);

    }

    public int createMessage(String content) {
        return WhatsappRepository.saveMessage(content);
    }

    public int sendMessage(Message message, User sender, Group group)throws RuntimeException{
        //try{
        return WhatsappRepository.sendMessage(message,sender,group);
//        }
//        catch(Exception e){
//            return e.toString();
//        }
    }

    public String changeAdmin(User approver, User user, Group group) throws RuntimeException{
        return WhatsappRepository.changeAdmin(approver, user, group);
    }
}
