package com.github.tsingjyujing.tof.util;

import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 文件小公举～
 */
public class FileUtil {

    /**
     * 自动读取配置文件
     * 先尝试从文件中获取，再从配置中获取
     *
     * @param propertiesFileName 配置文件名
     * @return
     * @throws IOException
     */
    public static Properties autoReadProperties(String propertiesFileName) throws IOException {
        try {
            // 尝试读取工作目录下conf文件夹
            return readPropertiesFile(propertiesFileName);
        } catch (IOException ex) {
            // 尝试读取资源文件
            return readPropertiesResource(propertiesFileName);
        }
    }

    /**
     * 自动读取文本文件
     * 先尝试从文件中获取，再从配置中获取
     *
     * @param textFileName 配置文件名
     * @return
     * @throws IOException
     */
    public static String autoReadText(String textFileName) throws IOException {
        try {
            // 尝试读取工作目录下conf文件夹
            return readTextFile(textFileName);
        } catch (IOException ex) {
            // 尝试读取资源文件
            return readTextResource(textFileName);
        }
    }


    /**
     * 读取配置（从资源文件根目录中）
     *
     * @param propertiesResourceName 配置文件名
     * @return properties对象
     */
    public static Properties readPropertiesResource(String propertiesResourceName) throws IOException {
        final Properties properties = new Properties();
        final InputStream stream = FileUtil.class.getResourceAsStream("/" + propertiesResourceName);
        properties.load(stream);
        return properties;
    }

    /**
     * 读取配置（默认从工作目录中的conf文件夹）
     *
     * @param propertiesFileName 配置文件名
     * @return properties对象
     */
    public static Properties readPropertiesFile(String propertiesFileName) throws IOException {
        final Properties properties = new Properties();
        final InputStream stream = new FileInputStream("conf/" + propertiesFileName);
        properties.load(stream);
        return properties;
    }


    /**
     * 读取配置（从资源文件根目录中）
     *
     * @param textResourceName 配置文件名
     * @return properties对象
     */
    public static String readTextResource(String textResourceName) throws IOException {
        return IOUtils.toString(FileUtil.class.getResourceAsStream("/" + textResourceName));
    }

    /**
     * 读取配置（默认从工作目录中的conf文件夹）
     *
     * @param textFileName 配置文件名
     * @return properties对象
     */
    public static String readTextFile(String textFileName) throws IOException {
        return IOUtils.toString(new FileInputStream("conf/" + textFileName));
    }

    /**
     * 读取配置（默认从工作目录中的conf文件夹）
     *
     * @param textFileName 配置文件名
     * @return properties对象
     */
    public static String readRawTextFile(String textFileName) throws IOException {
        return IOUtils.toString(new FileInputStream(textFileName));
    }


}
