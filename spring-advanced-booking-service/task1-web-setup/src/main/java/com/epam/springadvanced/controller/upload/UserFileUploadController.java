package com.epam.springadvanced.controller.upload;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.epam.springadvanced.entity.User;
import com.epam.springadvanced.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class UserFileUploadController
{

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/upload", params = {"uploadUsers"}, method = RequestMethod.POST)
    public String uploadEvents(@RequestParam("file") MultipartFile file) throws IOException
    {
        if (!file.isEmpty())
        {
            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();
            InputStream in = file.getInputStream();
            List<User> users = mapper.readValue(in, new TypeReference<List<User>>()
            {
            });
            for (User user : users)
            {
                userRepository.save(user);
            }
            return "upload";
        }

        return "upload";
    }
}
