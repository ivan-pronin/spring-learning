package com.epam.springadvanced.config.web;

import java.io.File;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

public class ServletInitializer extends AbstractDispatcherServletInitializer
{
    private static final int MAX_UPLOAD_FILE_MB = 5 * 1024 * 1024; // 5 MB

    @Override
    protected WebApplicationContext createServletApplicationContext()
    {
        final AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(WebConfiguration.class);
        return context;
    }

    @Override
    protected WebApplicationContext createRootApplicationContext()
    {
        return null;
    }

    @Override
    protected String[] getServletMappings()
    {
        return new String[] {"/"};
    }

    @Override
    protected Filter[] getServletFilters()
    {
        final CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding(WebConfiguration.CHARACTER_ENCODING);
        encodingFilter.setForceEncoding(true);
        return new Filter[] {encodingFilter};
    }

    @Override
    protected void customizeRegistration(Dynamic registration)
    {
        File uploadDirectory = new File("uploadDirectory");
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(uploadDirectory.getAbsolutePath(),
                MAX_UPLOAD_FILE_MB, MAX_UPLOAD_FILE_MB * 2, MAX_UPLOAD_FILE_MB / 2);
        registration.setMultipartConfig(multipartConfigElement);
        super.customizeRegistration(registration);
    }
}
