package com.han.httpserver.core;

import com.han.javax.servlet.ServletRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author hxd
 * @Date 2020/10/16 11:07
 * @Version 1.0
 * @since 1.0
 */
public class RequestObject implements ServletRequest {

    private Map<String, String[]> parameterValueMap = new HashMap<String, String[]>();

    public RequestObject(String requestURI) {
        /**
         * localhost:8080/oa/user/save?
         * localhost:8080/oa/user/save?username=
         * localhost:8080/oa/user/save?username=hxd
         * localhost:8080/oa/user/save?username=hxd&
         * localhost:8080/oa/user/save?username=hxd&gender=
         * localhost:8080/oa/user/save?username=hxd&gender=1
         * localhost:8080/oa/user/save?username=hxd&gender=1&interest=music&interest=sport
         */
        if (requestURI.contains("?")) {
            String[] uriAndData = requestURI.split("[?]");
            // judge if there is any parameter
            if (uriAndData.length > 1) {
                String data = uriAndData[1];
                if (data.contains("&")) {
                    // 1. handle issues of multiple parameter
                    String[] nameAndValues = data.split("&");
                    for (String nameAndValue: nameAndValues) {
                        String[] nameAndValueAttr = nameAndValue.split("=");
                        if (parameterValueMap.containsKey(nameAndValueAttr[0])) {
                            String[] values = parameterValueMap.get(nameAndValueAttr[0]);
                            String[] newValues = new String[values.length + 1];
                            System.arraycopy(values, 0, newValues, 0, values.length);
                            if (nameAndValueAttr.length > 1) {
                                newValues[values.length] = nameAndValueAttr[1];
                            } else {
                                newValues[values.length] = "";
                            }
                            parameterValueMap.put(nameAndValueAttr[0], newValues);
                        } else {
                            if (nameAndValueAttr.length > 1) {
                                parameterValueMap.put(nameAndValueAttr[0], new String[]{nameAndValueAttr[1]});
                            } else {
                                parameterValueMap.put(nameAndValueAttr[0], new String[]{""});
                            }
                        }
                    }

                } else {
                    // 2. handle issues of single parameter
                    String[] nameAndValueAttr = data.split("=");
                    if (nameAndValueAttr.length > 1) {
                        parameterValueMap.put(nameAndValueAttr[0], new String[]{nameAndValueAttr[1]});
                    } else {
                        parameterValueMap.put(nameAndValueAttr[0], new String[]{""});
                    }
                }
            }

        }

    }

    /**
     * Get common parameter
     * @param key
     * @return String
     */
    public String getRequestParameter(String key) {

        String[] value = parameterValueMap.get(key);
        return (value != null && value.length != 0) ? value[0] : null;
    }

    /**
     * Get checkbox value
     * @param key
     * @return String[]
     */
    public String[] getRequestParameters(String key) {
        return parameterValueMap.get(key);
    }

}
