package com.epam.springadvanced.controller.upload;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(EventFileUploadController.class);

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
            for (Event event : events)
            {
                eventRepository.save(event);
                LOGGER.info("Saved event: {} with id: {}", event, event.getId());
            }
            return "upload";
        }
        return "upload";
    }
}
