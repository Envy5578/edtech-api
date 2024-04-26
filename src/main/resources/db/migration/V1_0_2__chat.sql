CREATE SEQUENCE IF NOT EXISTS chat_messages_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS chat_rooms_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE IF NOT EXISTS chat_messages (
                sender_id integer,
                id bigint not null,
                room_id bigint,
                timestamp timestamp(6),
                content varchar(255),
                primary key (id)
);

CREATE TABLE IF NOT EXISTS chat_rooms (
                id bigint not null,
                name varchar(255),
                type varchar(255) check (type in ('PERSONAL','GROUP')),
                primary key (id)
);

CREATE TABLE IF NOT EXISTS rooms_members (
                member_id integer not null,
                room_id bigint not null
);

ALTER TABLE chat_messages
    add constraint chat_messages_room_id_fkey
        foreign key (room_id)
            references chat_rooms(id);

ALTER TABLE chat_messages
    add constraint chat_messages_sender_id_fkey
        foreign key (sender_id)
            references users(id);

ALTER TABLE rooms_members
    add constraint rooms_members_member_id_fkey
        foreign key (member_id)
            references users(id);

ALTER TABLE rooms_members
    add constraint room_members_room_id_fkey
        foreign key (room_id)
            references chat_rooms(id);