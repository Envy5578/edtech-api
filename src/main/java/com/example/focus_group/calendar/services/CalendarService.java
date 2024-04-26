package com.example.focus_group.calendar.services;

import com.example.focus_group.calendar.models.Calendar;
import com.example.focus_group.calendar.models.Event;
import com.example.focus_group.calendar.repositories.CalendarRepository;
import com.example.focus_group.calendar.repositories.EventRepository;
import com.example.focus_group.utils.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;
    private final EventRepository eventRepository;

    public List<Calendar> getCalendars(){
        return calendarRepository.findAll();
    }

    public Calendar getCalendar(UUID uuid){
        return calendarRepository.findById(uuid).orElseThrow(()->new NotFoundException(
                        String.format("Calendar not found by id: %s", uuid)
                ));
    }

    public Calendar createCalendar(Calendar calendar){
        return calendarRepository.save(calendar);
    }

    public Calendar updateCalendar(UUID uuid, Calendar calendar){
        Calendar entity = getCalendar(uuid);
        entity.setName(calendar.getName());
        entity.setDescription(calendar.getDescription());
        return calendarRepository.save(entity);
    }

    public Calendar deleteCalendar(UUID uuid){
        Calendar entity = getCalendar(uuid);
        calendarRepository.deleteById(uuid);
        return entity;
    }
    
    public List<Event> getEvents(){
        return eventRepository.findAll();
    }

    public Event getEvent(UUID uuid){
        return eventRepository.findById(uuid).orElseThrow(()->new NotFoundException(
                String.format("Event not found by id: %s", uuid)
        ));
    }

    public Event createEvent(Event event){
        return eventRepository.save(event);
    }

    public Event updateEvent(UUID uuid, Event event){
        Event entity = getEvent(uuid);
        entity.setName(event.getName());
        entity.setDescription(event.getDescription());
        entity.setStart(event.getStart());
        entity.setEnd(event.getEnd());
        return eventRepository.save(entity);
    }

    public Event deleteEvent(UUID uuid){
        Event entity = getEvent(uuid);
        eventRepository.deleteById(uuid);
        return entity;
    }
    
    
}
