package cn.com.pism.hyacinth.commons.esm.service.impl;

import cn.com.pism.hyacinth.commons.esm.mapper.HcComMapper;
import cn.com.pism.hyacinth.commons.esm.service.HcComService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @author PerccyKing
 * @since 2023/3/5 11:24
 */
public class HcComServiceImpl<M extends HcComMapper<E>, E> extends ServiceImpl<M, E> implements HcComService<E> {
}
