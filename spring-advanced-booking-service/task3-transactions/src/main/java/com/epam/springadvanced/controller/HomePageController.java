package com.epam.springadvanced.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.epam.springadvanced.entity.Booking;

@Controller
public class HomePageController
{
    @RequestMapping(value = "/")
    public String openMainModelPage(final Booking booking, final BindingResult bindingResult, final ModelMap model)
    {
        return "index";
    }
}
