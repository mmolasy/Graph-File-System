package com.github.mmolasy.graphFileSystem.graph;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@NodeEntity(label = "Directory")
public class DirectoryNode {
    @GraphId
    private Long id;

    @Property(name = "directoryName")
    private String directoryName;

    @Property(name = "creationDate")
    private Long creationDate;

    @Property(name = "lastUpdateDate")
    private Long lastUpdateDate;

    @Property(name = "isRoot")
    private Boolean isRoot;

    @Relationship(type = "subDirectory", direction = Relationship.OUTGOING)
    private Set<DirectoryNode> subDirectories = new HashSet();

    @Relationship(type = "containsFile", direction = Relationship.OUTGOING)
    private Set<FileNode> files = new HashSet();
}
