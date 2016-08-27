package com.epam.springadvanced.controller.pdf;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Autowired
    private BookingService bookingService;

    @Autowired
    private EventService eventService;

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
        JasperPrint print = JasperFillManager.fillReport(report, parameters, beanColDataSource);
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", "ticketsReportTemplate" + ".pdf");
        response.setHeader(headerKey, headerValue);
        JasperExportManager.exportReportToPdfStream(print, response.getOutputStream());
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
