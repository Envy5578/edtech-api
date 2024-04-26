package com.example.focus_group.kanban.repositories;
import com.example.focus_group.kanban.models.KanColumn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KanColumnRepository extends JpaRepository<KanColumn, Long> {
}
