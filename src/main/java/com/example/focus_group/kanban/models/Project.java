package com.example.focus_group.kanban.models;

import com.example.focus_group.common.models.Comment;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@Table(name = "kanban")
public class Project {
    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID uuid;
    @Column
    @Size(max = 50)
    @NotNull
    private String name;
    @Column
    @Size(max = 250)
    @NotNull
    private String description;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<KanColumn> columns;
    @Column
    private String sequence = "";
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments;

    public void init(){
        StringTokenizer tokenizer = new StringTokenizer(sequence, ";");

        while (tokenizer.hasMoreElements()) {
           Long id = Long.parseLong(tokenizer.nextToken());
           KanColumn current = columns.stream().filter(f->f.getId()==id).findFirst().get();
           columns.remove(current);
           columns.add(current);
        }
    }

    public void addColumnIntoEnd(KanColumn column){
        LinkedList<Long> list = sequenceToList();
        list.add(column.getId());
        columns.add(column);
        setSequence(toSequence(list));
    }

    public long deleteColumnByPos(int pos){
        LinkedList<Long> list = sequenceToList();
        long id = list.remove(pos);
        setSequence(toSequence(list));
        columns.remove(pos);
        return id;
    }

    public void moveColumn(int element, int position){
        LinkedList<Long> list = sequenceToList();
        Long current = list.remove(element);
        list.add(position, current);
        setSequence(toSequence(list));
        KanColumn column = columns.remove(element);
        columns.add(position, column);
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
