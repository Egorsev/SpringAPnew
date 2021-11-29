
CREATE TABLE marvel_characters(
    id bigint not null primary key ,
    name varchar(255),
    description varchar(255),
    image_name varchar(255),
    mod TIMESTAMP
);
CREATE TABLE marvel_comics(
    id bigint not null primary key ,
    description varchar(255),
    format varchar(255),
    image_name varchar(255),
    mod timestamp,
    page_count integer,
    title varchar(255)
);
create table comics_marvel_characters(
    comics_id bigint not null,
    marvel_characters_id bigint not null,
    foreign key (marvel_characters_id) references marvel_characters(id),
    foreign key  (comics_id) references marvel_comics(id)
);
create table comics_date(
    id bigint not null primary key,
    date timestamp,
    type varchar(255),
    comics_id bigint,
    foreign key(comics_id) references marvel_comics
)