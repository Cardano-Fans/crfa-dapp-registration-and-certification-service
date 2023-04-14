-- liquibase formatted sql

-- changeset crfa-metadata-service-reloaded:1

create table example_table (
	id int primary key,
	firstname varchar(50),
	lastname varchar(50) not null,
	state char(2)
)

-- rollback DROP TABLE "example_table" CASCADE;