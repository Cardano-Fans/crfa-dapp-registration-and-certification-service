drop table if exists dapp_registration;
drop table if exists metadata_urls;

drop table if exists verified_signature;
drop table if exists offchain_verification_methods;

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

CREATE TABLE offchain_verification_method (
  signature_pub VARCHAR(255) NOT NULL,
   method INT
);

CREATE TABLE verified_signature (
  signature_pub VARCHAR(255) NOT NULL,
   verified_by VARCHAR(255),
   CONSTRAINT pk_verifiedsignature PRIMARY KEY (signature_pub)
);

ALTER TABLE offchain_verification_method ADD CONSTRAINT fk_offchain_verification_method_on_verified_signature FOREIGN KEY (signature_pub) REFERENCES verified_signature (signature_pub);