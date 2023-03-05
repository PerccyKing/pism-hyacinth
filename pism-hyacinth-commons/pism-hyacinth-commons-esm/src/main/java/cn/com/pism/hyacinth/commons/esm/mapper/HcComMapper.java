package cn.com.pism.hyacinth.commons.esm.mapper;

import cn.com.pism.hyacinth.annotation.HcEntityDefaultVal;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author PerccyKing
 * @since 2023/3/4 22:58
 */
public interface HcComMapper<T> extends BaseMapper<T> {

    /**
     * 插入一条记录
     *
     * @param entity 实体对象
     * @return 插入条数
     */
    @Override
    @HcEntityDefaultVal
    int insert(T entity);

    /**
     * 根据 ID 修改
     *
     * @param entity 实体对象
     * @return 更新行数
     */
    @HcEntityDefaultVal
    @Override
    int updateById(T entity);

    /**
     * 根据 whereEntity 条件，更新记录
     *
     * @param entity        实体对象 (set 条件值,可以为 null)
     * @param updateWrapper 实体对象封装操作类（可以为 null,里面的 entity 用于生成 where 语句）
     * @return int 更新行数
     */
    @Override
    @HcEntityDefaultVal
    int update(T entity, Wrapper<T> updateWrapper);
}
