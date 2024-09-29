create table user (
    id bigint not null auto_increment,
    role varchar(10) not null,
    document varchar(100) not null unique,
    email varchar(100) not null unique,
    name varchar(100) not null,

    primary key(id)
);

create table room (
    id bigint not null auto_increment,
    name varchar(100) not null,

    primary key(id)
);

create table schedule (
    id bigint not null auto_increment,
    user_id bigint not null,
    room_id bigint not null,

    primary key(id),
    foreign key (user_id) references user(id),
    foreign key (room_id) references room(id)
);