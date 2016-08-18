package epam.spring.university.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

@Component
public class JsonUtils implements IJsonUtils
{
    private final ObjectMapper mapper = new ObjectMapper();

    public JsonUtils()
    {

    }

    public JsonUtils(PropertyNamingStrategy namingStrategy)
    {
        mapper.setPropertyNamingStrategy(namingStrategy);
    }

    @Override
    public String toJson(Object object)
    {
        try
        {
            return mapper.writeValueAsString(object);
        }
        catch (IOException e)
        {
            throw new JsonProcessingException(e.getMessage(), e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T toObject(String json, Class<?> clazz)
    {
        try
        {
            return (T) mapper.readValue(json, clazz);
        }
        catch (IOException e)
        {
            throw new JsonProcessingException(e.getMessage(), e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> toObjectList(String json, Class<?> clazz)
    {
        try
        {
            return (List<T>) mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        }
        catch (IOException e)
        {
            throw new JsonProcessingException(e.getMessage(), e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T toObject(InputStream json, Class<?> clazz)
    {
        try
        {
            return (T) mapper.readValue(json, clazz);
        }
        catch (IOException e)
        {
            throw new JsonProcessingException(e.getMessage(), e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> toObjectList(InputStream json, Class<?> clazz)
    {
        try
        {
            return (List<T>) mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        }
        catch (IOException e)
        {
            throw new JsonProcessingException(e.getMessage(), e);
        }
    }

    public void setNamingStrategy(PropertyNamingStrategy namingStrategy)
    {
        mapper.setPropertyNamingStrategy(namingStrategy);
    }
}
