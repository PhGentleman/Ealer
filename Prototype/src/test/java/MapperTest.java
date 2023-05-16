import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.ph.ealer.bean.Picture;
import org.ph.ealer.bean.Tag;
import org.ph.ealer.bean.User;
import org.ph.ealer.mapper.PictureMapper;
import org.ph.ealer.mapper.UserMapper;
import org.ph.ealer.utils.MyBatisUtil;

import java.util.List;

public class MapperTest {

    @Test
    public void insertTest(){
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()){
            PictureMapper pictureMapper = sqlSession.getMapper(PictureMapper.class);
            Tag tag = new Tag("testTag");
            pictureMapper.createTag(tag);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
