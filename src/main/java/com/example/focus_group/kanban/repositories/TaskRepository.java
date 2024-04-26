package com.example.focus_group.kanban.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.focus_group.kanban.models.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
