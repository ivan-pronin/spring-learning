package epam.spring.university.domain;

import java.util.Date;
import java.util.Objects;

/**
 * @author Yuriy_Tkach
 */
public class User extends DomainObject
{

    private String firstName;

    private String lastName;

    private String email;

    private Date birthDate;

    public User()
    {
    }

    public String getFirstName()
    {
        return firstName;
    }

    public User(String firstName, String lastName, String email)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Date getBirthDate()
    {
        return birthDate;
    }

    public void setBirthDate(Date birthDate)
    {
        this.birthDate = birthDate;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(firstName, lastName, email);
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
        User other = (User) obj;
        return Objects.equals(email, other.getEmail()) && Objects.equals(firstName, other.getFirstName())
                && Objects.equals(lastName, other.getLastName());
    }

    @Override
    public String toString()
    {
        return "User [Id=" + getId() + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "]";
    }
}
