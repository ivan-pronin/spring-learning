package epam.spring.university.service.impl;

public interface AopCounterService
{
    void insertEventCounter(String counterName, int eventId, int newValue);
    
    void updateEventCounter(String counterName, int eventId, int newValue);
    
    void insertDiscountCounter(String counterName, int userId, int newValue);
    
    void updateDiscountCounter(String counterName, int userId, int newValue);
}
