package com.example.demo.utils.MapptingUtils;

import com.example.demo.domain.NewsRecord;
import com.example.demo.domain.User;
import com.example.demo.dto.NewsRecordDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.Calendar;

@Component
public class NewsRecordMapper {

    private final ModelMapper mapper;

    @Autowired
    public NewsRecordMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public NewsRecord mapDtoToNewsRecord(NewsRecordDto dto) {
        if(dto == null){
            return null;
        }
        if (dto.getPublicationDate() == null){
            dto.setPublicationDate(new Date(Calendar.getInstance().getTime().getTime()));
        }
        return mapper.map(dto,NewsRecord.class);
    }

    public NewsRecordDto mapNewsRecordToDto(NewsRecord record) {
        return record == null ? new NewsRecordDto() :  mapper.map(record,NewsRecordDto.class);
    }
}
