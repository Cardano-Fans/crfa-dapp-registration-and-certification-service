package de.crfa.app.crfametadataservicereloaded.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class RawOffchainDApp extends AbstractTimestampEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "subject")
    private String subject;

    @Column(name = "metadata_url")
    private String metadataUrl;

    // upon conversion to canonical form, we need to check if root hash matches with on chain data
    @Column(name = "root_hash_match")
    private Boolean rootHashMatch;

    @Column(name = "body")
    private String body;

    @Column(name = "canonical_body")
    private String canonicalBody;

}
