-- liquibase formatted sql

-- changeset crfa-metadata-service-reloaded:1

CREATE TABLE IF NOT EXISTS example (
	id IDENTITY PRIMARY KEY,
	firstname varchar(50) not null,
	lastname varchar(50) not null,
	state char(2)
)

-- rollback DROP TABLE "example" CASCADE;
