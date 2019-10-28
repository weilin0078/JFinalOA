package com.pointlion.mvc.common.utils.office.word;

import com.itextpdf.text.Font;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
 
public class AsianFontProvider extends XMLWorkerFontProvider {
 
    @Override
    public Font getFont(final String fontname, String encoding, float size, final int style) {
        String fntname = fontname;
        if (fntname == null) {
           /*使用的windows里的宋体，可将其文件放资源文件中引入
            *请确保simsun.ttc字体在windows下支持
            *我是将simsun.ttc字体打进maven的jar包中使用
            */
            fntname = "fonts/simsun.ttc";
        }
        if (size == 0) {
            size = 4;
        }
        return super.getFont(fntname, encoding, size, style);
    }
}
