package com.epam.springadvanced.controller;

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
    private static final String EVENT_SERVICE = "event-service";
    private static final String EVENT_SERVICE_PATH = "/event-service";

    @Autowired
    private EventService eventService;

    @RequestMapping(value = EVENT_SERVICE_PATH)
    public String openMainModelPage(final Event user, final BindingResult bindingResult, final ModelMap model)
    {
        return EVENT_SERVICE;
    }

    @RequestMapping(value = EVENT_SERVICE_PATH, params = {"getEventById"})
    public String getEventById(final Event event, final BindingResult bindingResult, final ModelMap model)
    {
        Event foundevent = eventService.getById(event.getId());
        ControllerUtils.addResultToModel(model, foundevent);
        return EVENT_SERVICE;
    }

    @RequestMapping(value = EVENT_SERVICE_PATH, params = {"getEventByName"})
    public String getEventByName(final Event event, final BindingResult bindingResult, final ModelMap model)
    {
        Event foundevent = eventService.getByName(event.getName());
        ControllerUtils.addResultToModel(model, foundevent);
        return EVENT_SERVICE;
    }
}
