package epam.spring.university.domain;

/**
 * @author Yuriy_Tkach
 */
public enum EventRating
{

    LOW(0.8), MID(1), HIGH(1.2);

    public double getMultiplier()
    {
        return multiplier;
    }

    private double multiplier;

    private EventRating(double multiplier)
    {
        this.multiplier = multiplier;
    }

}
