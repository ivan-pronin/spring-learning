package epam.spring.university.service.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;

import epam.spring.university.domain.User;
import epam.spring.university.service.UserService;
import epam.spring.university.util.IJsonUtils;
import epam.spring.university.util.IResourceUtils;

public class JsonUserServiceImpl extends AbstractJsonServiceImpl implements UserService
{
    private Set<User> users = new HashSet<>();

    protected JsonUserServiceImpl(@Value("${userDataFileName}") String dataFileName, IResourceUtils resourceUtils,
            IJsonUtils jsonUtils, @Value("epam.spring.university.domain.User") Class<?> domainClazz)
    {
        super(dataFileName, resourceUtils, jsonUtils, domainClazz);
    }

    @Override
    public User save(User object)
    {
        if (users.add(object))
        {
            return object;
        }
        return null;
    }

    @SuppressWarnings("unused")
    private JsonUserServiceImpl()
    {
        super();
    }

    @Override
    public void remove(User object)
    {
        users.remove(object);
    }

    @Override
    public User getById(int id)
    {
        Set<User> results = users.stream().filter(e -> e.getId() == id).collect(Collectors.toSet());
        return results.isEmpty() ? null : results.iterator().next();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<User> getAll()
    {
        return users;
    }

    @Override
    public User getUserByEmail(String email)
    {
        Set<User> results = users.stream().filter(e -> e.getEmail().equals(email)).collect(Collectors.toSet());
        return results.isEmpty() ? null : results.iterator().next();
    }

    @Override
    protected void init()
    {
        users = super.getAll();
    }

    @Override
    public void removeAllUsers()
    {
        users.clear();
    }
}
