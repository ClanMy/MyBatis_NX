package generation;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

public class Generator {

    public boolean generator() {
        //获取当前项目路径
//        String path = Class.class.getResource("/").getPath();
         String path = "./config";

        try {

            List<String> warnings = new ArrayList<>();
            boolean overwrite = true;
            //指定 逆向工程配置文件
            File configFile = new File(path + "/generator.xml");
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(configFile);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);

            //返回值 用来判断状态
            List<GeneratedXmlFile> generatedXmlFiles = myBatisGenerator.getGeneratedXmlFiles();
            if (generatedXmlFiles.size() > 0) {
                return true;
            }

        } catch (IOException | XMLParserException | InvalidConfigurationException | InterruptedException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
