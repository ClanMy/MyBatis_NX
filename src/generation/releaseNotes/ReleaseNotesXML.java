package generation.releaseNotes;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReleaseNotesXML {


    public String instructions() {

        Element releaseNotes = rootElement();

        //获取使用说明标签内容
        String instructions = null;

        if (releaseNotes != null) {

            instructions = releaseNotes.element("instructions").getText();
        }

        return instructions;
    }


    public List<Map<String, String>> releaseNotes() {

        List<Map<String, String>> pointMap = new ArrayList<>();

        //根标签
        Element releaseNotes = rootElement();

        if (releaseNotes != null) {

            List<Element> explanationList = releaseNotes.elements("explanation");

            //遍历
            explanationList.forEach(explanation -> {

                Map<String, String> map = new HashMap<>();

                String updateTime = explanation.attribute("updateTime").getText();
                String version = explanation.attribute("version").getText();
                String text = explanation.getText();

                map.put("updateTime", updateTime);
                map.put("version", version);
                map.put("text", text);

                pointMap.add(map);

            });
        }

        return pointMap;
    }


    private Element rootElement() {
        //获取当前项目路径
        String paths = "./config/releaseNotes.xml";
        // 创建SAXReader对象
        SAXReader reader = new SAXReader(); // 需要导入jar包:dom4j

        try {

            // 读取已存在的xml
            Document document = reader.read(paths);
//            document.setXMLEncoding("UTF-8");
            // 获取根元素,
            return document.getRootElement();


        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return null;
    }

}