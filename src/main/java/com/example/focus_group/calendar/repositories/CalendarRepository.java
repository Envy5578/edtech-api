package com.example.focus_group.calendar.repositories;

import com.example.focus_group.calendar.models.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CalendarRepository extends JpaRepository<Calendar, UUID> {
}
