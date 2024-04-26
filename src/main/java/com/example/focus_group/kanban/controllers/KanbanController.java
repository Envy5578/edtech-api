package com.example.focus_group.kanban.controllers;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.focus_group.kanban.controllers.dto.kanban.KanColumnDTO;
import com.example.focus_group.kanban.controllers.dto.kanban.ProjectDTO;
import com.example.focus_group.kanban.controllers.dto.kanban.TaskDTO;
import com.example.focus_group.kanban.models.KanColumn;
import com.example.focus_group.kanban.models.Project;
import com.example.focus_group.kanban.models.Task;
import com.example.focus_group.kanban.services.KanbanService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(value = "api/v1/projects", produces = MediaType.APPLICATION_JSON_VALUE)
public class KanbanController {
    private final KanbanService service;

    //private final CalendarService calendarService;
    //private final ModelMapper modelMapper;

    @Operation(summary = "Get all available projects")
    @GetMapping()
    public List<ProjectDTO> getAllProjects(){
        return service.getProjects().stream().map(f-> new ProjectDTO(
                f.getUuid().toString(),
                f.getName(),
                f.getDescription())).collect(Collectors.toList());
    }

    @Operation(summary = "Get a project by id")
    @GetMapping("/{id}")
    public ProjectDTO getProject(@PathVariable("id") UUID id){
        Project f = service.getProject(id);
        return new ProjectDTO(
                f.getUuid().toString(),
                f.getName(),
                f.getDescription());
    }

    @Operation(summary = "Create a project")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProjectDTO createProject(@RequestBody Project project){
        Project f =  service.createProject(project);
        return new ProjectDTO(
                f.getUuid().toString(),
                f.getName(),
                f.getDescription());
    }

    @Operation(summary = "Update a project")
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProjectDTO updateProject(@PathVariable("id") UUID id, @RequestBody Project project){
        Project f =  service.updateProject(id, project);
        return new ProjectDTO(
                f.getUuid().toString(),
                f.getName(),
                f.getDescription());
    }

    @Operation(summary = "Delete a project")
    @DeleteMapping(value = "/{id}")
    public void deleteProject(@PathVariable("id") UUID id){
        service.deleteProject(id);
    }

    @Operation(summary = "Get all columns of the current project")
    @GetMapping(value = "/{id}/columns")
    public List<KanColumnDTO> getAllKanColumns(@PathVariable("id") UUID id){
        return service.getKanColumns(id).stream().map(f-> new KanColumnDTO(
                f.getMaxTask(),
                f.getName(),
                f.getDescription(),
                f.getMaxTask())).collect(Collectors.toList());
    }

    @Operation(summary = "Get a column for the position of the current project")
    @GetMapping("/{id}/columns/{columnPos}")
    public KanColumnDTO getKanban(@PathVariable("id") UUID id, @PathVariable("columnPos") Integer columnPos){
        KanColumn f = service.getKanColumn(id, columnPos);
        return new KanColumnDTO(
                f.getMaxTask(),
                f.getName(),
                f.getDescription(),
                f.getMaxTask());
    }

    @Operation(summary = "Create a column at the end in the current project")
    @PostMapping(value = "/{id}/columns",consumes = MediaType.APPLICATION_JSON_VALUE)
    public KanColumnDTO createKanColumn(@PathVariable("id") UUID id, @RequestBody KanColumn column){
        KanColumn f = service.createKanColumn(id, column);
        return new KanColumnDTO(
                f.getMaxTask(),
                f.getName(),
                f.getDescription(),
                f.getMaxTask());
    }

    @Operation(summary = "Update the column by position in the current project")
    @PatchMapping(value = "/{id}/columns/{columnPos}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public KanColumnDTO updateKanColumn(@PathVariable("id") UUID id, @PathVariable("columnPos") Integer columnPos, @RequestBody KanColumn column){
        KanColumn f = service.updateKanColumn(id, columnPos, column);
        return new KanColumnDTO(
                f.getMaxTask(),
                f.getName(),
                f.getDescription(),
                f.getMaxTask());
    }

    @Operation(summary = "Delete a column by position in the current project")
    @DeleteMapping(value = "/{id}/columns/{columnPos}")
    public void deleteKanColumn(@PathVariable("id") UUID id, @PathVariable("columnPos") Integer columnPos){
        service.deleteKanColumn(id, columnPos);
    }

    @Operation(summary = "Move a column in the project to the specified position")
    @PostMapping(value = "/{id}/columns/{columnPos}")
    public void moveKanColumn(@PathVariable("id") UUID id, @PathVariable("columnPos") Integer columnPos, @RequestParam Integer pos){
        service.moveKanColumn(id, columnPos, pos);
    }

    @Operation(summary = "Get all the tasks in the project")
    @GetMapping(value = "/{id}/tasks")
    public List<TaskDTO> getAllTasks(@PathVariable("id") UUID id){
        return service.getTasksByProject(id).stream().map(f-> new TaskDTO(
                f.getName(),
                f.getDescription(),
                f.getCreateData().toString(),
                f.getCompleteData().toString())).collect(Collectors.toList());
    }

    @Operation(summary = "Get all the tasks in the column")
    @GetMapping(value = "/{id}/columns/{columnPos}/tasks")
    public List<TaskDTO> getTasksByColumn(@PathVariable("id") UUID id, @PathVariable("columnPos") Integer columnPos){
        return service.getTasksByColumn(id, columnPos).stream().map(f-> new TaskDTO(
                f.getName(),
                f.getDescription(),
                f.getCreateData().toString(),
                f.getCompleteData().toString())).collect(Collectors.toList());
    }

    @Operation(summary = "Get a task by a position in a column")
    @GetMapping("/{id}/columns/{columnPos}/tasks/{taskPos}")
    public TaskDTO getTask(@PathVariable("id") UUID id, @PathVariable("columnPos") Integer columnPos, @PathVariable("taskPos") Integer taskPos){
        Task f = service.getTask(id, columnPos, taskPos);
        return new TaskDTO(
                f.getName(),
                f.getDescription(),
                f.getCreateData().toString(),
                f.getCompleteData().toString());
    }

    @Operation(summary = "Create a task at the end of the column")
    @PostMapping(value = "/{id}/columns/{columnPos}/tasks",consumes = MediaType.APPLICATION_JSON_VALUE)
    public TaskDTO createTask(@PathVariable("id") UUID id, @PathVariable("columnPos") Integer columnPos, @RequestBody Task task){
        Task f = service.createTask(id, columnPos, task);
        return new TaskDTO(
                f.getName(),
                f.getDescription(),
                f.getCreateData().toString(),
                f.getCompleteData().toString());
    }

    @Operation(summary = "Update the task by position in the column")
    @PatchMapping(value = "/{id}/columns/{columnPos}/tasks/{taskPos}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TaskDTO updateTask(@PathVariable("id") UUID id, @PathVariable("columnPos") Integer columnPos, @PathVariable("taskPos") Integer taskPos, @RequestBody Task task){
        Task f = service.updateTask(id, columnPos, taskPos, task);
        return new TaskDTO(
                f.getName(),
                f.getDescription(),
                f.getCreateData().toString(),
                f.getCompleteData().toString());
    }

    @Operation(summary = "Delete the task by position in the column")
    @DeleteMapping(value = "/{id}/columns/{columnPos}/tasks/{taskPos}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteTask(@PathVariable("id") UUID id, @PathVariable("columnPos") Integer columnPos, @PathVariable("taskPos") Integer taskPos){
        service.deleteTask(id, columnPos, taskPos);
    }

    @Operation(summary = "Move the task by id to the specified point")
    @PostMapping(value = "/{id}/columns/{columnPos}/tasks/{taskPos}")
    public void moveTask(@PathVariable("id") UUID id, @PathVariable("columnPos") Integer columnPos, @PathVariable("taskPos") Integer taskPos, @RequestParam Integer pos){
        service.moveTask(id, columnPos, taskPos, pos);
    }
}
