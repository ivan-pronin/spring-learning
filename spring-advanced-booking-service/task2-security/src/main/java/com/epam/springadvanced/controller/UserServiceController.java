/*
 * =============================================================================
 *
 * Copyright (c) 2011-2016, The THYMELEAF team (http://www.thymeleaf.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * =============================================================================
 */
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
    @Autowired
    private UserService userService;

    public UserServiceController()
    {
        super();
    }

    @RequestMapping(value = "/user-service")
    public String openMainModelPage(final User user, final BindingResult bindingResult, final ModelMap model)
    {
        return "user-service";
    }

    @RequestMapping(value = "/user-service", params = {"getUserById"})
    public String getUserById(final User user, final BindingResult bindingResult, final ModelMap model)
    {
        User foundUser = userService.getById(user.getId());
        ControllerUtils.addResultToModel(model, foundUser);
        return "user-service";
    }

    @RequestMapping(value = "/user-service", params = {"getUserByEmail"})
    public String getUserByEmail(final User user, final BindingResult bindingResult, final ModelMap model)
    {
        User foundUser = userService.getUserByEmail(user.getEmail());
        ControllerUtils.addResultToModel(model, foundUser);
        return "user-service";
    }
}
