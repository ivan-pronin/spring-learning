package epam.spring.university.service.impl;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import epam.spring.university.util.IJsonUtils;
import epam.spring.university.util.IResourceUtils;

public abstract class AbstractJsonServiceImpl
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractJsonServiceImpl.class);

    private String dataFileName;

    private IResourceUtils resourceUtils;

    private IJsonUtils jsonUtils;

    private Class<?> domainClass;

    protected AbstractJsonServiceImpl()
    {
    }

    protected AbstractJsonServiceImpl(String dataFileName, IResourceUtils resourceUtils, IJsonUtils jsonUtils,
            Class<?> domainClazz)
    {
        this.dataFileName = dataFileName;
        this.resourceUtils = resourceUtils;
        this.jsonUtils = jsonUtils;
        this.domainClass = domainClazz;
    }

    public <T> Set<T> getAll()
    {
        try
        {
            Resource resource = resourceUtils.getResourceFromFile(dataFileName);
            List<T> objects = jsonUtils.toObjectList(resource.getInputStream(), domainClass);
            return new HashSet<T>(objects);
        }
        catch (IOException e)
        {
            LOGGER.error("Failed to read data from file: " + dataFileName, e);
        }
        return null;
    }

    public String getDataFileName()
    {
        return dataFileName;
    }

    public IResourceUtils getResourceUtils()
    {
        return resourceUtils;
    }

    public IJsonUtils getJsonUtils()
    {
        return jsonUtils;
    }

    public Class<?> getDomainClass()
    {
        return domainClass;
    }

    protected abstract void init();
}
