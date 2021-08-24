package com.example.demo.dao;

import com.example.demo.domain.NewsRecord;
import com.example.demo.domain.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<NewsRecord, Long> {

    @Query("from NewsRecord nr " +
            "WHERE nr.author.id = :authorId")
    List<NewsRecord> findAllByAuthorId(@Param("authorId") Long authorId, Sort sort);

    List<NewsRecord> findAllByTag(String tag, Sort sort);

    @Query("from NewsRecord nr " +
            "WHERE nr.tag = :tag AND nr.author.id = :authorId")
    List<NewsRecord> findAllByTagAndAuthor(@Param("tag") String tag,
                                           @Param("authorId") Long authorId, Sort sort);

    @Query("select distinct nr.tag from NewsRecord nr")
    List<String> findAllTags();

    void deleteById(Long id);
}
