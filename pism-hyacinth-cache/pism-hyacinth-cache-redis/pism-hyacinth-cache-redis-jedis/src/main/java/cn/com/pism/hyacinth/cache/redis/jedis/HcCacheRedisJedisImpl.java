package cn.com.pism.hyacinth.cache.redis.jedis;

import cn.com.pism.hyacinth.cache.base.HcCache;
import cn.com.pism.hyacinth.cache.redis.base.HcCacheRedis;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;

import static cn.com.pism.hyacinth.commons.object.constant.cache.HcCacheTypeConstant.Redis.JEDIS;
import static cn.com.pism.hyacinth.commons.object.constant.HcSystemConstant.OK;

/**
 * @author PerccyKing
 * @since 2023/3/11 22:55
 */
@Component(JEDIS)
@Slf4j
public class HcCacheRedisJedisImpl implements HcCache, HcCacheRedis, HcCacheRedisJedis {

    @Resource
    private JedisPool jedisPool;


    /**
     * <p>
     * 获取缓存名称
     * </p>
     * by PerccyKing
     *
     * @return {@link String} 缓存名称
     * @since 2023/3/11 23:40
     */

    @Override
    public String getName() {
        return JEDIS;
    }


    /**
     * <p>
     * 通过key获取储存在缓存中的value
     * </p>
     * by PerccyKing
     *
     * @param key : 建
     * @return {@link String} 缓存中的值
     * @since 2023/3/11 17:16
     */

    @Override
    public String get(String key) {
        return jedisRun(jedisPool, jedis -> jedis.get(key), "");
    }

    /**
     * <p>
     * 向缓存存入key和value
     * </p>
     * by PerccyKing
     *
     * @param key   : 键
     * @param value : 值
     * @return {@link boolean} 是否成功
     * @since 2023/3/11 17:14
     */
    @Override
    public boolean set(String key, String value) {
        return jedisRun(jedisPool, jedis -> OK.equals(jedis.set(key, value)), false);
    }

    /**
     * 删除指定的key,也可以传入一个包含key的数组
     *
     * @param keys 一个key 也可以使 string 数组
     * @return 返回删除成功的个数
     */

    @Override
    public Long del(String... keys) {
        return jedisRun(jedisPool, jedis -> jedis.del(keys), 0L);
    }


    /**
     * 通过key向指定的value值追加值
     *
     * @param key 键
     * @param str 值
     * @return 成功返回 添加后value的长度 失败 返回 添加的 value 的长度 异常返回0L
     */

    @Override
    public Long append(String key, String str) {
        return jedisRun(jedisPool, jedis -> jedis.append(key, str), 0L);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true OR false
     */

    @Override
    public Boolean exists(String key) {
        return jedisRun(jedisPool, jedis -> jedis.exists(key), Boolean.FALSE);
    }

    /**
     * <p>
     * 清空缓存
     * </p>
     * by PerccyKing
     *
     * @return {@link boolean} true 清空成功
     * @since 2023/3/12 22:57
     */

    @Override
    public boolean clear() {
        return flushDb();
    }

    /**
     * 清空当前数据库中的所有 key,此命令从不失败。
     *
     * @return 总是返回 OK
     */
    @Override
    public Boolean flushDb() {
        return jedisRun(jedisPool, jedis -> OK.equals(jedis.flushDB()), Boolean.FALSE);
    }

    /**
     * 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。
     *
     * @param key 键
     * @param exp 过期时间，单位：秒
     * @return 成功返回1 如果存在 和 发生异常 返回 0
     */

    @Override
    public Long expire(String key, long exp) {
        return jedisRun(jedisPool, jedis -> jedis.expire(key, exp), 0L);
    }

    /**
     * 以秒为单位，返回给定 key 的剩余生存时间
     *
     * @param key 键
     * @return 当 key 不存在时，返回 -2 。当 key 存在但没有设置剩余生存时间时，返回 -1 。否则，以秒为单位，返回 key
     * 的剩余生存时间。 发生异常 返回 0
     */

    @Override
    public Long ttl(String key) {
        return jedisRun(jedisPool, jedis -> jedis.ttl(key), 0L);
    }

    /**
     * <p>
     * 设置key value,如果key已经存在则返回0,nx==> not exist
     * </p>
     *
     * @param key   键
     * @param value 值
     * @return 成功返回1 如果存在 和 发生异常 返回 0
     */

    @Override
    public Long setNx(String key, String value) {
        return jedisRun(jedisPool, jedis -> jedis.setnx(key, value), 0L);
    }

    /**
     * <p>
     * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)。
     * </p>
     * <p>
     * 当 key 存在但不是字符串类型时，返回一个错误。
     * </p>
     *
     * @param key   键
     * @param value 值
     * @return 返回给定 key 的旧值。当 key 没有旧值时，也即是， key 不存在时，返回 nil
     */

    @Override
    public String getSet(String key, String value) {
        return jedisRun(jedisPool, jedis -> jedis.getSet(key, value), "");
    }

    /**
     * <p>
     * 设置key value并制定这个键值的有效期
     * </p>
     *
     * @param key     键
     * @param value   值
     * @param seconds 单位:秒
     * @return 成功返回OK 失败和异常返回null
     */

    @Override
    public Boolean setEx(String key, String value, long seconds) {
        return jedisRun(jedisPool, jedis -> OK.equals(jedis.setex(key, seconds, value)), Boolean.FALSE);
    }

    /**
     * <p>
     * 通过key 和offset 从指定的位置开始将原先value替换
     * </p>
     * <p>
     * 下标从0开始,offset表示从offset下标开始替换
     * </p>
     * <p>
     * 如果替换的字符串长度过小则会这样
     * </p>
     * <p>
     * example:
     * </p>
     * <p>
     * value : bigsea@zto.cn
     * </p>
     * <p>
     * str : abc
     * </p>
     * <P>
     * 从下标7开始替换 则结果为
     * </p>
     * <p>
     * RES : bigsea.abc.cn
     * </p>
     *
     * @param key    键
     * @param str    值
     * @param offset 下标位置
     * @return 返回替换后 value 的长度
     */

    @Override
    public Long setRange(String key, String str, int offset) {
        return jedisRun(jedisPool, jedis -> jedis.setrange(key, offset, str), 0L);
    }

    /**
     * <p>
     * 通过批量的key获取批量的value
     * </p>
     *
     * @param keys string数组 也可以是一个key
     * @return 成功返回value的集合, 失败返回null的集合 ,异常返回空
     */

    @Override
    public List<String> mGet(String... keys) {
        return jedisRun(jedisPool, jedis -> jedis.mget(keys), Collections.emptyList());
    }

    /**
     * <p>
     * 批量的设置key:value,可以一个
     * </p>
     * <p>
     * example:
     * </p>
     * <p>
     * obj.mset(new String[]{"key2","value1","key2","value2"})
     * </p>
     *
     * @param keysValues 键值对
     * @return 成功返回OK 失败 异常 返回 null
     */

    @Override
    public Boolean mSet(String... keysValues) {
        return jedisRun(jedisPool, jedis -> OK.equals(jedis.mset(keysValues)), Boolean.FALSE);
    }

    /**
     * <p>
     * 批量的设置key:value,可以一个,如果key已经存在则会失败,操作会回滚
     * </p>
     * <p>
     * example:
     * </p>
     * <p>
     * obj.msetnx(new String[]{"key2","value1","key2","value2"})
     * </p>
     *
     * @param keysValues 键值对
     * @return 成功返回1 失败返回0
     */

    @Override
    public Long mSetNx(String... keysValues) {
        return jedisRun(jedisPool, jedis -> jedis.msetnx(keysValues), 0L);
    }

    /**
     * <p>
     * 通过下标 和key 获取指定下标位置的 value
     * </p>
     *
     * @param key         键
     * @param startOffset 开始位置 从0 开始 负数表示从右边开始截取
     * @param endOffset   结束位置
     * @return 如果没有返回null
     */

    @Override
    public String getRange(String key, int startOffset, int endOffset) {
        return jedisRun(jedisPool, jedis -> jedis.getrange(key, startOffset, endOffset), "");
    }

    /**
     * <p>
     * 通过key 对value进行加值+1操作,当value不是int类型时会返回错误,当key不存在是则value为1
     * </p>
     *
     * @param key 键
     * @return 加值后的结果
     */

    @Override
    public Long incr(String key) {
        return jedisRun(jedisPool, jedis -> jedis.incr(key), 0L);
    }

    /**
     * <p>
     * 通过key给指定的value加值,如果key不存在,则这是value为该值
     * </p>
     *
     * @param key     键
     * @param integer 增量
     * @return 结果
     */

    @Override
    public Long incrBy(String key, Long integer) {
        return jedisRun(jedisPool, jedis -> jedis.incrBy(key, integer), 0L);
    }

    /**
     * <p>
     * 对key的值做减减操作,如果key不存在,则设置key为-1
     * </p>
     *
     * @param key 键
     * @return return
     */

    @Override
    public Long decr(String key) {
        return jedisRun(jedisPool, jedis -> jedis.decr(key), 0L);
    }

    /**
     * <p>
     * 减去指定的值
     * </p>
     *
     * @param key     param
     * @param integer param
     * @return return
     */

    @Override
    public Long decrBy(String key, Long integer) {
        return jedisRun(jedisPool, jedis -> jedis.decrBy(key, integer), 0L);
    }

    /**
     * <p>
     * 通过key获取value值的长度
     * </p>
     *
     * @param key param
     * @return 失败返回null
     */

    @Override
    public Long strLen(String key) {
        return jedisRun(jedisPool, jedis -> jedis.strlen(key), 0L);
    }

    /**
     * <p>
     * 通过key给field设置指定的值,如果key不存在,则先创建
     * </p>
     *
     * @param key   param
     * @param field 字段
     * @param value param
     * @return 如果存在返回0 异常返回null
     */

    @Override
    public Long hSet(String key, String field, String value) {
        return jedisRun(jedisPool, jedis -> jedis.hset(key, field, value), 0L);
    }

    /**
     * <p>
     * 通过key给field设置指定的值,如果key不存在则先创建,如果field已经存在,返回0
     * </p>
     *
     * @param key   key
     * @param field field
     * @param value value
     * @return return
     */

    @Override
    public Long hSetNx(String key, String field, String value) {
        return jedisRun(jedisPool, jedis -> jedis.hsetnx(key, field, value), 0L);
    }

    /**
     * <p>
     * 通过key同时设置 hash的多个field
     * </p>
     *
     * @param key  key
     * @param hash hash
     * @return 返回OK 异常返回null
     */

    @Override
    public Boolean hmSet(String key, Map<String, String> hash) {
        return jedisRun(jedisPool, jedis -> OK.equals(jedis.hmset(key, hash)), Boolean.FALSE);
    }

    /**
     * <p>
     * 通过key 和 field 获取指定的 value
     * </p>
     *
     * @param key   key
     * @param field field
     * @return 没有返回null
     */

    @Override
    public String hGet(String key, String field) {
        return jedisRun(jedisPool, jedis -> jedis.hget(key, field), "");
    }

    /**
     * <p>
     * 通过key 和 fields 获取指定的value 如果没有对应的value则返回null
     * </p>
     *
     * @param key    param
     * @param fields 可以是 一个String 也可以是 String数组
     * @return return
     */

    @Override
    public List<String> hmGet(String key, String... fields) {
        return jedisRun(jedisPool, jedis -> jedis.hmget(key, fields), Collections.emptyList());
    }

    /**
     * <p>
     * 通过key和field判断是否有指定的value存在
     * </p>
     *
     * @param key   param
     * @param field param
     * @return return
     */

    @Override
    public Boolean hExists(String key, String field) {
        return jedisRun(jedisPool, jedis -> jedis.hexists(key, field), Boolean.FALSE);
    }

    /**
     * <p>
     * 通过key返回field的数量
     * </p>
     *
     * @param key param
     * @return return
     */

    @Override
    public Long hLen(String key) {
        return jedisRun(jedisPool, jedis -> jedis.hlen(key), 0L);
    }

    /**
     * <p>
     * 通过key 删除指定的 field
     * </p>
     *
     * @param key    param
     * @param fields 可以是 一个 field 也可以是 一个数组
     * @return return
     */

    @Override
    public Long hDel(String key, String... fields) {
        return jedisRun(jedisPool, jedis -> jedis.hdel(key, fields), 0L);
    }

    /**
     * <p>
     * 通过key返回所有的field
     * </p>
     *
     * @param key param
     * @return return
     */

    @Override
    public Set<String> hKeys(String key) {
        return jedisRun(jedisPool, jedis -> jedis.hkeys(key), Collections.emptySet());
    }

    /**
     * <p>
     * 通过key返回所有和key有关的value
     * </p>
     *
     * @param key param
     * @return return
     */

    @Override
    public List<String> hVals(String key) {
        return jedisRun(jedisPool, jedis -> jedis.hvals(key), Collections.emptyList());
    }

    /**
     * <p>
     * 通过key获取所有的field和value
     * </p>
     *
     * @param key param
     * @return return
     */

    @Override
    public Map<String, String> hGetAll(String key) {
        return jedisRun(jedisPool, jedis -> jedis.hgetAll(key), Collections.emptyMap());
    }


    /**
     * <p>
     * 通过key向list头部添加字符串
     * </p>
     *
     * @param key  param
     * @param strs 可以使一个string 也可以使string数组
     * @return 返回list的value个数
     */
    @Override
    public Long lPush(String key, String... strs) {
        return jedisRun(jedisPool, jedis -> jedis.lpush(key, strs), 0L);
    }

    /**
     * <p>
     * 通过key向list尾部添加字符串
     * </p>
     *
     * @param key  param
     * @param strs 可以使一个string 也可以使string数组
     * @return 返回list的value个数
     */
    @Override
    public Long rPush(String key, String... strs) {
        return jedisRun(jedisPool, jedis -> jedis.rpush(key, strs), 0L);
    }

    /**
     * <p>
     * 通过key设置list指定下标位置的value
     * </p>
     * <p>
     * 如果下标超过list里面value的个数则报错
     * </p>
     *
     * @param key   param
     * @param index 从0开始
     * @param value param
     * @return 成功返回OK
     */
    @Override
    public Boolean lSet(String key, Long index, String value) {
        return jedisRun(jedisPool, jedis -> OK.equals(jedis.lset(key, index, value)), Boolean.FALSE);
    }

    /**
     * <p>
     * 通过key从对应的list中删除指定的count个 和 value相同的元素
     * </p>
     *
     * @param key   param
     * @param count 当count为0时删除全部
     * @param value param
     * @return 返回被删除的个数
     */
    @Override
    public Long lRem(String key, long count, String value) {
        return jedisRun(jedisPool, jedis -> jedis.lrem(key, count, value), 0L);
    }

    /**
     * <p>
     * 通过key保留list中从strat下标开始到end下标结束的value值
     * </p>
     *
     * @param key   param
     * @param start param
     * @param end   param
     * @return 成功返回OK
     */
    @Override
    public Boolean lTrim(String key, long start, long end) {
        return jedisRun(jedisPool, jedis -> OK.equals(jedis.ltrim(key, start, end)), Boolean.FALSE);
    }

    /**
     * <p>
     * 通过key从list的头部删除一个value,并返回该value
     * </p>
     *
     * @param key param
     * @return return
     */
    @Override
    public synchronized String lPop(String key) {
        return jedisRun(jedisPool, jedis -> jedis.lpop(key), "");
    }

    /**
     * <p>
     * 通过key从list尾部删除一个value,并返回该元素
     * </p>
     *
     * @param key param
     * @return return
     */
    @Override
    public synchronized String rPop(String key) {
        return jedisRun(jedisPool, jedis -> jedis.rpop(key), "");
    }

    /**
     * <p>
     * 通过key从一个list的尾部删除一个value并添加到另一个list的头部,并返回该value
     * </p>
     * <p>
     * 如果第一个list为空或者不存在则返回null
     * </p>
     *
     * @param srcKey param
     * @param dstKey param
     * @return return
     */
    @Override
    public String rightPopLeftPush(String srcKey, String dstKey) {
        return jedisRun(jedisPool, jedis -> jedis.rpoplpush(srcKey, dstKey), "");
    }

    /**
     * <p>
     * 通过key获取list中指定下标位置的value
     * </p>
     *
     * @param key   param
     * @param index param
     * @return 如果没有返回null
     */
    @Override
    public String lIndex(String key, long index) {
        return jedisRun(jedisPool, jedis -> jedis.lindex(key, index), "");
    }

    /**
     * <p>
     * 通过key返回list的长度
     * </p>
     *
     * @param key param
     * @return return
     */
    @Override
    public Long lLen(String key) {
        return jedisRun(jedisPool, jedis -> jedis.llen(key), 0L);
    }

    /**
     * <p>
     * 通过key获取list指定下标位置的value
     * </p>
     * <p>
     * 如果start 为 0 end 为 -1 则返回全部的list中的value
     * </p>
     *
     * @param key   param
     * @param start param
     * @param end   param
     * @return return
     */
    @Override
    public List<String> lRange(String key, long start, long end) {
        return jedisRun(jedisPool, jedis -> jedis.lrange(key, start, end), Collections.emptyList());
    }

    /**
     * <p>
     * 将列表 key 下标为 index 的元素的值设置为 value
     * </p>
     *
     * @param key   param
     * @param index param
     * @param value param
     * @return 操作成功返回 ok ，否则返回错误信息
     */
    @Override
    public Boolean lSet(String key, long index, String value) {
        return jedisRun(jedisPool, jedis -> OK.equals(jedis.lset(key, index, value)), Boolean.FALSE);
    }

    /**
     * <p>
     * 返回排序后的结果，排序默认以数字作为对象，值被解释为双精度浮点数，然后进行比较。
     * </p>
     *
     * @param key param
     * @return 返回列表形式的排序结果
     */
    @Override
    public List<String> sort(String key) {
        return jedisRun(jedisPool, jedis -> jedis.sort(key), Collections.emptyList());
    }

    /**
     * <p>
     * 通过key向指定的set中添加value
     * </p>
     *
     * @param key     param
     * @param members 可以是一个String 也可以是一个String数组
     * @return 添加成功的个数
     */
    @Override
    public Long sAdd(String key, String... members) {
        return jedisRun(jedisPool, jedis -> jedis.sadd(key, members), 0L);
    }

    /**
     * <p>
     * 通过key删除set中对应的value值
     * </p>
     *
     * @param key     param
     * @param members 可以是一个String 也可以是一个String数组
     * @return 删除的个数
     */
    @Override
    public Long sRem(String key, String... members) {
        return jedisRun(jedisPool, jedis -> jedis.srem(key, members), 0L);
    }

    /**
     * <p>
     * 通过key随机删除一个set中的value并返回该值
     * </p>
     *
     * @param key param
     * @return return
     */
    @Override
    public String sPop(String key) {
        return jedisRun(jedisPool, jedis -> jedis.spop(key), "");
    }

    /**
     * <p>
     * 通过key获取set中的差集
     * </p>
     * <p>
     * 以第一个set为标准
     * </p>
     *
     * @param keys 可以使一个string 则返回set中所有的value 也可以是string数组
     * @return return
     */
    @Override
    public Set<String> sDiff(String... keys) {
        return jedisRun(jedisPool, jedis -> jedis.sdiff(keys), Collections.emptySet());
    }

    /**
     * <p>
     * 通过key获取set中的差集并存入到另一个key中
     * </p>
     * <p>
     * 以第一个set为标准
     * </p>
     *
     * @param dstkey 差集存入的key
     * @param keys   可以使一个string 则返回set中所有的value 也可以是string数组
     * @return return
     */
    @Override
    public Long sDiffStore(String dstkey, String... keys) {
        return jedisRun(jedisPool, jedis -> jedis.sdiffstore(dstkey, keys), 0L);
    }

    /**
     * <p>
     * 通过key获取指定set中的交集
     * </p>
     *
     * @param keys 可以使一个string 也可以是一个string数组
     * @return return
     */
    @Override
    public Set<String> sInter(String... keys) {
        return jedisRun(jedisPool, jedis -> jedis.sinter(keys), Collections.emptySet());
    }

    /**
     * <p>
     * 通过key获取指定set中的交集 并将结果存入新的set中
     * </p>
     *
     * @param dstkey param
     * @param keys   可以使一个string 也可以是一个string数组
     * @return return
     */
    @Override
    public Long sInterStore(String dstkey, String... keys) {
        return jedisRun(jedisPool, jedis -> jedis.sinterstore(dstkey, keys), 0L);
    }

    /**
     * <p>
     * 通过key返回所有set的并集
     * </p>
     *
     * @param keys 可以使一个string 也可以是一个string数组
     * @return return
     */
    @Override
    public Set<String> sUnion(String... keys) {
        return jedisRun(jedisPool, jedis -> jedis.sunion(keys), Collections.emptySet());
    }

    /**
     * <p>
     * 通过key返回所有set的并集,并存入到新的set中
     * </p>
     *
     * @param dstKey param
     * @param keys   可以使一个string 也可以是一个string数组
     * @return return
     */
    @Override
    public Long sUnionStore(String dstKey, String... keys) {
        return jedisRun(jedisPool, jedis -> jedis.sunionstore(dstKey, keys), 0L);
    }

    /**
     * <p>
     * 通过key将set中的value移除并添加到第二个set中
     * </p>
     *
     * @param srcKey 需要移除的
     * @param dstKey 添加的
     * @param member set中的value
     * @return return
     */
    @Override
    public Long sMove(String srcKey, String dstKey, String member) {
        return jedisRun(jedisPool, jedis -> jedis.smove(srcKey, dstKey, member), 0L);
    }

    /**
     * <p>
     * 通过key获取set中value的个数
     * </p>
     *
     * @param key param
     * @return return
     */
    @Override
    public Long sCard(String key) {
        return jedisRun(jedisPool, jedis -> jedis.scard(key), 0L);
    }

    /**
     * <p>
     * 通过key判断value是否是set中的元素
     * </p>
     *
     * @param key    param
     * @param member param
     * @return return
     */
    @Override
    public Boolean sIsMember(String key, String member) {
        return jedisRun(jedisPool, jedis -> jedis.sismember(key, member), Boolean.FALSE);
    }

    /**
     * <p>
     * 通过key获取set中随机的value,不删除元素
     * </p>
     *
     * @param key key
     * @return return
     */
    @Override
    public String sRandMember(String key) {
        return jedisRun(jedisPool, jedis -> jedis.srandmember(key), "");
    }

    /**
     * <p>
     * 通过key获取set中所有的value
     * </p>
     *
     * @param key key
     * @return return
     */
    @Override
    public Set<String> sMembers(String key) {
        return jedisRun(jedisPool, jedis -> jedis.smembers(key), Collections.emptySet());
    }

    /**
     * <p>
     * 通过key向zset中添加value,score,其中score就是用来排序的
     * </p>
     * <p>
     * 如果该value已经存在则根据score更新元素
     * </p>
     *
     * @param key    key
     * @param score  score
     * @param member member
     * @return return
     */
    @Override
    public Long zAdd(String key, double score, String member) {
        return jedisRun(jedisPool, jedis -> jedis.zadd(key, score, member), 0L);
    }

    /**
     * <p>
     * 返回有序集 key 中，指定区间内的成员。min=0,max=-1代表所有元素
     * </p>
     *
     * @param key key
     * @param min min
     * @param max max
     * @return 指定区间内的有序集成员的列表。
     */
    @Override
    public List<String> zRange(String key, long min, long max) {
        return jedisRun(jedisPool, jedis -> jedis.zrange(key, min, max), Collections.emptyList());
    }

    /**
     * <p>
     * 统计有序集 key 中,值在 min 和 max 之间的成员的数量
     * </p>
     *
     * @param key key
     * @param min min
     * @param max max
     * @return 值在 min 和 max 之间的成员的数量。异常返回0
     */
    @Override
    public Long zCount(String key, double min, double max) {
        return jedisRun(jedisPool, jedis -> jedis.zcount(key, min, max), 0L);
    }

    /**
     * <p>
     * 为哈希表 key 中的域 field 的值加上增量 increment 。增量也可以为负数，相当于对给定域进行减法操作。 如果 key
     * 不存在，一个新的哈希表被创建并执行 HINCRBY 命令。如果域 field 不存在，那么在执行命令前，域的值被初始化为 0 。
     * 对一个储存字符串值的域 field 执行 HINCRBY 命令将造成一个错误。本操作的值被限制在 64 位(bit)有符号数字表示之内。
     * </p>
     * <p>
     * 将名称为key的hash中field的value增加integer
     * </p>
     *
     * @param key       键
     * @param value     值
     * @param increment increment
     * @return 执行 HINCRBY 命令之后，哈希表 key 中域 field的值。异常返回0
     */

    @Override
    public Long hinCrBy(String key, String value, long increment) {
        return jedisRun(jedisPool, jedis -> jedis.hincrBy(key, value, increment), 0L);

    }

    /**
     * <p>
     * 通过key删除在zset中指定的value
     * </p>
     *
     * @param key     key
     * @param members 可以使一个string 也可以是一个string数组
     * @return return
     */
    @Override
    public Long zRem(String key, String... members) {
        return jedisRun(jedisPool, jedis -> jedis.zrem(key, members), 0L);
    }

    /**
     * <p>
     * 通过key增加该zset中value的score的值
     * </p>
     *
     * @param key    key
     * @param score  score
     * @param member member
     * @return return
     */
    @Override
    public Double zIncrBy(String key, double score, String member) {
        return jedisRun(jedisPool, jedis -> jedis.zincrby(key, score, member), 0D);
    }

    /**
     * <p>
     * 通过key返回zset中value的排名
     * </p>
     * <p>
     * 下标从小到大排序
     * </p>
     *
     * @param key    key
     * @param member member
     * @return return
     */
    @Override
    public Long zRank(String key, String member) {
        return jedisRun(jedisPool, jedis -> jedis.zrank(key, member), 0L);
    }

    /**
     * <p>
     * 通过key返回zset中value的排名
     * </p>
     * <p>
     * 下标从大到小排序
     * </p>
     *
     * @param key    key
     * @param member member
     * @return return
     */
    @Override
    public Long zRevRank(String key, String member) {
        return jedisRun(jedisPool, jedis -> jedis.zrevrank(key, member), 0L);
    }

    /**
     * <p>
     * 通过key将获取score从start到end中zset的value
     * </p>
     * <p>
     * socre从大到小排序
     * </p>
     * <p>
     * 当start为0 end为-1时返回全部
     * </p>
     *
     * @param key   key
     * @param start start
     * @param end   end
     * @return return
     */
    @Override
    public List<String> zRevRange(String key, long start, long end) {
        return jedisRun(jedisPool, jedis -> jedis.zrevrange(key, start, end), Collections.emptyList());
    }

    /**
     * <p>
     * 通过key返回指定score内zset中的value
     * </p>
     *
     * @param key key
     * @param max max
     * @param min min
     * @return return
     */
    @Override
    public List<String> zRangeByScore(String key, String max, String min) {
        return jedisRun(jedisPool, jedis -> jedis.zrangeByScore(key, max, min), Collections.emptyList());
    }

    /**
     * <p>
     * 通过key返回指定score内zset中的value
     * </p>
     *
     * @param key 键
     * @param max 最大值
     * @param min 最小值
     * @return return
     */
    @Override
    public List<String> zRevRangeByScore(String key, double max, double min) {
        return jedisRun(jedisPool, jedis -> jedis.zrevrangeByScore(key, max, min), Collections.emptyList());
    }

    /**
     * <p>
     * 返回指定区间内zset中value的数量
     * </p>
     *
     * @param key key
     * @param min min
     * @param max max
     * @return return
     */
    @Override
    public Long zCount(String key, String min, String max) {
        return jedisRun(jedisPool, jedis -> jedis.zcount(key, min, max), 0L);
    }

    /**
     * <p>
     * 通过key返回zset中的value个数
     * </p>
     *
     * @param key key
     * @return return
     */
    @Override
    public Long zCard(String key) {
        return jedisRun(jedisPool, jedis -> jedis.zcard(key), 0L);
    }

    /**
     * <p>
     * 通过key获取zset中value的score值
     * </p>
     *
     * @param key    key
     * @param member member
     * @return return
     */
    @Override
    public Double zScore(String key, String member) {
        return jedisRun(jedisPool, jedis -> jedis.zscore(key, member), 0D);
    }

    /**
     * <p>
     * 通过key删除给定区间内的元素
     * </p>
     *
     * @param key   key
     * @param start start
     * @param end   end
     * @return return
     */
    @Override
    public Long zRemRangeByRank(String key, long start, long end) {
        return jedisRun(jedisPool, jedis -> jedis.zremrangeByRank(key, start, end), 0L);
    }

    /**
     * <p>
     * 通过key删除指定score内的元素
     * </p>
     *
     * @param key   key
     * @param start start
     * @param end   end
     * @return return
     */
    @Override
    public Long zRemRangeByScore(String key, double start, double end) {
        return jedisRun(jedisPool, jedis -> jedis.zremrangeByScore(key, start, end), 0L);
    }

    /**
     * <p>
     * 返回满足pattern表达式的所有key
     * </p>
     * <p>
     * keys(*)
     * </p>
     * <p>
     * 返回所有的key
     * </p>
     *
     * @param pattern pattern
     * @return keys
     */
    @Override
    public Set<String> keys(String pattern) {
        return jedisRun(jedisPool, jedis -> jedis.keys(pattern), Collections.emptySet());
    }


    /**
     * <p>
     * 通过key判断值得类型
     * </p>
     *
     * @param key 键
     * @return 类型
     */
    @Override
    public String type(String key) {
        return jedisRun(jedisPool, jedis -> jedis.type(key), "");
    }

    /**
     * 序列化对象
     *
     * @param obj 需要序列化的独享
     * @return 对象需实现Serializable接口
     */
    public static byte[] objToSerialize(Object obj) {
        ObjectOutputStream oos;
        ByteArrayOutputStream byteOut;
        try {
            byteOut = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(byteOut);
            oos.writeObject(obj);
            return byteOut.toByteArray();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new byte[0];
    }

    /**
     * 反序列化对象
     *
     * @param bytes bytes
     * @return 对象需实现Serializable接口
     */
    public static Object unSerialize(byte[] bytes) {
        ByteArrayInputStream bais;
        try {
            //反序列化
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * <p>
     * 获取jedis实例
     * </p>
     * by PerccyKing
     *
     * @return {@link Jedis} jedis实例
     * @since 2023/3/11 23:17
     */

    @Override
    public Jedis getInstance() {
        return jedisPool.getResource();
    }

    /**
     * <p>
     * 连接redis
     * </p>
     * by PerccyKing
     *
     * @param jedisPool  : jedis连接池
     * @param doFunction : 执行的方法
     * @param defaultVal : 默认值
     * @return {@link T} 返回参数
     * @since 2023/3/13 0:51
     */
    public static <T> T jedisRun(JedisPool jedisPool, Function<Jedis, T> doFunction, T defaultVal) {
        AtomicReference<T> res = new AtomicReference<>(defaultVal);
        doConnect(jedisPool, jedis -> res.set(doFunction.apply(jedis)), null);
        return res.get();
    }

    /**
     * <p>
     * 连接redis 并执行命令，最后释放连接
     * </p>
     * by PerccyKing
     *
     * @param jedisConsumer : jedis的命令执行逻辑
     * @param onError       : 异常回调
     * @since 2023/3/12 13:07
     */
    public static void doConnect(JedisPool jedisPool, Consumer<Jedis> jedisConsumer, Consumer<Exception> onError) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedisConsumer.accept(jedis);
        } catch (Exception e) {
            if (onError != null) {
                onError.accept(e);
            }
            log.error(e.getMessage());
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 返还到连接池
     *
     * @param jedis jedis
     */
    public static void returnResource(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}
