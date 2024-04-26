package com.example.focus_group.kanban.models;

import com.example.focus_group.common.models.Comment;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    @Size(max = 50)
    @NotNull
    private String name;
    @Column
    @Size(max = 250)
    private String description;
    @Column(name = "pg_create")
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime createData;
    @Column(name = "pg_complete")
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime completeData;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments;
}
