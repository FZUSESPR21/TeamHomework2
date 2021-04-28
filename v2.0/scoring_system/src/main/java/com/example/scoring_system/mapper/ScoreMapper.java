package com.example.scoring_system.mapper;

import com.example.scoring_system.bean.Details;
import com.example.scoring_system.bean.DetailsData;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ScoreMapper {
    Integer insDetailsBatch(List<DetailsData> detailsList);
}
