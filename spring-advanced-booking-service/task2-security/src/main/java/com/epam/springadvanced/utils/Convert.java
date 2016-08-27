package com.epam.springadvanced.utils;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public final class Convert
{
    private Convert()
    {
        // nothing to do
    }

    public static Timestamp toTimestamp(LocalDateTime dateTime)
    {
        if (dateTime != null)
        {
            return Timestamp.valueOf(dateTime);
        }
        return null;
    }

    public static Timestamp toTimestamp(LocalDate dateTime)
    {
        if (dateTime != null)
        {
            return Timestamp.valueOf(dateTime.atStartOfDay());
        }
        return null;
    }
}
