package epam.spring.university.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Yuriy_Tkach
 */
public class Auditorium
{

    private String name;

    private int numberOfSeats;

    private Set<Integer> vipSeats = Collections.emptySet();

    public Auditorium(String name, int numberOfSeats, Set<Integer> vipSeats)
    {
        this.name = name;
        this.numberOfSeats = numberOfSeats;
        this.vipSeats = vipSeats;
    }

    public Auditorium()
    {
    }

    /**
     * Counts how many vip seats are there in supplied <code>seats</code>
     * @param seats
     * Seats to process
     * @return number of vip seats in request
     */
    public int countVipSeats(Collection<Integer> seats)
    {
        return (int) seats.stream().filter(seat -> vipSeats.contains(seat)).count();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public long getNumberOfSeats()
    {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats)
    {
        this.numberOfSeats = numberOfSeats;
    }

    public Set<Integer> getAllSeats()
    {
        return IntStream.range(1, numberOfSeats + 1).boxed().collect(Collectors.toSet());
    }

    public Set<Integer> getVipSeats()
    {
        return vipSeats;
    }

    public void setVipSeats(Set<Integer> vipSeats)
    {
        this.vipSeats = vipSeats;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        Auditorium other = (Auditorium) obj;
        return Objects.equals(name, other.getName());
    }

    @Override
    public String toString()
    {
        return "Auditorium [name=" + name + ", numberOfSeats=" + numberOfSeats + ", vipSeats=" + vipSeats + "]";
    }

}
