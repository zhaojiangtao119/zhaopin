package test.qiniu;

import com.labelwall.util.CommonUtil;
import com.labelwall.util.storage.FileTypeHepler;
import com.labelwall.util.storage.QiniuKeyGenerator;
import com.labelwall.util.storage.QiniuStorage;
import com.labelwall.util.storage.QiniuWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * Created by Administrator on 2017-12-05.
 */
public class TestUpload {

    private static Logger logger = LoggerFactory.getLogger(TestUpload.class);

    /*public static void main(String[] args) {
        File file = new File("E:/3.jpg");
        byte[] data = CommonUtil.getFileByte(file);
        upload(data);
    }

    public static void upload(byte[] data) {
        String key = QiniuWrapper.upload(data, QiniuKeyGenerator.generatorProductMianImgageKey(),false);
        System.out.println("Key："+key);

    }*/
}
