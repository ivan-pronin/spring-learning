package com.epam.springadvanced.xml.adapter;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime>
{
    @Override
    public String marshal(LocalDateTime arg0) throws Exception
    {
        return arg0.toString();
    }

    @Override
    public LocalDateTime unmarshal(String arg0) throws Exception
    {
        return LocalDateTime.parse(arg0);
    }
}
