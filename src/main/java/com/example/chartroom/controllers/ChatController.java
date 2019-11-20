package com.example.chartroom.controllers;

import com.example.chartroom.DAL.ActiveUsersRepository;
import com.example.chartroom.models.Login;
import com.example.chartroom.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.net.UnknownHostException;

@RestController
@EnableAsync
public class ChatController {
    @Autowired
    ActiveUsersRepository activeUsersRepository;

    /**
     * Login Page
     */
    @GetMapping("")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    /**
     * Chatroom Page
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam("username") String username) throws UnknownHostException {
        ModelAndView mv = new ModelAndView();
        mv.addObject("username", username);
        mv.setViewName("chat");

        return mv;
    }

    @MessageMapping("/message")
    @SendTo("/topic/message")
    public Message broadcastMessage(@RequestBody Message message) {
        return message;
    }

    @MessageMapping("/login")
    @SendTo("/topic/login")
    public Message activateUser(@RequestBody Login loginInfo, SimpMessageHeaderAccessor headerAccessor) {
        activeUsersRepository.ActivateUser(headerAccessor.getSessionId(), loginInfo.getUsername());
        Message msg = new Message();
        msg.setUsername(loginInfo.getUsername());
        msg.setOnlineCount(activeUsersRepository.getActiveUsers().size());
        return msg;
    }
}