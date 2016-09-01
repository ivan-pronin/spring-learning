package com.epam.springadvanced.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.epam.springadvanced.service.EventService;
import com.epam.springadvanced.ws.request.GetEventByIdRequest;
import com.epam.springadvanced.ws.response.GetEventByIdResponse;

@Endpoint
public class EventEndpoint
{
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    @Autowired
    private EventService eventService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getEventRequest")
    @ResponsePayload
    public GetEventByIdResponse getEventById(@RequestPayload GetEventByIdRequest request)
    {
        GetEventByIdResponse response = new GetEventByIdResponse();
        response.setEvent(eventService.getById(request.getId()));
        return response;
    }
}
