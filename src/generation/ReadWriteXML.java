package generation;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ReadWriteXML {

    //获取当前项目路径
//    private String path = Class.class.getResource("/").getPath();
//    private String paths = path + "resource/generator.xml";
    private String paths = "./config/generator.xml";

    public void generator(DatabaseInformation datainf, List<String> tableName_list) {

        // 创建SAXReader对象
        SAXReader reader = new SAXReader(); // 需要导入jar包:dom4j
//        reader.setEncoding("utf-8");

        try {

            // 读取已存在的xml
            Document document = reader.read(paths);
//            document.setXMLEncoding("UTF-8");
            // 获取根元素, 并且获取目标标签的上一级标签（因为下面要多次使用）
            Element context = document.getRootElement().element("context");

            //清除上一次生成的table （获取所有table标签，生成一个List集合）
            List<Element> tablelist = context.elements("table");

            // 通过父类删除子类
            //context.remove(table);
            tablelist.forEach(context::remove);

            //直接删除本身(第二种写法)
//            tablelist.forEach(Node::detach);


            /*
             * 修改数据库配置文件
             * */
            //Element标签类的addAttribute("attr","v1alue")方法 ( //key相同，覆盖；不存在key，则添加)
            Element jdbcConnection = context.element("jdbcConnection");
            //拼装数据库地址
            jdbcConnection.addAttribute("driverClass", datainf.getDriverClass());
            String url = "jdbc:mysql://" + datainf.getLibraryURL() + "/" + datainf.getLibraryName() + "?useUnicode=true&characterEncoding=utf8";
            jdbcConnection.addAttribute("connectionURL", url);
            jdbcConnection.addAttribute("userId", datainf.getUserId());
            jdbcConnection.addAttribute("password", datainf.getPassword());

            /*
             * 添加生成表
             * */
            tableName_list.forEach(tableName -> {

                //在context标签下生成table标签
                Element addTable = context.addElement("table");
                //表名
                addTable.addAttribute("tableName", tableName);

                if (false) {
                    //表别名
                    addTable.addAttribute("domainObjectName", tableName);
                }

                //是否生成扩展类
                if (false) {

                    addTable.addAttribute("enableCountByExample", "flase");
                    addTable.addAttribute("enableUpdateByExample", "flase");
                    addTable.addAttribute("enableDeleteByExample", "flase");
                    addTable.addAttribute("enableSelectByExample", "flase");
                    addTable.addAttribute("selectByExampleQueryId", "flase");
                }
            });

            //保存输出
            saveDocument(document, new File(paths));

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }


    // 下面的代码可以完成java对XML的输出,和输出格斯等操作
    private static void saveDocument(Document document, File xmlFile) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(xmlFile);
            Writer osWrite = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);// 创建输出流,指定输出格式（很重要）
            OutputFormat format = OutputFormat.createPrettyPrint(); // 获取输出的指定格式 漂亮格式：有空格换行
            format.setEncoding("utf-8");// 设置编码 ，确保解析的xml为UTF-8格式
            XMLWriter writer = new XMLWriter(osWrite, format);// XMLWriter
            // 指定输出文件以及格式
            writer.write(document);// 把document写入xmlFile指定的文件(可以为被解析的文件或者新创建的文件)
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}