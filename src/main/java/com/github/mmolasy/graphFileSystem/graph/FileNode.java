package com.github.mmolasy.graphFileSystem.graph;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@Data
@AllArgsConstructor
@NoArgsConstructor
@NodeEntity(label = "File")
public class FileNode {
    @GraphId
    private Long id;

    @Property(name = "name")
    private String name;

    @Property(name = "content")
    private byte [] content;

    @Property(name = "creationDate")
    private Long creationDate;

    @Property(name = "lastUpdateDate")
    private Long lastUpdateDate;

    private String base64;
}
