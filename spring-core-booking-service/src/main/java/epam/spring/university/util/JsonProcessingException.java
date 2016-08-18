package epam.spring.university.util;

public class JsonProcessingException extends RuntimeException
{
    private static final long serialVersionUID = -3966886243331905418L;

    public JsonProcessingException(String message)
    {
        super(message);
    }

    public JsonProcessingException(String message, Throwable throwable)
    {
        super(message, throwable);
    }
}
