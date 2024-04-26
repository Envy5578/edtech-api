create sequence kan_column_seq start with 1 increment by 50;

create sequence task_seq start with 1 increment by 50;

create table comment (
                pg_create timestamp(6) not null,
                uuid uuid not null,
                massage varchar(2000) not null,
                primary key (uuid)
);

create table kan_column (
                max_task integer,
                id bigint not null,
                name varchar(50) not null,
                description varchar(250) not null,
                sequence varchar(255),
                primary key (id)
);

create table kan_column_tasks (
                kan_column_id bigint not null,
                tasks_id bigint not null unique
);

create table kanban (
                uuid uuid not null,
                name varchar(50) not null,
                description varchar(250) not null,
                sequence varchar(255),
                primary key (uuid)
);

create table kanban_columns (
                columns_id bigint not null unique,
                project_uuid uuid not null
);

create table kanban_comments (
                comments_uuid uuid not null unique,
                project_uuid uuid not null
);

create table task (
                id bigint not null,
                pg_complete timestamp(6) not null,
                pg_create timestamp(6) not null,
                name varchar(50) not null,
                description varchar(250),
                primary key (id)
);

create table task_comments (
                task_id bigint not null,
                comments_uuid uuid not null unique
);

alter table if exists kan_column_tasks
    add constraint kan_column_tasks_tasks_id_fkey
        foreign key (tasks_id)
            references task(id);

alter table if exists kan_column_tasks
    add constraint kan_column_tasks_kan_column_id_fkey
        foreign key (kan_column_id)
            references kan_column(id);

alter table if exists kanban_columns
    add constraint kanban_columns_columns_id_fkey
        foreign key (columns_id)
            references kan_column(id);

alter table if exists kanban_columns
    add constraint kanban_columns_project_uuid_fkey
        foreign key (project_uuid)
            references kanban(uuid);

alter table if exists kanban_comments
    add constraint kanban_comments_comments_uuid_fkey
        foreign key (comments_uuid)
            references comment(uuid);

alter table if exists kanban_comments
    add constraint kanban_comments_project_uuid_fkey
        foreign key (project_uuid)
            references kanban(uuid);

alter table if exists task_comments
    add constraint task_comments_comments_uuid
        foreign key (comments_uuid)
            references comment(uuid);

alter table if exists task_comments
    add constraint task_comments_task_id_fkey
        foreign key (task_id)
            references task(id);