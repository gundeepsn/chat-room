package com.example.chartroom.DAL;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ActiveUsersRepository {
    private Map<String, String> activeUsers;

    public ActiveUsersRepository() {
        this.activeUsers = new HashMap<String, String>();
    }

    public Map<String, String> getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(Map<String, String> activeUsers) {
        this.activeUsers = activeUsers;
    }

    public void ActivateUser(String sessionId, String username) {
        this.activeUsers.put(sessionId, username);
    }

    public String getUsername(String sessionId){
        return activeUsers.get(sessionId);
    }

    public void DisconnectedUser(String sessionId) {
        this.activeUsers.remove(sessionId);
    }
}
