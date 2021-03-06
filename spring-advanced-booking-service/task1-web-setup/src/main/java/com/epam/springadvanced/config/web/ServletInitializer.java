package com.epam.springadvanced.config.web;

import java.io.File;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

import com.epam.springadvanced.config.SpringConfiguration;

public class ServletInitializer extends AbstractDispatcherServletInitializer
{

    private int maxUploadSizeInMb = 5 * 1024 * 1024; // 5 MB

    public ServletInitializer()
    {
        super();
    }

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
        final AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(SpringConfiguration.class);
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
                maxUploadSizeInMb, maxUploadSizeInMb * 2, maxUploadSizeInMb / 2);
        registration.setMultipartConfig(multipartConfigElement);
        super.customizeRegistration(registration);
    }
}
