import com.example.scoringsystem.ScoringSystemApplication;
import com.example.scoringsystem.bean.Team;
import com.example.scoringsystem.bean.User;
import com.example.scoringsystem.bean.UserVO;
import com.example.scoringsystem.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class JRedisTest {

//    @Autowired
//    private UserMapper userMapper;
//
//    @Test
//    public void testUserMapper()
//    {
//        Team team=new Team();
//        team.setSysTeamName("测试测试");
//        team.setClassRoomId("1");
//        userMapper.insPairTeam(team);
//        System.out.println(team);
//    }
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;  //对字符串支持比较友好,不能存储对象
//    @Autowired
//    private RedisTemplate redisTemplate;  //存储对象
//
//    @Test
//    public void testRedisTemplate(){
//        System.out.println(redisTemplate);
//        //设置redistemplate值使用对象序列化策略
//        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());//指定值使用对象序列化
//        //redisTemplate.opsForValue().set("user",new User("21","小黑",23,new Date()));
//        User user = (User) redisTemplate.opsForValue().get("user");
//        System.out.println(user);
////      Set keys = redisTemplate.keys("*");
////      keys.forEach(key -> System.out.println(key));
//        /*Object name = redisTemplate.opsForValue().get("name");
//        System.out.println(name);*/
//
//        //Object xiaohei = redisTemplate.opsForValue().get("xiaohei");
//        //System.out.println(xiaohei);
//        /*redisTemplate.opsForValue().set("name","xxxx");
//        Object name = redisTemplate.opsForValue().get("name");
//        System.out.println(name);*/
//        /*redisTemplate.opsForList().leftPushAll("lists","xxxx","1111");
//        List lists = redisTemplate.opsForList().range("lists", 0, -1);
//        lists.forEach(list-> System.out.println(list));*/
//    }
//
//
//    //key的绑定操作 如果日后对某一个key的操作及其频繁,可以将这个key绑定到对应redistemplate中,日后基于绑定操作都是操作这个key
//    //boundValueOps 用来对String值绑定key
//    //boundListOps 用来对List值绑定key
//    //boundSetOps 用来对Set值绑定key
//    //boundZsetOps 用来对Zset值绑定key
//    //boundHashOps 用来对Hash值绑定key
//
//    @Test
//    public void testBoundKey(){
//        BoundValueOperations<String, String> nameValueOperations = stringRedisTemplate.boundValueOps("name");
//        nameValueOperations.set("1");
//        //yuew
//        nameValueOperations.set("2");
//        String s = nameValueOperations.get();
//        System.out.println(s);
//
//    }
//
//
//    //hash相关操作 opsForHash
//    @Test
//    public void testHash(){
//        stringRedisTemplate.opsForHash().put("maps","name","小黑");
//        Object o = stringRedisTemplate.opsForHash().get("maps", "name");
//        System.out.println(o);
//    }
//
//    //zset相关操作 opsForZSet
//    @Test
//    public void testZSet(){
//        stringRedisTemplate.opsForZSet().add("zsets","小黑",10);
//        Set<String> zsets = stringRedisTemplate.opsForZSet().range("zsets", 0, -1);
//        zsets.forEach(value-> System.out.println(value));
//    }
//
//    //set相关操作 opsForSet
//    @Test
//    public void testSet(){
//        stringRedisTemplate.opsForSet().add("sets","xiaosan","xiaosi","xiaowu");
//        Set<String> sets = stringRedisTemplate.opsForSet().members("sets");
//        sets.forEach(value-> System.out.println(value));
//    }
//
//    //list相关的操作opsForList
//    @Test
//    public void testList(){
//        // stringRedisTemplate.opsForList().leftPushAll("lists","张三","李四","王五");
//        List<String> lists = stringRedisTemplate.opsForList().range("lists", 0, -1);
//        lists.forEach(key -> System.out.println(key));
//    }
//
//
//    //String相关的操作 opsForValue
//    @Test
//    public void testString(){
//        //stringRedisTemplate.opsForValue().set("166","好同学");
//        String s = stringRedisTemplate.opsForValue().get("166");
//        System.out.println(s);
//        Long size = stringRedisTemplate.opsForValue().size("166");
//        System.out.println(size);
//    }
//
//
//    //key相关的操作
//    @Test
//    public void test(){
//        Set<String> keys = stringRedisTemplate.keys("*");//查看所有key
//        Boolean name = stringRedisTemplate.hasKey("name");//判断某个key是否存在
//        stringRedisTemplate.delete("age");//根据指定key删除
//        stringRedisTemplate.rename("","");//修改key的名称
//        stringRedisTemplate.expire("key",10, TimeUnit.HOURS);
//        //设置key超时时间 参数1:设置key名 参数2:时间 参数3:时间的单位
//        stringRedisTemplate.move("",1);//移动key
//    }
}
