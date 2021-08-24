package com.example.demo.utils.MapptingUtils;

import com.example.demo.domain.Course;
import com.example.demo.domain.Module;
import com.example.demo.domain.NewsRecord;
import com.example.demo.domain.User;
import com.example.demo.dto.ModuleDto;
import com.example.demo.dto.NewsRecordDto;

import java.sql.Date;
import java.util.Calendar;

public class NewsRecordMapper {

    public static NewsRecord mapDtoToNewsRecord(NewsRecordDto dto, User user) {
        if (dto.getPublicationDate() == null){
            dto.setPublicationDate(new Date(Calendar.getInstance().getTime().getTime()));
        }
        return new NewsRecord(dto.getId(), user, dto.getTitle(),dto.getPublicationDate(), dto.getTag());
    }

    public static NewsRecordDto mapNewsRecordToDto(NewsRecord record) {
        if (record.getAuthor() != null) {
            return new NewsRecordDto(record.getId(), record.getAuthor().getId(),record.getTitle(),record.getPublicationDate(),record.getTag());
        }
        return new NewsRecordDto(record.getId(), null,record.getTitle(),record.getPublicationDate(),record.getTag());
    }
}
