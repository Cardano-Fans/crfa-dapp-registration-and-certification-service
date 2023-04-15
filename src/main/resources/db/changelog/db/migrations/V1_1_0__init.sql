drop table if exists example;

create table example
(
    id bigint not null auto_increment primary key,
    firstname              varchar(255) not null,
    lastname              varchar(255),
    state                 char(2)
);

CREATE INDEX idx_example1
    ON example(lastname);
