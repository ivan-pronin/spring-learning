package com.epam.springadvanced.converter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.epam.springadvanced.entity.Ticket;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class HttpTicketPdfMessageConverter implements HttpMessageConverter<Ticket>
{
    private static final String TICKETS_REPORT_TEMPLATE = "jasper/ticketsReportTemplate.jrxml";
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpTicketPdfMessageConverter.class);

    private List<MediaType> supportedMediaTypes = Collections.emptyList();

    @SuppressWarnings("unused")
    private HttpTicketPdfMessageConverter()
    {
    }

    public HttpTicketPdfMessageConverter(List<MediaType> supportedMediaTypes)
    {
        this.supportedMediaTypes = supportedMediaTypes;
    }

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType)
    {
        return false;
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType)
    {
        return isTicketClass(clazz) && canWrite(mediaType);
    }

    @Override
    public List<MediaType> getSupportedMediaTypes()
    {
        return supportedMediaTypes;
    }

    @Override
    public Ticket read(Class<? extends Ticket> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException
    {
        throw new UnsupportedOperationException("We cannot read PDF streams of Ticket");        
    }

    @Override
    public void write(Ticket t, MediaType contentType, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException
    {
        InputStream template = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(TICKETS_REPORT_TEMPLATE);
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(Arrays.asList(t));
        Map<String, Object> parameters = new HashMap<>();
        try
        {
            JasperReport report = JasperCompileManager.compileReport(template);
            OutputStream out = outputMessage.getBody();
            JasperPrint print = JasperFillManager.fillReport(report, parameters, beanColDataSource);
            JasperExportManager.exportReportToPdfStream(print, out);
        }
        catch (Exception e)
        {
            LOGGER.error("Failed to generate PDF output stream: {}", e);
        }
    }

    protected boolean canWrite(MediaType mediaType)
    {
        if (mediaType == null || MediaType.ALL.equals(mediaType))
        {
            return true;
        }
        for (MediaType supportedMediaType : getSupportedMediaTypes())
        {
            if (supportedMediaType.isCompatibleWith(mediaType))
            {
                return true;
            }
        }
        return false;
    }

    private boolean isTicketClass(Class<?> clazz)
    {
        return clazz.isAssignableFrom(Ticket.class);
    }
}
