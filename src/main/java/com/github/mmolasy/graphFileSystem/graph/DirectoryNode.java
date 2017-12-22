package com.github.mmolasy.graphFileSystem.graph;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

@NodeEntity
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @Relationship(type = "subDirectory", direction = "INCOMING")
    Set<DirectoryNode> subDirectories;

    @Relationship(type = "containsFile", direction = "INCOMING")
    Set<FileNode> files;
}
