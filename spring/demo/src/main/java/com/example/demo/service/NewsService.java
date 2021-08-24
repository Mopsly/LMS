package com.example.demo.service;

import com.example.demo.dao.NewsRepository;
import com.example.demo.domain.NewsRecord;
import com.example.demo.domain.User;
import com.example.demo.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {
    private final NewsRepository newsRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public NewsRecord findRecordById(Long id){
        return newsRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public List<NewsRecord> findNewsWithFilters(String tag, Long authorId){
        tag = tag == null? "any": tag;
        authorId = authorId == null? -1: authorId;

        if (authorId == -1 && tag.equals("any")){
            return newsRepository.findAll();
        }
        if (authorId != -1 && tag.equals("any")){
            return newsRepository.findAllByAuthorId(authorId);
        }
        if (authorId == -1){
            return newsRepository.findAllByTag(tag);
        }
        return newsRepository.findAllByTagAndAuthor(tag,authorId);
    }

    public List<User> findAllAuthors(){
        return newsRepository.findAllAuthors();
    }
    public List<String> findAllTags(){
        return newsRepository.findAllTags();
    }

    public void save(NewsRecord record) {
        newsRepository.save(record);
    }

    public void deleteById(Long id){
        newsRepository.deleteById(id);
    }
}
