package com.example.demo.service;

import com.example.demo.dao.NewsRepository;
import com.example.demo.domain.NewsRecord;
import com.example.demo.domain.User;
import com.example.demo.exception.NotFoundException;
import com.example.demo.utils.filter.NewsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<NewsRecord> findNewsWithFilters(NewsFilter filter){
        String tag =filter.getHashTag();
        String sortDir = filter.getSortOrder();
        Long authorId = filter.getAuthorId();

        tag = tag == null? "any": tag;
        authorId = authorId == null? -1: authorId;

        Sort.Direction direction;
        if (sortDir == null || sortDir.equals("asc")){
            direction = Sort.Direction.ASC;
        }else {
            direction = Sort.Direction.DESC;
        }

        if (authorId == -1 && tag.equals("any")){
            return newsRepository.findAll(Sort.by(direction, "publicationDate"));
        }
        if (authorId != -1 && tag.equals("any")){
            return newsRepository.findAllByAuthorId(authorId,Sort.by(direction, "publicationDate"));
        }
        if (authorId == -1){
            return newsRepository.findAllByTag(tag,Sort.by(direction, "publicationDate"));
        }
        return newsRepository.findAllByTagAndAuthor(tag,authorId,Sort.by(direction, "publicationDate"));
    }

    public List<String> findAllTags(){
        return newsRepository.findAllTags().stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public void save(NewsRecord record) {
        newsRepository.save(record);
    }

    public void deleteById(Long id){
        newsRepository.deleteById(id);
    }

}
