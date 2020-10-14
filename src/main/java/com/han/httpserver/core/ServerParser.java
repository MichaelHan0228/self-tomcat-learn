package com.han.httpserver.core;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @ClassName Core
 * @Author hxd
 * @Date 2020/10/13 11:28
 * @Version 1.0
 */
public class ServerParser {

    public static int getPort() {
        int port = 8080;
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read("conf/server.xml");
            Element connector = (Element) document.selectSingleNode("//connector");
            port = Integer.parseInt(connector.attributeValue("port"));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return port;
    }
}
