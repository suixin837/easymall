package cn.tedu.pic.service;

import com.jt.common.utils.UUIDUtil;
import com.jt.common.utils.UploadUtil;
import com.jt.common.vo.PicUploadResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class PicService {
    @Value("${pic.pathDirPrefix}")
    private String pathDirPrefix;
    @Value("${pic.urlPrefix}")
    private String urlPrefix;
    public PicUploadResult picUpload(MultipartFile pic) {
        /*1 获取图片名称,判断后缀合法
         *2 判断文件的木马(看看数据中心有没有宽和高)
         *3 生成共享路径(upload/1/2/d/2/d/3/d/3/)
         *4 生成磁盘路径 tomcat的webapp的路径,放到本地工程的webapp中
         *"D:/emSoftware/workspace/1811easymall-ssm/src/main/webapp/"
         *5 生成url前缀
         *6 重命名文件 huawei.jpg-->uuid.jpg
         *7 存盘 数据流输出存储到对应的磁盘位置
         *8 将封装完毕的数据PicUploadResult返回
         */
        PicUploadResult result = new PicUploadResult();
        //1.图片合法判断
        //--后缀判断，获取图片名称
        String originalFilename = pic.getOriginalFilename();
        //截取后缀，判断是否以.png\.jpg\.gif结尾
        String extName = originalFilename.substring(
                originalFilename.lastIndexOf("."));
        boolean matches = extName.matches(".(jpg|png|gif|JPG|PNG|GIF)$");
        //如果后缀不匹配，则直接return，只有匹配成功，才能继续
        if(!matches){
            result.setError(1);//代表错误
            return result;
        }
        //木马判断
        try {
            BufferedImage bufferImage = ImageIO.read(pic.getInputStream());
            bufferImage.getWidth();
            bufferImage.getHeight();
        } catch (IOException e) {
            //不满足图片属性
            e.printStackTrace();
            result.setError(1);//代表错误
            return result;
        }
        //2.创建路径
        //引入UploadUtil工具类，生成图片路径，如：/upload/a/b/c/1/2/3/4/5/
        String dir = "/"+UploadUtil.getUploadPath(
                originalFilename, "upload")+"/";
        //3.拼接根路径和文件名(配置文件读取的根路径)
        //相当于D://upload/upload/a/b/c/1/2/3/4/5/
        String pathDir = pathDirPrefix+dir;
        //磁盘创建目录（如果没有）
        File file = new File(pathDir);
        if(!file.exists()){
            file.mkdirs();
        }
        //4.将文件存入对应的目录
        //避免文件名重复（加uuid）
        String filename = UUIDUtil.getUUID()+originalFilename;
        //流输出文件
        try {
            pic.transferTo(new File(pathDir+filename));
        } catch (Exception e) {
            //存储失败，返回
            e.printStackTrace();
            result.setError(1);//代表错误
            return result;
        }
        //5.拼接域名
        String imageUrl = urlPrefix+dir+filename;
        //6.将imageUrl封装到返回对象中
        result.setUrl(imageUrl);
        System.out.println(imageUrl);
        System.out.println(extName);
        return result;
    }

}

