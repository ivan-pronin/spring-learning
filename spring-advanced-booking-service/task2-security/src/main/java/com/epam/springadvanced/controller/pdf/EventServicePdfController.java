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
package com.epam.springadvanced.controller.pdf;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epam.springadvanced.entity.Event;
import com.epam.springadvanced.service.EventService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
public class EventServicePdfController
{
    private static final String EVENTS_REPORT_TEMPLATE = "jasper/eventsReportTemplate.jrxml";

    @Autowired
    private EventService eventService;

    public EventServicePdfController()
    {
        super();
    }

    @RequestMapping(value = "/event-service",
            params = {"getAllEventsAsPdf"},
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public void getAllEventsAsPdf(final Event event, final BindingResult bindingResult, final ModelMap model,
            HttpServletResponse response) throws JRException, IOException
    {
        List<Event> events = (List<Event>) eventService.getAll();
        InputStream template = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(EVENTS_REPORT_TEMPLATE);
        JasperReport report = JasperCompileManager.compileReport(template);
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(events);
        Map<String, Object> parameters = new HashMap<>();
        try
        {
            JasperPrint print = JasperFillManager.fillReport(report, parameters, beanColDataSource);
            response.setContentType("application/octet-stream");
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", "eventsReportTemplate" + ".pdf");
            response.setHeader(headerKey, headerValue);
            JasperExportManager.exportReportToPdfStream(print, response.getOutputStream());
        }
        catch (Exception e)
        {
            System.out.println("Failed to generate report! ");
            e.printStackTrace();
        }
    }
}
