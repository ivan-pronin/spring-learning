package epam.spring.university.util;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class ResourceUtils implements ResourceLoaderAware, IResourceUtils
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceUtils.class);
    private ResourceLoader resourceLoader;

    @Override
    public Resource getResourceFromFile(String fileName)
    {
        Resource resource = resourceLoader.getResource(ResourceLoader.CLASSPATH_URL_PREFIX + fileName);
        try
        {
            File file = resource.getFile();
            if (file.exists() && file.canRead())
            {
                return resource;
            }
            LOGGER.error("File {} doesn't exist or isn't readable", fileName);

        }
        catch (IOException e)
        {
            LOGGER.error("Failed to read file {}", fileName, e);
        }
        return null;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader)
    {
        this.resourceLoader = resourceLoader;
    }
}
