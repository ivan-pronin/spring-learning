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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.epam.springadvanced.entity.Event;
import com.epam.springadvanced.service.EventService;
import com.epam.springadvanced.utils.ControllerUtils;

@Controller
public class EventServiceController
{
    @Autowired
    private EventService eventService;

    public EventServiceController()
    {
        super();
    }

    @RequestMapping(value = "/event-service")
    public String openMainModelPage(final Event user, final BindingResult bindingResult, final ModelMap model)
    {
        return "event-service";
    }

    @RequestMapping(value = "/event-service", params = {"getEventById"})
    public String getEventById(final Event event, final BindingResult bindingResult, final ModelMap model)
    {
        Event foundEvent = eventService.getById(event.getId());
        ControllerUtils.addResultToModel(model, foundEvent);
        return "event-service";
    }

    @RequestMapping(value = "/event-service", params = {"getEventByName"})
    public String getEventByName(final Event event, final BindingResult bindingResult, final ModelMap model)
    {
        Event foundEvent = eventService.getByName(event.getName());
        ControllerUtils.addResultToModel(model, foundEvent);
        return "event-service";
    }
    
    @RequestMapping(value = "/event-service", params = {"getAllEvents"})
    public String getAllEvents(final Event event, final BindingResult bindingResult, final ModelMap model)
    {
        List<Event> foundEvents = (List<Event>) eventService.getAll();
        ControllerUtils.addResultToModel(model, foundEvents);
        return "event-service";
    }
}
