package com.example.focus_group.kanban.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.focus_group.kanban.models.KanColumn;
import com.example.focus_group.kanban.models.Project;
import com.example.focus_group.kanban.models.Task;
import com.example.focus_group.kanban.repositories.KanColumnRepository;
import com.example.focus_group.kanban.repositories.ProjectRepository;
import com.example.focus_group.kanban.repositories.TaskRepository;
import com.example.focus_group.utils.exceptions.NotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class KanbanService {

    private final ProjectRepository projectRepository;
    private final KanColumnRepository kanColumnRepository;
    private final TaskRepository taskRepository;

    public List<Project> getProjects(){
        return projectRepository.findAll();
    }

    public Project getProject(UUID uuid){
        Project project = projectRepository.findById(uuid).orElseThrow(()->new NotFoundException(
                String.format("Kanban not found by uuid: %s", uuid)
        ));
        project.init();
        return project;
    }

    public Project createProject(Project project){
        return projectRepository.save(project);
    }

    public Project updateProject(UUID uuid, Project project){
        Project entity = getProject(uuid);
        entity.setName(project.getName());
        entity.setDescription(project.getDescription());
        return projectRepository.save(entity);
    }

    public void deleteProject(UUID uuid){
        projectRepository.deleteById(uuid);
    }

    public List<KanColumn> getKanColumns(UUID uuid){
        Project project = getProject(uuid);
        return project.getColumns();
    }

    public KanColumn getKanColumn(UUID uuid, int pos){
        Project project = getProject(uuid);
        return project.getColumns().get(pos);
    }

    public KanColumn createKanColumn(UUID uuid, KanColumn kanColumn){
        Project project = getProject(uuid);
        KanColumn column = kanColumnRepository.save(kanColumn);

        project.addColumnIntoEnd(column);
        projectRepository.save(project);
        return column;
    }

    public KanColumn updateKanColumn(UUID uuid, int pos, KanColumn kanColumn){
        KanColumn entity = getKanColumn(uuid, pos);
        entity.setName(kanColumn.getName());
        entity.setDescription(kanColumn.getDescription());
        entity.setMaxTask(kanColumn.getMaxTask());
        return kanColumnRepository.save(entity);
    }

    public void deleteKanColumn(UUID uuid, int pos){
        Project project = getProject(uuid);
        long id = project.deleteColumnByPos(pos);
        projectRepository.save(project);
        kanColumnRepository.deleteById(id);
    }

    public void moveKanColumn(UUID uuid, int element, int position){
        Project project = getProject(uuid);
        project.moveColumn(element, position);
        projectRepository.save(project);
    }

    public List<Task> getTasksByProject(UUID uuid){
        Project project = getProject(uuid);
        List<Task> tasks = project.getColumns().stream().flatMap(f->f.getTasks().stream()).collect(Collectors.toList());
        return tasks;
    }

    public List<Task> getTasksByColumn(UUID uuid, int columnPos){
        KanColumn column = getKanColumn(uuid, columnPos);
        return column.getTasks();
    }

    public Task getTask(UUID uuid, int columnPos, int pos){
        Project project = getProject(uuid);
        return project.getColumns().get(columnPos).getTasks().get(pos);
    }

    public Task createTask(UUID uuid, int columnPos, Task task){
        KanColumn column = getKanColumn(uuid, columnPos);
        Task t = taskRepository.save(task);

        column.addTaskIntoEnd(t);
        kanColumnRepository.save(column);
        return t;
    }

    public Task updateTask(UUID uuid, int columnPos, int pos, Task task){
        Task entity = getTask(uuid, columnPos, pos);
        entity.setName(task.getName());
        entity.setDescription(task.getDescription());
        return taskRepository.save(entity);
    }

    public void deleteTask(UUID uuid, int columnPos, int pos){
        KanColumn column = getKanColumn(uuid, columnPos);
        long id = column.deleteTaskByPos(pos);
        kanColumnRepository.save(column);
        taskRepository.deleteById(id);
    }

    public void moveTask(UUID uuid, int columnPos, int element, int position){
        KanColumn column = getKanColumn(uuid, columnPos);
        column.moveTask(element, position);
        kanColumnRepository.save(column);
    }

}
