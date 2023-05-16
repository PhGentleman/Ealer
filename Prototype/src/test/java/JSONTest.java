import com.alibaba.fastjson2.JSON;
import org.junit.Test;
import org.ph.ealer.bean.Picture;

public class JSONTest {
    @Test
    public void jsonTest(){
        String picture = "{\"pid\":7,\"picName\":\"bike\",\"picLoc\":\"1\\/20230425200134798.jpg\",\"uid\":1,\"picView\":0,\"picLike\":0,\"picFav\":0,\"owner\":{\"uid\":1,\"username\":\"\\u6D4B\\u8BD5\\u7528\\u62371\",\"password\":null,\"tel\":0,\"email\":null},\"tags\":[{\"tid\":4,\"tagName\":\"bike\",\"tagUsed\":0,\"tagView\":0},{\"tid\":5,\"tagName\":\"person\",\"tagUsed\":0,\"tagView\":0}],\"picDesc\":null,\"completePath\":\"http:\\/\\/pzwork.oss-cn-shanghai.aliyuncs.com\\/content\\/1\\/20230425200134798.jpg?Expires=1683347193\\u0026OSSAccessKeyId=LTAI5t9E7YPuC8oSt8NjyyA1\\u0026Signature=hLIU2hTIiLBBk2x6%2FF%2Bx5yGDZTI%3D\",\"thumbnailPath\":\"http:\\/\\/pzwork.oss-cn-shanghai.aliyuncs.com\\/content\\/1\\/20230425200134798.jpg\\/thumbnail?Expires=1683347193\\u0026OSSAccessKeyId=LTAI5t9E7YPuC8oSt8NjyyA1\\u0026Signature=9eGd2xrmGkwo3RSVmhhxtLODCT0%3D\"}";
        Picture pic = JSON.parseObject(picture, Picture.class);
        System.out.println(pic.toString());
    }
}

