package com.pointlion.mvc.admin.oa.workflow.flowimg;

import com.pointlion.mvc.common.base.BaseController;
import sun.java2d.pipe.RenderBuffer;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.Buffer;

/**
 * @Description: FlowImgController
 * @Author: liyang
 * @Date: 2019/10/15 0015 9:30
 * @Version 1.0
 */
public class FlowImgController extends BaseController {
    private static FlowImgService flowImgService = FlowImgService.me;

    /***
     * 图片生成，接口返回
     */
    public void getFlowImg() throws Exception {
        String processInstanceId = getPara("processInstanceId");
        byte[] b = flowImgService.generateImageByProcInstId(processInstanceId);
        ByteArrayInputStream in = new ByteArrayInputStream(b);
        BufferedImage bi = ImageIO.read(in);
        ImageIO.write(bi,"png",this.getResponse().getOutputStream());
        renderNull();
    }


    /***
     * 生成到本地文件夹下，前端再读取
     * @throws IOException
     */
    public void viewProcessImg() throws IOException {
        String processInstanceId = getPara("processInstanceId");
        OutputStream os = null;
        try {
            String directory = "F:" + File.separator + "temp" + File.separator;
            final String suffix = ".png";
            File folder = new File(directory);
            File[] files = folder.listFiles();
            for (File file : files) {
                if (file.getName().equals(processInstanceId + suffix)) {
                    file.delete();
                }
            }
            byte[] processImage = flowImgService.generateImageByProcInstId(processInstanceId);
            File dest = new File(directory + processInstanceId + suffix);
            os = new FileOutputStream(dest, true);
            os.write(processImage, 0, processImage.length);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }
}
