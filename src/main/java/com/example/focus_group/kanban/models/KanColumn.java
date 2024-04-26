package com.example.focus_group.kanban.models;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "kan_column")
public class KanColumn {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    @Size(max = 50)
    @NotNull
    private String name;
    @Column
    @Size(max = 250)
    @NotNull
    private String description;
    @Column
    private Integer maxTask = Integer.MAX_VALUE;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Task> tasks;
    @Column
    private String sequence = "";

    public void init(){
        StringTokenizer tokenizer = new StringTokenizer(sequence, ";");

        while (tokenizer.hasMoreElements()) {
            Long id = Long.parseLong(tokenizer.nextToken());
            Task current = tasks.stream().filter(f->f.getId()==id).findFirst().get();
            tasks.remove(current);
            tasks.add(current);
        }
    }

    public void addTaskIntoEnd(Task task){
        LinkedList<Long> list = sequenceToList();
        list.add(task.getId());
        tasks.add(task);
        setSequence(toSequence(list));
    }

    public long deleteTaskByPos(int pos){
        LinkedList<Long> list = sequenceToList();
        long id = list.remove(pos);
        setSequence(toSequence(list));
        tasks.remove(pos);
        return id;
    }

    public void moveTask(int element, int position){
        LinkedList<Long> list = sequenceToList();
        Long current = list.remove(element);
        list.add(position, current);
        setSequence(toSequence(list));
        Task task = tasks.remove(element);
        tasks.add(position, task);
    }

    private LinkedList<Long> sequenceToList(){
        StringTokenizer tokenizer = new StringTokenizer(sequence, ";");

        LinkedList<Long> list = new LinkedList<>();

        while (tokenizer.hasMoreElements()) {
            list.add(Long.parseLong(tokenizer.nextToken()));
        }

        return list;
    }

    private String toSequence(LinkedList<Long> list){
        return String.join(";", list.stream().map(f->f.toString()).collect(Collectors.toList()));
    }
}
