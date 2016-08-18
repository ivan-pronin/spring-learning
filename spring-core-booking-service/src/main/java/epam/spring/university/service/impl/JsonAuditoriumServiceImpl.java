package epam.spring.university.service.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;

import epam.spring.university.domain.Auditorium;
import epam.spring.university.service.AuditoriumService;
import epam.spring.university.util.IJsonUtils;
import epam.spring.university.util.IResourceUtils;

public class JsonAuditoriumServiceImpl extends AbstractJsonServiceImpl implements AuditoriumService
{
    private Set<Auditorium> auditoriums = new HashSet<>();

    @SuppressWarnings("unused")
    protected JsonAuditoriumServiceImpl()
    {
    }

    protected JsonAuditoriumServiceImpl(@Value("${auditoriumDataFileName}") String dataFileName,
            IResourceUtils resourceUtils, IJsonUtils jsonUtils,
            @Value("epam.spring.university.domain.Auditorium") Class<?> domainClazz)
    {
        super(dataFileName, resourceUtils, jsonUtils, domainClazz);
    }

    @Override
    public Auditorium getByName(String name)
    {
        Set<Auditorium> results = auditoriums.stream().filter(a -> a.getName().contains(name))
                .collect(Collectors.toSet());
        return results.isEmpty() ? null : results.iterator().next();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<Auditorium> getAll()
    {
        return auditoriums;
    }

    @Override
    protected void init()
    {
        auditoriums = super.getAll();
    }

    public Set<Auditorium> getAuditoriums()
    {
        return auditoriums;
    }
}
