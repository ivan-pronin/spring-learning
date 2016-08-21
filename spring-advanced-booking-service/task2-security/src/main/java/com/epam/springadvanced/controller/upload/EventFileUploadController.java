package com.epam.springadvanced.controller.upload;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.epam.springadvanced.entity.Event;
import com.epam.springadvanced.entity.FileUpload;
import com.epam.springadvanced.repository.EventRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class EventFileUploadController
{

    @Autowired
    private EventRepository eventRepository;

    @RequestMapping(value = "/upload")
    public String openMainModelPage(final FileUpload fileUpload, final BindingResult bindingResult,
            final ModelMap model)
    {
        return "upload";
    }

    @RequestMapping(value = "/upload", params = {"uploadEvents"}, method = RequestMethod.POST)
    public String uploadEvents(@RequestParam("file") MultipartFile file) throws IOException
    {
        if (!file.isEmpty())
        {
            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();
            InputStream in = file.getInputStream();
            List<Event> events = mapper.readValue(in, new TypeReference<List<Event>>()
            {
            });
            System.out.println(events);
            for (Event event : events)
            {
                eventRepository.save(event);
            }
            return "upload";
        }

        return "upload";
    }
}
