package cn.com.pism.hyacinth.commons.esm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 每个实体都应该有 id，创建时间、更新时间、删除时间、创建人、更新人、逻辑删除标记
 *
 * @author PerccyKing
 * @since 2023/3/4 22:30
 */
@Data
public class HcComEntity implements Serializable {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 创建人id
     */
    @TableField(value = "create_id")
    private Long createId;

    /**
     * 更新人id
     */
    @TableField(value = "update_id")
    private Long updateId;

    /**
     * 逻辑删除标志，not null:删除，null:未删除
     */
    @TableField(value = "dlt_time")
    private Date dltTime;
}
