package com.example.focus_group.calendar.repositories;

import com.example.focus_group.calendar.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
}
