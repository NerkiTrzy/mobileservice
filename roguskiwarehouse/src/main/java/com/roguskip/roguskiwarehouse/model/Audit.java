package com.roguskip.roguskiwarehouse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@MappedSuperclass
public class Audit {

    @Version
    @Column(name = "entity_version")
    @JsonIgnore
    protected Integer entityVersion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    @JsonIgnore
    protected Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modification_date")
    @JsonIgnore
    protected Date modificationDate;

    @Column(name = "uuid")
    protected UUID uuid;

    @PrePersist
    public void prePersist() {
        creationDate = modificationDate = new Date();
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
    }

    @PreUpdate
    public void preUpdate() {
        modificationDate = new Date();
    }
}
