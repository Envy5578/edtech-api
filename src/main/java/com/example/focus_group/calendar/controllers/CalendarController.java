package com.example.focus_group.calendar.controllers;

import com.example.focus_group.calendar.models.Calendar;
import com.example.focus_group.calendar.models.Event;
import com.example.focus_group.calendar.services.CalendarService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/calendar", produces = MediaType.APPLICATION_JSON_VALUE)
public class CalendarController {
    private final CalendarService service;
     //private final ModelMapper modelMapper;

    @GetMapping()
    public List<Calendar> getAllCalendars(){
        return service.getCalendars();
    }

    @GetMapping("/{id}")
    public Calendar getCalendar(@PathVariable("id") UUID id){
        return service.getCalendar(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Calendar createCalendar(@RequestBody Calendar calendar){
        return service.createCalendar(calendar);
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Calendar updateCalendar(@PathVariable("id") UUID id, @RequestBody Calendar calendar){
        return service.updateCalendar(id,calendar);
    }

    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Calendar deleteCalendar(@PathVariable("id") UUID id){
        return service.deleteCalendar(id);
    }


    @GetMapping(value = "/{id}/event")
    public List<Event> getAllEvents(){
        return service.getEvents();
    }

    @GetMapping("/{id}/event/{id_event}")
    public Event getEvent(@PathVariable("id") UUID id, @PathVariable("id") UUID id_event){
        return service.getEvent(id);
    }

    @PostMapping(value = "/{id}/event", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Event createEvent(@PathVariable("id") UUID id, @RequestBody Event event){
        return service.createEvent(event);
    }

    @PatchMapping(value = "/{id}/event/{id_event}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Event updateEvent(@PathVariable("id") UUID id, @PathVariable("id") UUID id_event, @RequestBody Event event){
        return service.updateEvent(id,event);
    }

    @DeleteMapping(value = "/{id}/event/{id_event}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Event deleteEvent(@PathVariable("id") UUID id, @PathVariable("id") UUID id_event){
        return service.deleteEvent(id);
    }
}
