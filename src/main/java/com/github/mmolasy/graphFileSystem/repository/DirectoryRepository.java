package com.github.mmolasy.graphFileSystem.repository;

import com.github.mmolasy.graphFileSystem.graph.DirectoryNode;
import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

public interface DirectoryRepository extends Neo4jRepository<DirectoryNode, Long> {

    DirectoryNode findById(Long id);

    DirectoryNode findByIsRootTrue();

    @Query("OPTIONAL MATCH (d :Directory) where d.isRoot=true " +
            "FOREACH ( x in CASE WHEN d IS NULL THEN [1] ELSE [0] END | " +
            "   CREATE(d :Directory {isRoot:true, creationDate: timestamp(), lastUpdateDate:timestamp()}) " +
            ")" +
            "RETURN d")
    DirectoryNode createRootDirectory();

    @Query("MATCH (parent :Directory) where id(parent)={parentId} " +
            "CREATE (parent)-[:subDirectory]->(child :Directory {creationDate: timestamp(), directoryName: {directoryName}, lastUpdateDate:timestamp(), isRoot:false}) " +
            "SET parent.lastUpdateDate=timestamp() " +
            "RETURN child")
    DirectoryNode createDirectory(@Param("parentId") Long parentId, @Param("directoryName") String directoryName);

    @Query("MATCH (d :Directory) where id(d)={id} and d.isRoot <> true " +
            "OPTIONAL MATCH (d)<-[s0:subDirectory]-(parent :Directory) " +
            "OPTIONAL MATCH (d)-[f0:containsFile*]->(file0 :File) " +
            "OPTIONAL MATCH (d)-[s:subDirectory*]->(child :Directory) " +
            "OPTIONAL MATCH (child)-[f:containsFile*]->(file :File) " +
            "FOREACH (rel IN f0| DELETE rel) " +
            "FOREACH (rel IN f| DELETE rel) " +
            "FOREACH (rel IN s| DELETE rel) " +
            "DELETE s0, file0, file, child, d " +
            "SET parent.lastUpdateDate = timestamp() ")
    Boolean deleteDirectoryById(@Param("id") Long id);

    @Query("MATCH (n1)-[r]-(n2) " +
            "DELETE r, n1, n2")
    Boolean destroyFileSystem();
}