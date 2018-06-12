package tankwar;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author AnCo
 *
 * <p>自定义地图类</p>
 * <p>用来读取生成的xml地图配置文件</p>
 */
public class TMap  {

    File xmlMap = null;
    ArrayList<SingleOneTMap> singelTMaps;

    public TMap(File file){
        xmlMap = file;
        singelTMaps = new ArrayList<>();
        loadXMLMap();
    }

    public TMap(String url){
        xmlMap = new File(url);
        singelTMaps = new ArrayList<>();
        loadXMLMap();

    }

    public void loadXMLMap(){

        SAXReader reader = new SAXReader(); //创建阅读器
        Document document = null; //创建document对象
        try {
            document = reader.read(xmlMap); //读入文件
            Element root = document.getRootElement(); //获取根元素
            List<Element> elementList = root.elements(); //获取全部子元素存入链表
            for(Element i : elementList){
                //将element存入内存
                if(i.getName()=="Point") {
                    SingleOneTMap singleOneTMap = new SingleOneTMap(i.getText(), i.attributeValue("MapType"),
                            i.attributeValue("CanPass"));
                    singelTMaps.add(singleOneTMap);
                }
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    public void drawTMap(Graphics graphics){
        for(SingleOneTMap i : singelTMaps){
            i.draw(graphics);
        }
    }
}
