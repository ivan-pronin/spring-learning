package epam.spring.university.util;

import java.io.InputStream;
import java.util.List;

public interface IJsonUtils
{
    String toJson(Object object);

    <T> T toObject(String json, Class<?> clazz);

    <T> List<T> toObjectList(String json, Class<?> clazz);

    <T> T toObject(InputStream json, Class<?> clazz);

    <T> List<T> toObjectList(InputStream json, Class<?> clazz);
}
