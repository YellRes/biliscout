package com.yellres.biliscout.mapper;
import org.apache.ibatis.annotations.Mapper;
import com.yellres.biliscout.entity.ResearchTask;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Insert;
import com.yellres.biliscout.entity.ResearchTaskInsert;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface ResearchTaskMapper {

    @Select("""
        SELECT id, status, created_at AS createdAt, question
        FROM research_task
        WHERE id = #{id}
    """)
    public ResearchTask selectById(@Param("id") Long id);

    @Insert("""
        INSERT INTO research_task (question, status, created_at)
        VALUES (#{question}, #{status}, #{createdAt})
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ResearchTaskInsert task);

}
