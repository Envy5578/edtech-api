CREATE TABLE IF NOT EXISTS calendar (
                uuid uuid not null,
                name varchar(50) not null,
                description varchar(250) not null,
                primary key (uuid)
);

CREATE TABLE IF NOT EXISTS calendar_events (
                calendar_uuid uuid not null,
                events_uuid uuid not null unique
);

CREATE TABLE IF NOT EXISTS event (
                pg_end timestamp(6) not null,
                pg_start timestamp(6) not null,
                uuid uuid not null,
                name varchar(50) not null,
                description varchar(250) not null,
                primary key (uuid)
);

CREATE TABLE IF NOT EXISTS event_comments (
                comments_uuid uuid not null unique,
                event_uuid uuid not null
);

alter table if exists calendar_events
    add constraint calendar_events_events_uuid_fkey
        foreign key (events_uuid)
            references event(uuid);

alter table if exists calendar_events
    add constraint calendar_events_calendar_uuid_fkey
        foreign key (calendar_uuid)
            references calendar(uuid);

alter table if exists event_comments
    add constraint event_comments_comments_uuid_fkey
        foreign key (comments_uuid)
            references comment(uuid);

alter table if exists event_comments
    add constraint event_comments_event_uuid_fkey
        foreign key (event_uuid)
            references event(uuid);