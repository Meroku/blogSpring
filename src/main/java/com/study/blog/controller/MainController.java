package com.study.blog.controller;

import com.study.blog.domain.Message;
import com.study.blog.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private MessageRepo messageRepo;

    @GetMapping("/")
    public String greeting(
            Model model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Model model) {

        Iterable<Message> messages = messageRepo.findAll();
        model.addAttribute("messages", messages);

        return "main";
    }

    @PostMapping("/main")
    public String add(@RequestParam String text, @RequestParam String tag, Model model) {
        Message message = new Message(text, tag);

        messageRepo.save(message);

        Iterable<Message> messages = messageRepo.findAll();

        model.addAttribute("messages", messages);

        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Model model) {
        Iterable<Message> messagesByTag;
        if(filter != null && filter.isEmpty()) {
            messagesByTag = messageRepo.findByTag(filter);
        } else {
            messagesByTag = messageRepo.findAll();
        }

        model.addAttribute("messages", messagesByTag);

        return "main";
    }
}