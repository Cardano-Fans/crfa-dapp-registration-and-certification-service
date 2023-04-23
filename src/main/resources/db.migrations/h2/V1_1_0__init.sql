drop table if exists on_chain_dapp_registration_event;
drop table if exists on_chain_dapp_registration_event_failure;
drop table if exists metadata_urls;

CREATE TABLE metadata_urls (
  on_chain_dapp_registration_event_slot BIGINT NOT NULL,
   on_chain_dapp_registration_event_block_hash VARCHAR(255) NOT NULL,
   on_chain_dapp_registration_event_subject VARCHAR(255) NOT NULL,
   slot BIGINT,
   block_hash VARCHAR(255),
   subject VARCHAR(255),
   url VARCHAR(255),
   type INT
);

CREATE TABLE on_chain_dapp_registration_event (
  created TIMESTAMP NOT NULL,
   updated TIMESTAMP NOT NULL,
   root_hash VARCHAR(255),
   action_type INT NOT NULL,
   release_number VARCHAR(255),
   release_name VARCHAR(255),
   signature_r VARCHAR(255),
   signature_s VARCHAR(255),
   signature_algo VARCHAR(255),
   signature_pub VARCHAR(255),
   slot BIGINT NOT NULL,
   block_hash VARCHAR(255) NOT NULL,
   subject VARCHAR(255) NOT NULL,
   CONSTRAINT pk_onchaindappregistrationevent PRIMARY KEY (slot, block_hash, subject)
);

ALTER TABLE metadata_urls ADD CONSTRAINT fk_metadata_urls_on_on_chain_dapp_registration_event FOREIGN KEY (on_chain_dapp_registration_event_slot, on_chain_dapp_registration_event_block_hash, on_chain_dapp_registration_event_subject) REFERENCES on_chain_dapp_registration_event (slot, block_hash, subject);

CREATE TABLE on_chain_dapp_registration_event_failure (
   body VARCHAR(255) NOT NULL,
   slot BIGINT NOT NULL,
   block_hash VARCHAR(255) NOT NULL,
   CONSTRAINT pk_onchaindappregistrationeventfailure PRIMARY KEY (slot, block_hash)
);

CREATE TABLE raw_offchaindapp (
  id BIGINT NOT NULL,
   created TIMESTAMP NOT NULL,
   updated TIMESTAMP NOT NULL,
   subject VARCHAR(255),
   metadata_url VARCHAR(255),
   root_hash_match BOOLEAN,
   body VARCHAR(255),
   canonical_body VARCHAR(255),
   CONSTRAINT pk_rawoffchaindapp PRIMARY KEY (id)
);