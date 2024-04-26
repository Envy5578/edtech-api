package com.example.focus_group.kanban.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.focus_group.kanban.models.Project;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
}
