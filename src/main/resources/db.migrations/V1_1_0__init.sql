drop table if exists dapp_registration;
drop table if exists metadata_urls;

CREATE TABLE dapp_registration (
   subject VARCHAR(255) NOT NULL,
   root_hash VARCHAR(255),
   action_type INT NOT NULL,
   release_number VARCHAR(255),
   release_name VARCHAR(255),
   signature_r VARCHAR(255),
   signature_s VARCHAR(255),
   signature_algo VARCHAR(255),
   signature_pub VARCHAR(255),
   CONSTRAINT pk_dappregistration PRIMARY KEY (subject)
);

CREATE TABLE metadata_urls (
   subject VARCHAR(255) NOT NULL,
   url VARCHAR(255)
);

ALTER TABLE metadata_urls ADD CONSTRAINT fk_metadata_urls_on_dapp_registration FOREIGN KEY (subject) REFERENCES dapp_registration (subject);