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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epam.springadvanced.entity.Booking;
import com.epam.springadvanced.entity.Event;
import com.epam.springadvanced.entity.Ticket;
import com.epam.springadvanced.entity.TicketReport;
import com.epam.springadvanced.service.BookingService;
import com.epam.springadvanced.service.EventService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
public class BookingServicePdfController
{
    private static final String TICKETS_REPORT_TEMPLATE = "jasper/ticketsReportTemplate.jrxml";
    private static final String EVENT_1_DATE = "2016-11-03T21:00:00";
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingServicePdfController.class);

    @Autowired
    private BookingService bookingService;

    @Autowired
    private EventService eventService;

    public BookingServicePdfController()
    {
        super();
    }

    @RequestMapping(value = "/booking-service",
            params = {"getEventTicketsPdfView"},
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public void getEventTicketsPdfView(final Booking booking, final BindingResult bindingResult, final ModelMap model,
            HttpServletResponse response) throws JRException, IOException
    {
        List<TicketReport> tickets = convertToTicketReport(getTickets(booking));
        InputStream template = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(TICKETS_REPORT_TEMPLATE);
        JasperReport report = JasperCompileManager.compileReport(template);
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(tickets);
        Map<String, Object> parameters = new HashMap<>();
        try
        {
            JasperPrint print = JasperFillManager.fillReport(report, parameters, beanColDataSource);
            response.setContentType("application/octet-stream");
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", "ticketsReportTemplate" + ".pdf");
            response.setHeader(headerKey, headerValue);
            JasperExportManager.exportReportToPdfStream(print, response.getOutputStream());
        }
        catch (Exception e)
        {
            LOGGER.error("Failed to generate report!", e);
        }
    }

    private List<Ticket> getTickets(final Booking booking)
    {
        Event event = eventService.getById(booking.getEventId());
        return (List<Ticket>) bookingService.getTicketsForEvent(event, LocalDateTime.parse(EVENT_1_DATE));
    }

    private List<TicketReport> convertToTicketReport(List<Ticket> tickets)
    {
        List<TicketReport> report = new ArrayList<>();
        for (Ticket t : tickets)
        {
            report.add(new TicketReport(t.getId(), t.getPrice(), t.getSeat().getNumber(), t.getEvent().getId(),
                    t.getEvent().getName()));
        }
        return report;
    }
}
