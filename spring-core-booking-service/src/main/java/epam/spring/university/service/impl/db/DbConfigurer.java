package epam.spring.university.service.impl.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class DbConfigurer
{
    private static final String DROP_TABLE_PATTERN = "DROP TABLE %s";
    private static final String FUTURE_DATE = "2016-08-24T19:00:00";
    private static final String PAST_DATE = "2015-02-08T18:00:00";
    private static final String SOME_PAST_DATE = "1980-10-20";
    private static final String PAST_DATE_PLUS_DAY = "2016-10-21";
    private static final String ID_INT_GENERATED_ALWAYS_AS_IDENTITY = "id int GENERATED ALWAYS AS IDENTITY (START WITH "
            + "1, INCREMENT BY 1) PRIMARY KEY,";
    private static final String INSERT_INTO_DATES = "INSERT INTO dates(date) VALUES(?)";
    private static final String INSERT_INTO_EVENTS = "INSERT INTO events(name, dateId, ratingId, auditoriumId) "
            + "VALUES(?, ?, ?, ?)";
    private static final String INSERT_INTO_RATINGS = "INSERT INTO ratings(name, multiplier) VALUES(?, ?)";
    private static final Logger LOGGER = LoggerFactory.getLogger(DbConfigurer.class);
    private static final String SELECT_COUNT_FROM = "select count(*) from %s";
    private static final String STAR_WARS_7 = "Star Wars 7";
    private Set<String> dbTables;
    private JdbcTemplate jdbcTemplate;
    private static boolean isInitialized;

    private DbConfigurer(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void dropAllTables()
    {
        Set<String> tableNames = getTableNames();
        LOGGER.info("Starting to drop ALL tables ... Number of tables to be dropped: " + tableNames.size());
        for (String table : tableNames)
        {
            dropTable(table);
        }
        LOGGER.info("Dropping tables finished. Current number of tables: " + getTableNames().size());
    }

    private void dropTable(String table)
    {
        String sql = String.format(DROP_TABLE_PATTERN, table);
        jdbcTemplate.execute(sql);
    }

    public Set<String> getTableNames()
    {
        try
        {
            return getDBTables(jdbcTemplate.getDataSource().getConnection());
        }
        catch (SQLException e)
        {
            LOGGER.error("Error occured while reading tables", e);
        }
        return Collections.emptySet();
    }

    @SuppressWarnings("unused")
    private void init()
    {
        if (!isInitialized)
        {

            LOGGER.info("Started tables creation");
            createTables();
            LOGGER.info("Finished tables creation");
            dbTables = getTableNames();
            LOGGER.info("Total number of tables: {}", dbTables.size());
            LOGGER.info("List of tables: {}", dbTables);

            fillTables();
            isInitialized = true;
        }
    }

    private void fillTables()
    {
        fillUsersTable();
        fillDatesTable();
        fillAuditoriumsTable();
        fillRatingsTable();
        fillEventsTable();
    }

    private void fillEventsTable()
    {
        jdbcTemplate.update(INSERT_INTO_EVENTS, STAR_WARS_7, 1, 1, 1);
        jdbcTemplate.update(INSERT_INTO_EVENTS, STAR_WARS_7, 2, 3, 1);
        logTableRecords("events");
    }

    private void fillRatingsTable()
    {
        jdbcTemplate.update(INSERT_INTO_RATINGS, "LOW", 0.8d);
        jdbcTemplate.update(INSERT_INTO_RATINGS, "MID", 1d);
        jdbcTemplate.update(INSERT_INTO_RATINGS, "HIGH", 1.2);
        logTableRecords("ratings");
    }

    public void truncateTable(String tableName)
    {
        jdbcTemplate.execute("TRUNCATE TABLE " + tableName);
    }

    private void createAuditoriumsTable()
    {
        jdbcTemplate.execute("CREATE table auditoriums(" + ID_INT_GENERATED_ALWAYS_AS_IDENTITY
                + "name varchar(255) NOT NULL," + "numberOfSeats int NOT NULL," + "vipSeats varchar(255)" + ")");
    }

    private void createDatesTable()
    {
        jdbcTemplate
                .execute("CREATE table dates(" + ID_INT_GENERATED_ALWAYS_AS_IDENTITY + "date TIMESTAMP NOT NULL" + ")");
    }

    private void createEventsTable()
    {
        jdbcTemplate.execute("CREATE table events(" + ID_INT_GENERATED_ALWAYS_AS_IDENTITY
                + "name varchar(255) NOT NULL," + "dateId int NOT NULL," + "ratingId int NOT NULL,"
                + "auditoriumId int NOT NULL," + "basePrice double NOT NULL DEFAULT 50.0)");
    }

    private void createRatingsTable()
    {
        jdbcTemplate.execute("CREATE table ratings(" + ID_INT_GENERATED_ALWAYS_AS_IDENTITY
                + "name varchar(255) NOT NULL," + "multiplier double NOT NULL" + ")");
    }

    private void createTables()
    {
        createUsersTable();
        createDatesTable();
        createAuditoriumsTable();
        createRatingsTable();
        createEventsTable();
        createTicketsTable();
        createEventAopCounterTable();
        createDiscountAopCounterTable();
    }

    private void createDiscountAopCounterTable()
    {
        jdbcTemplate.execute("CREATE table discountAopCounter(" + ID_INT_GENERATED_ALWAYS_AS_IDENTITY
                + "name varchar(255)" + " NOT NULL, userId int NOT NULL, count int DEFAULT 0)");

    }

    private void createEventAopCounterTable()
    {
        jdbcTemplate.execute("CREATE table eventAopCounter(" + ID_INT_GENERATED_ALWAYS_AS_IDENTITY + "name varchar(255)"
                + " NOT NULL, eventId int NOT NULL, count int DEFAULT 0)");
    }

    private void createTicketsTable()
    {
        jdbcTemplate.execute("CREATE table tickets(" + ID_INT_GENERATED_ALWAYS_AS_IDENTITY + "userId int NOT NULL,"
                + "eventId int NOT NULL," + "dateId int NOT NULL," + "seat int NOT NULL" + ")");
    }

    private void createUsersTable()
    {
        jdbcTemplate.execute("CREATE table users(" + ID_INT_GENERATED_ALWAYS_AS_IDENTITY
                + "firstName varchar(255) NOT NULL," + "lastName varchar(255) NOT NULL,"
                + "email varchar(255) NOT NULL," + "birthDate TIMESTAMP" + ")");
    }

    private void fillAuditoriumsTable()
    {

        jdbcTemplate.update("INSERT INTO auditoriums(name, numberOfSeats, vipSeats) VALUES(?, ?, ?)", "GrandCinema",
                500, "30,31,32,33,34");
        logTableRecords("auditoriums");
    }

    private void fillDatesTable()
    {
        jdbcTemplate.update(INSERT_INTO_DATES, getSqlDateFromString(PAST_DATE));
        jdbcTemplate.update(INSERT_INTO_DATES, getSqlDateFromString(FUTURE_DATE));
        jdbcTemplate.update(INSERT_INTO_DATES, getSqlDateFromString(SOME_PAST_DATE));
        jdbcTemplate.update(INSERT_INTO_DATES, getSqlDateFromString(PAST_DATE_PLUS_DAY));
        logTableRecords("dates");
    }

    private void fillUsersTable()
    {
        jdbcTemplate.update("INSERT INTO users(firstName, lastName, email, birthDate) VALUES(?,?,?,?)", "Kyle", "Rice",
                "kyle@mail.com", DateTime.now().toDate());
        logTableRecords("users");
    }

    private Set<String> getDBTables(Connection targetDBConn)
    {
        Set<String> set = new HashSet<String>();
        DatabaseMetaData dbmeta;
        try
        {
            dbmeta = targetDBConn.getMetaData();
            readDBTable(set, dbmeta, "TABLE", null);
            readDBTable(set, dbmeta, "VIEW", null);
        }
        catch (SQLException e)
        {
            LOGGER.error("Error occured while getting list of tables", e);
        }
        return set;
    }

    private java.sql.Date getSqlDateFromString(String dateString)
    {
        java.util.Date date = new DateTime(dateString).toDate();
        return new java.sql.Date(date.getTime());
    }

    private void logTableRecords(String tableName)
    {
        int count = jdbcTemplate.queryForObject(String.format(SELECT_COUNT_FROM, tableName), Integer.class);
        LOGGER.info("Added {} records to the <{}> table", count, tableName);
    }

    private void readDBTable(Set<String> set, DatabaseMetaData dbmeta, String searchCriteria, String schema)
            throws SQLException
    {
        ResultSet rs = dbmeta.getTables(null, schema, null, new String[] {searchCriteria});
        while (rs.next())
        {
            set.add(rs.getString("TABLE_NAME").toLowerCase());
        }
    }
}
