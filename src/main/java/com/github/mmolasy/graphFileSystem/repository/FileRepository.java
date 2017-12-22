package com.github.mmolasy.graphFileSystem.repository;

import com.github.mmolasy.graphFileSystem.graph.DirectoryNode;
import com.github.mmolasy.graphFileSystem.graph.FileNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

public interface FileRepository extends Neo4jRepository<FileNode, Long> {

    @Query("MATCH (f :File) where id(f)={id} " +
            "RETURN f")
    FileNode findFileById(@Param("id") Long id);

    @Query("MATCH (d :Directory) where id(d)={directoryId} " +
            "CREATE (d)-[:containsFile]->(f :File {creationDate: timestamp(), lastUpdateDate:timestamp(), content: {content}, name: {name}}) " +
            "SET d.lastUpdateDate=timestamp() " +
            "RETURN f")
    FileNode createFile(@Param("directoryId") Long directoryId, @Param("name") String name, @Param("content") byte [] content);

    @Query("MATCH (f :File) where id(f)={id} " +
            "MATCH (d :Directory)-[cf:containsFile]->(f :File) " +
            "DELETE cf, f " +
            "SET d.lastUpdateDate=timestamp() " +
            "RETURN TRUE")
    Boolean deleteFileById(@Param("id") Long id);
}