package stage.utensil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class AutoWriteData {

    //创建一个Properties对象
    private Properties properties = new Properties();
    //配置文件名
    private String propName;
    //配置文件路径
    private String resourcePath;
    //配置文件路径后缀名
    private String properSuffix = ".properties";
    //要写入的文件
    private Map<String, String> dateMap;

    /**
     * 描述： 写入配置数据
     *
     * @param dateMap  写入文件的数据
     * @param propName 文件name
     */
    public void steWriteData(Map<String, String> dateMap, String propName) {
        this.dateMap = dateMap;
        this.propName = propName;
        //判断路径是否为空
        isResourcePath();
        writeDate();
    }

    /**
     * 描述： 写入配置数据
     *
     * @param dateMap      写入文件的数据
     * @param propName     文件name
     * @param resourcePath 保存路径
     */
    public void steWriteData(Map<String, String> dateMap, String propName, String resourcePath) {
        this.resourcePath = resourcePath;
        //判断路径是否为空
        isResourcePath();
        this.steWriteData(dateMap, propName);
    }

    /**
     * 描述： 读取配置数据
     *
     * @param propName 读取文件的name
     * @return 返回读取的数据
     */
    public Map<String, String> getReadDate(String propName) {
        this.propName = propName;
        //判断路径是否为空
        isResourcePath();
        return readDate();
    }

    /**
     * 描述： 读取配置数据
     *
     * @param propName 读取文件的name
     * @return 返回读取的数据
     */
    public Map<String, String> getReadDate(String propName, String resourcePath) {
        this.resourcePath = resourcePath;
        //判断路径是否为空
        isResourcePath();
        return getReadDate(propName);
    }

    /* 判断resourcePath路径是否为空 ，如果为空就设置默认值*/
    private void isResourcePath() {
        if (resourcePath == null)
            this.resourcePath = "Resource";
    }

    /**
     * 写入配置数据
     */
    private void writeDate() {
        //拼接资源路径
        String filePath = resourcePath + File.separator + propName + properSuffix;
        FileOutputStream fileOutputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        try {
            //输出流（创建Properties文件）
            fileOutputStream = new FileOutputStream(filePath, false);
            //循环将数据存入properties对象中
            dateMap.forEach((key, value) -> properties.setProperty(key, value));
            //（输出流编写器）设置文件输入编码格式
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
            //将数据写入文件中
            properties.store(outputStreamWriter, "文件标题头");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //释放资源
            try {
                if (fileOutputStream != null)
                    fileOutputStream.close();
                if (outputStreamWriter != null)
                    outputStreamWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取配置数据
     */
    private Map<String, String> readDate() {
        //拼接资源路径
        String filePath = resourcePath + File.separator + propName + properSuffix;
        //读取配置数据集合
        Map<String, String> map = new HashMap<>();
        FileInputStream fileInputStream = null;
        BufferedInputStream bufferedInputStream = null;
        InputStreamReader inputStreamReader = null;
        if (new File(filePath).exists()) {
            try {
                //输入流
                fileInputStream = new FileInputStream(filePath);
                //读取数据，缓冲输入流
                bufferedInputStream = new BufferedInputStream(fileInputStream);
                //（输入流阅读器） 读取数据并设置编码格式
                inputStreamReader = new InputStreamReader(bufferedInputStream, StandardCharsets.UTF_8);
                //加载本地配置数据
                properties.load(inputStreamReader);
                //将读取的数据循环存入集合中
                properties.forEach((object, object2) -> map.put(object.toString(), object2.toString()));
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //释放资源
                try {
                    if (bufferedInputStream != null)
                        bufferedInputStream.close();
                    if (fileInputStream != null)
                        fileInputStream.close();
                    if (inputStreamReader != null)
                        inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }
}
