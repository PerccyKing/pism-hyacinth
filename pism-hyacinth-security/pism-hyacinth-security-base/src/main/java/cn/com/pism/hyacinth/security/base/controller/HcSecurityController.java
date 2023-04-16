package cn.com.pism.hyacinth.security.base.controller;

import cn.com.pism.hyacinth.commons.object.sys.vo.req.HcSysLoginReqVo;
import cn.com.pism.hyacinth.commons.object.sys.vo.resp.HcSysTokenRespVo;
import cn.com.pism.hyacinth.commons.object.vo.HcResult;
import cn.com.pism.hyacinth.security.base.service.HcSecurityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 包含登录、退出接口
 *
 * @author PerccyKing
 * @since 2023/4/8 15:34
 */
@RequestMapping("/security")
@Api("认证授权")
@RestController
public class HcSecurityController {

    @Resource
    private HcSecurityService hcSecurityService;


    /**
     * <p>
     * 用户登录接口
     * </p>
     * by PerccyKing
     *
     * @param loginReqVo : 登录请求参数
     * @return {@link HcResult<HcSysTokenRespVo>}
     * @since 2023/4/8 17:33
     */
    @PostMapping("/login")
    @ApiOperation("用户登录")
    public HcResult<HcSysTokenRespVo> login(@RequestBody HcSysLoginReqVo loginReqVo) {
        return HcResult.success(hcSecurityService.login(loginReqVo));
    }

    /**
     * <p>
     * 退出登录
     * </p>
     * by PerccyKing
     *
     * @since 2023/4/8 17:53
     */
    @GetMapping("/logout")
    public void logout() {
        hcSecurityService.logout();
    }


    /**
     * <p>
     * 获取公钥，用于前端密码加密
     * </p>
     * by PerccyKing
     *
     * @param uniqueId: 唯一id
     * @return {@link HcResult<String>} 公钥
     * @since 2023/4/8 17:43
     */
    @GetMapping("/getPublicKey/{uniqueId}")
    @ApiOperation("获取公钥")
    public HcResult<String> getPublicKey(@ApiParam("唯一id") @PathVariable String uniqueId) {
        return HcResult.success(hcSecurityService.getPublicKey(uniqueId));
    }

    /**
     * <p>
     * 获取公钥加密数据,生产环境，前端不允许调用当前接口
     * </p>
     * by PerccyKing
     *
     * @param uniqueId : 唯一id 用于识别密钥对
     * @return {@link HcResult<String>} 公钥加密后的数据
     * @since 2023/4/15 19:08
     */
    @GetMapping("/publicKeyEncrypt/{uniqueId}")
    @ApiOperation("获取公钥加密数据")
    public HcResult<String> publicKeyEncrypt(@ApiParam("唯一id") @PathVariable String uniqueId,
                                             @ApiParam("代加密数据") @RequestParam("data") String data) {
        return HcResult.success(hcSecurityService.publicKeyEncrypt(uniqueId, data));
    }

}
