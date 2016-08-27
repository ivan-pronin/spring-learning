package com.epam.springadvanced.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.epam.springadvanced.entity.User;
import com.epam.springadvanced.service.UserService;
import com.epam.springadvanced.utils.ControllerUtils;

@Controller
public class UserServiceController
{
    private static final String USER_SERVICE = "user-service";
    private static final String USER_SERVICE_PATH = "/user-service";

    @Autowired
    private UserService userService;

    @RequestMapping(value = USER_SERVICE_PATH)
    public String openMainModelPage(final User user, final BindingResult bindingResult, final ModelMap model)
    {
        return USER_SERVICE;
    }

    @RequestMapping(value = USER_SERVICE_PATH, params = {"getUserById"})
    public String getUserById(final User user, final BindingResult bindingResult, final ModelMap model)
    {
        User foundUser = userService.getById(user.getId());
        ControllerUtils.addResultToModel(model, foundUser);
        return USER_SERVICE;
    }

    @RequestMapping(value = USER_SERVICE_PATH, params = {"getUserByEmail"})
    public String getUserByEmail(final User user, final BindingResult bindingResult, final ModelMap model)
    {
        User foundUser = userService.getUserByEmail(user.getEmail());
        ControllerUtils.addResultToModel(model, foundUser);
        return USER_SERVICE;
    }
}
