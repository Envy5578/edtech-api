package com.example.focus_group.kanban.controllers.dto.kanban;

public record KanColumnDTO (
        Integer position,
        String name,
        String description,
        Integer maxTask){
}
