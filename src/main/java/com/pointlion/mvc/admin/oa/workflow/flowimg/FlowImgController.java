package com.pointlion.mvc.admin.oa.workflow.flowimg;

import com.pointlion.mvc.common.base.BaseController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

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
    public void generateImage() throws Exception {
        String procInstId = getPara("procInstId");
        flowImgService.generateImageByProcInstId(procInstId);
    }


    /***
     * 生成到本地文件夹下，前端再读取
     * @param processId
     * @param response
     * @throws IOException
     */
    public void viewProcessImg(String processId, HttpServletResponse response) throws IOException {
        OutputStream os = null;
        try {
            String directory = "F:" + File.separator + "temp" + File.separator;
            final String suffix = ".png";
            File folder = new File(directory);
            File[] files = folder.listFiles();
            for (File file : files) {
                if (file.getName().equals(processId + suffix)) {
                    file.delete();
                }
            }
            byte[] processImage = flowImgService.generateImageByProcInstId(processId);
            File dest = new File(directory + processId + suffix);
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
