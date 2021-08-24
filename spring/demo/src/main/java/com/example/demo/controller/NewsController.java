package com.example.demo.controller;

import com.example.demo.domain.NewsRecord;
import com.example.demo.domain.User;
import com.example.demo.dto.NewsRecordDto;
import com.example.demo.dto.UserDto;
import com.example.demo.service.NewsService;
import com.example.demo.service.UserService;
import com.example.demo.utils.MapptingUtils.NewsRecordMapper;
import com.example.demo.utils.MapptingUtils.UserMapper;
import com.example.demo.utils.filter.NewsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;
    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public NewsController(NewsService newsService, UserService userService, UserMapper userMapper) {
        this.newsService = newsService;
        this.userService = userService;
        this.userMapper = userMapper;
    }


    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public String newsForm(Model model, @PathVariable Long id) {
        model.addAttribute("record", newsService.findRecordById(id));
        return "news_form";
    }

    @GetMapping("/new")
    @PreAuthorize("@AccessSecurityBean.hasAdminRights(#request)")
    public String newRecord(Model model, HttpServletRequest request) {
        model.addAttribute("record", new NewsRecordDto());
        model.addAttribute("users",  userService.findAll());
        return "news_edit";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("@AccessSecurityBean.hasAdminRights(#request)")
    public String editRecord(Model model, @PathVariable Long id, HttpServletRequest request) {
        model.addAttribute("record", NewsRecordMapper.mapNewsRecordToDto(newsService.findRecordById(id)));
        model.addAttribute("users",  userService.findAll());
        return "news_edit";
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public String recordsWithFilters(NewsFilter filter, Model model) {
        List<NewsRecord> records = newsService.findNewsWithFilters(filter.getHashTag(), filter.getAuthorId());
        model.addAttribute("records", records);
        model.addAttribute("authors", newsService.findAllAuthors());
        model.addAttribute("tags", newsService.findAllTags());
        model.addAttribute("filter", new NewsFilter());
        return "news_table";
    }

    @PostMapping
    @PreAuthorize("@AccessSecurityBean.hasAdminRights(#request)")
    public String recordsTable(@Valid @ModelAttribute("record") NewsRecordDto recordDto, BindingResult bindingResult, HttpServletRequest request){
        if(bindingResult.hasErrors()){
            return "news_edit";
        }
        User author = userMapper.mapDtoToUser(userService.findDtoById(recordDto.getAuthorId()));
        newsService.save(NewsRecordMapper.mapDtoToNewsRecord(recordDto,author));
        return "redirect:/news";
    }

    @DeleteMapping("{id}")
    @PreAuthorize("@AccessSecurityBean.hasAdminRights(#request)")
    public String deleteRecord(@PathVariable Long id, HttpServletRequest request){
        newsService.deleteById(id);
        return "redirect:/news";
    }
}