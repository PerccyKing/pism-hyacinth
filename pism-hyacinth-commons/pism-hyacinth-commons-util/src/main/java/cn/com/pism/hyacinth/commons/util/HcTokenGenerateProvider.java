package cn.com.pism.hyacinth.commons.util;

/**
 * @author PerccyKing
 * @since 2023/4/16 13:28
 */
@FunctionalInterface
public interface HcTokenGenerateProvider {

    /**
     * <p>
     * 生成token
     * </p>
     * by PerccyKing
     *
     * @param id : 登录用户id
     * @return {@link String} token
     * @since 2023/4/16 16:24
     */
    String generateToken(Object id);
}
