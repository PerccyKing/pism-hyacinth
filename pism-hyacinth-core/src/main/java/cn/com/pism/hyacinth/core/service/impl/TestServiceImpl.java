package cn.com.pism.hyacinth.core.service.impl;

import cn.com.pism.hyacinth.cache.base.HcCache;
import cn.com.pism.hyacinth.core.service.TestService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import static cn.com.pism.hyacinth.commons.object.constant.HcSystemConstant.HC_CACHE_DEFAULT_INSTANCE;

/**
 * @author PerccyKing
 * @since 2023/3/11 23:44
 */
@Service
public class TestServiceImpl implements TestService {

    @Resource(name = HC_CACHE_DEFAULT_INSTANCE)
    private HcCache hcCache;

    public TestServiceImpl() {
        System.out.println("ccc");
    }

    @Override
    public void test() {
        hcCache.set("key", "32ref");
        String s = hcCache.get("key");
        System.out.println(s);
    }
}
