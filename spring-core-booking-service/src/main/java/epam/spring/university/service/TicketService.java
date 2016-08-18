package epam.spring.university.service;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import epam.spring.university.domain.Event;
import epam.spring.university.domain.Ticket;
import epam.spring.university.domain.User;

/**
 * @author Yuriy_Tkach
 */
public interface TicketService extends AbstractDomainObjectService<Ticket>
{

    /**
     * Finding user by email
     * @param email
     * Email of the user
     * @return found user or <code>null</code>
     */
    public @Nullable Set<Ticket> getTicketsByEvent(@Nonnull Event event);

    public @Nullable Set<Ticket> getTicketsByUser(@Nonnull User user);

    public void removeAllTickets();

}
