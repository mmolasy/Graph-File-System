package com.github.mmolasy.graphFileSystem.repository;

import com.github.mmolasy.graphFileSystem.graph.DirectoryNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

public interface DirectoryRepository extends Neo4jRepository<DirectoryNode, Long> {

    @Query("MATCH (d :Directory) where id(d)={id} " +
            "OPTIONAL MATCH (d)-[:subDirectory]->(d2 :Directory) " +
            "OPTIONAL MATCH (d)-[:containsFile]->(f :File) " +
            "return d, d2, f")
    DirectoryNode findDirectoryById(@Param("id") Long id);

    @Query("MATCH (d :Directory) where d.isRoot=true " +
            "OPTIONAL MATCH (d)-[:subDirectory]->(d2 :Directory) " +
            "OPTIONAL MATCH (d)-[:containsFile]->(f :File) " +
            "return d, d2, f")
    DirectoryNode findRootDirectory();

    @Query("OPTIONAL MATCH (d :Directory) where d.isRoot=true " +
            "FOREACH ( x in CASE WHEN d IS NULL THEN [1] ELSE [0] END | " +
            "   CREATE(d :Directory {isRoot:true, creationDate: timestamp(), lastUpdateDate:timestamp()}) " +
            ")" +
            "RETURN d")
    DirectoryNode createRootDirectory();

    @Query("MATCH (parent :Directory) where id(d)={parentId} " +
            "CREATE (parent)-[:subDirectory]->(child :Directory {creationDate: timestamp(), directoryName: {directoryName}, lastUpdateDate:timestamp(), isRoot:false}) " +
            "SET parent.lastUpdateDate=timestamp() " +
            "RETURN child")
    DirectoryNode createDirectory(@Param("parentId") Long parentId, @Param("directoryName") String directoryName);

    //TODO
    @Query("MATCH (d :Directory) where id(d)={id} and d.isRoot <> true" +
            "DELETE d " +
            "RETURN true")
    Boolean deleteDirectoryById(@Param("id") Long id);

    @Query("MATCH (n1)-[r]-(n2) " +
            "DELETE r, n1, n2")
    Boolean destroyFileSystem();
}