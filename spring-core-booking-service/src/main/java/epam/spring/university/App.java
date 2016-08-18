package epam.spring.university;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import epam.spring.university.service.AuditoriumService;
import epam.spring.university.service.EventService;
import epam.spring.university.service.UserService;

public class App
{
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    private AuditoriumService auditoriumService;

    private EventService eventService;

    private UserService userService;

    public App(AuditoriumService auditoriumService, EventService eventService, UserService userService)
    {
        this.auditoriumService = auditoriumService;
        this.eventService = eventService;
        this.userService = userService;
    }

    public static void main(String[] args)
    {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);
        App app = ctx.getBean(App.class);
        LOGGER.info("APP started");

        LOGGER.info("Printing available static data...");
        LOGGER.info("Registered users: ");
        LOGGER.info(app.userService.getAll().toString());
        LOGGER.info("Auditoriums: ");
        LOGGER.info(app.auditoriumService.getAll().toString());
        LOGGER.info("Available events: ");
        LOGGER.info(app.eventService.getAll().toString());

        LOGGER.info("APP finished");
        ctx.close();
    }

}
