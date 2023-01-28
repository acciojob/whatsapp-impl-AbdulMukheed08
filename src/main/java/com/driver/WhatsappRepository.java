package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.
    private HashMap<Group, List<User>> groupUserMap;
    private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private  HashMap<Group, User> adminMap;
    private  HashSet<String> userMobile;
    private  int customGroupCount;
    private int messageId=0;
    private int groupInd = 0;

    public WhatsappRepository(){
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userMobile = new HashSet<>();
        this.customGroupCount = 0;
        this.messageId = 0;
    }

    public String saveUser(String name, String mobile) {
        if(userMobile.contains(mobile)){
            throw new RuntimeException("User already exists");
        }
        userMobile.add(mobile);
        return "SUCCESS";
    }


    public Group saveGroup(List<User> users) {
        Group g;
        if(users.size()==2){
            g = new Group(users.get(1).getName(),2);
        }
        else {
            this.customGroupCount++;
            g = new Group("Group " + String.valueOf(this.customGroupCount), users.size());
        }
        groupUserMap.put(g,users);
        this.adminMap.put(g,users.get(0));
        return g;
    }

    public int saveMessage(String content) {
        this.messageId++;
        Message m = new Message(this.messageId,content);
        return this.messageId;
    }

    public int sendMessage(Message message, User sender, Group group) {
        if(!groupUserMap.containsKey(group)){
            throw new RuntimeException("Group does not exist");
        }
        for(User user : groupUserMap.get(group)){
            if(Objects.equals(user,sender)){
                List<Message> mesList = new ArrayList<>();
                if(groupMessageMap.containsKey(group)){
                    mesList = groupMessageMap.get(group);
                }
                mesList.add(message);
                groupMessageMap.put(group,mesList);
                return mesList.size();
            }
        }
        throw new RuntimeException("You are not allowed to send message");
    }

    public String changeAdmin(User approver, User user, Group group) {
        if(!groupUserMap.containsKey(group)){
            throw new RuntimeException("Group does not exist");
        }
        User admin = adminMap.get(group);
        if(!Objects.equals(admin,approver)){
            throw new RuntimeException("Approver does not have rights");
        }
        for(User groupMember : groupUserMap.get(group)){
            if(Objects.equals(groupMember,user)){
                adminMap.put(group,user);
                return "SUCCESS";
            }
        }
        throw new RuntimeException("User is not a participant");
    }
}
