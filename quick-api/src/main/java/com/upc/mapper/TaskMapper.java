package com.upc.mapper;

import com.upc.entity.Task;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.upc.entity.TaskDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lww
 * @since 2024-07-20
 */
@Mapper
public interface TaskMapper extends BaseMapper<Task> {

    //根据publishUserId查订单
    @Select("select * from task where publishUserId=#{publishUserId}")
    public Task getTaskByPublishUserId(Integer publishUserId);


    @Select("select * from task where state=0")
    List<Task> getAllTaskUndo();

    @Select("SELECT DATE_FORMAT(endTime, '%Y-%m-%d') AS time, COUNT(*) AS num " +
            "FROM task " +
            "WHERE endTime BETWEEN #{startTime} AND #{endTime} " +
            "GROUP BY DATE_FORMAT(endTime   , '%Y-%m-%d')")
    List<TaskDTO> getTaskCount(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Update("update task set state = 1 ,taketime=#{nowtime},acceptUserId=#{acceptUserId} where id = #{taskId}")
    public int updateTaskState1(Integer acceptUserId,@Param("taskId") Integer taskId, LocalDateTime nowtime);
    @Update("update task set state = 2 where id = #{taskId}")
    public int updateTaskState2(@Param("taskId") Integer taskId);
    @Update("update task set state = 3 where id = #{taskId}")
    public int updateTaskState3(@Param("taskId") Integer taskId);

    @Select("select * from task where state=1 and acceptUserId=#{acceptUserId}")
    List<Task> getAcceptTaskDiverse1(Integer acceptUserId);
    @Select("select * from task where (state=2 or state=3) and acceptUserId=#{acceptUserId}")
    List<Task> getAcceptTaskDiverse2(Integer acceptUserId);
    @Select("select * from task where state!=0 and acceptUserId = #{acceptUserId}")
    List<Task> getAcceptTaskDiverse3(Integer acceptUserId);

    @Select("select * from task where state=1 and publishUserId=#{publishUserId}")
    List<Task> getPublishTaskDiverse1(Integer publishUserId);
    @Select("select * from task where state=2 and publishUserId=#{publishUserId}")
    List<Task> getPublishTaskDiverse2(Integer publishUserId);
    @Select("select * from task where state=3 and publishUserId=#{publishUserId}")
    List<Task> getPublishTaskDiverse3(Integer publishUserId);
    @Select("select * from task where state=0 and publishUserId=#{publishUserId}")
    List<Task> getPublishTaskDiverse4(Integer publishUserId);


    @Select("select count(*) from task where state=1 and acceptUserId=#{userId}")
    int getIngTaskNum(int userId);

    @Select("select count(*) from task where state=2 and acceptUserId=#{userId}")
    int getedTaskNum(int userId);

    @Select("select count(*) from task where state=0 and acceptUserId=#{userId}")
    int getunTaskNum(int userId);
    @Select("select count(*) from task where state=3 and acceptUserId=#{userId}")
    int getabTaskNum(int userId);

    @Select("select count(*) from task where state!=0 and acceptUserId = #{acceptUserId}")
    Integer getAcceptTaskTotal(Integer acceptUserId);

    @Select("select count(*) from task where state=0")
    Integer getAllTaskUndoTotal();
    @Select("select count(*) from task where state=1")
    Integer getAllTask1();
    @Select("select count(*) from task where state=2")
    Integer getAllTask2();
    @Select("select count(*) from task where state=3")
    Integer getAllTask3();

    @Select("select count(*) from task where publishUserId = #{publishUserId}")
    Integer getPublishTaskTotal(Integer publishUserId);
}
