package com.han.httpserver.core;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用来解析 WEB-INFO下的 web.xml文件
 * @Author web server
 * @Date 2020/10/14 10:27
 * @Version 1.0
 * @since 1.0
 */
public class WebParser {
    public static Map<String, Map<String, String>> servletMaps = new HashMap<String, Map<String, String>>();

    public static void parseWebXML(String[] webAppNames) throws DocumentException {
        for (String webAppName : webAppNames) {
            Map<String, String> servletMap = parseWebXML(webAppName);
            servletMaps.put(webAppName, servletMap);
        }
    }

    private static Map<String, String> parseWebXML(String webAppName) throws DocumentException {
        // 获取web.xml路径
        String xmlPath = webAppName + "/WEB-INF/web.xml";
        SAXReader reader = new SAXReader();
        // 解析servlet节点们
        Document document = reader.read(new File(xmlPath));
        List<Element> servletNodes = document.selectNodes("web-app/servlet");
        // k:v -> servlet-name: servlet-class
        Map<String, String> servletInfoMap = new HashMap<String, String>();
        for(Element servletNode: servletNodes) {
            Element servletElt = (Element) servletNode.selectSingleNode("servlet-name");
            String servletName = servletElt.getStringValue();
            Element servletClassElt = (Element) servletNode.selectSingleNode("servlet-class");
            String servletClassName = servletClassElt.getStringValue();
            servletInfoMap.put(servletName, servletClassName);
        }

        List<Element> servletMappingNodes = document.selectNodes("web-app/servlet-mapping");
        // k:v -> servlet-name: pattern-url
        Map<String, String> servletMappingInfoMap = new HashMap<String, String>();
        for (Element servletMappingNode : servletMappingNodes) {
            Element servletElt = (Element) servletMappingNode.selectSingleNode("servlet-name");
            String servletName = servletElt.getStringValue();
            Element urlPatternElt = (Element) servletMappingNode.selectSingleNode("url-pattern");
            String urlPattern = urlPatternElt.getStringValue();
            servletMappingInfoMap.put(servletName, urlPattern);
        }

        // k:v -> pattern-url: servlet-class
        Set<String> servletNames = servletInfoMap.keySet();
        Map<String, String> servletMap = new HashMap<String, String>();
        for (String servletName : servletNames) {
            String urlPattern = servletMappingInfoMap.get(servletName);
            String servletClassName = servletInfoMap.get(servletName);
            servletMap.put(urlPattern, servletClassName);
        }

        return servletMap;
    }
}
