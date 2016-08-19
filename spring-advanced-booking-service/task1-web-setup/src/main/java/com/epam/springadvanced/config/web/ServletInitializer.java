package com.epam.springadvanced.config.web;

import javax.servlet.Filter;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

public class ServletInitializer extends AbstractDispatcherServletInitializer
{

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

}
