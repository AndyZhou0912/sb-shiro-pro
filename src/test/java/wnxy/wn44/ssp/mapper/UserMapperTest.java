package wnxy.wn44.ssp.mapper;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import wnxy.wn44.ssp.entity.User;

public class UserMapperTest {

    private static UserMapper mapper;

    @BeforeClass
    public static void setUpMybatisDatabase() {
        SqlSessionFactory builder = new SqlSessionFactoryBuilder().build(UserMapperTest.class.getClassLoader().getResourceAsStream("mybatisTestConfiguration/UserMapperTestConfiguration.xml"));
        //you can use builder.openSession(false) to not commit to database
        mapper = builder.getConfiguration().getMapper(UserMapper.class, builder.openSession(true));
    }


    @Test
    public void testSelectUserByUserName() {
        User user = mapper.selectUserByUserName("zhangsan");
        System.out.println(user);
    }
}
