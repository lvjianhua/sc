package org.sc.common.utils;


import com.alibaba.fastjson.JSON;

import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.sc.common.exception.ServiceRunTimeException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.support.SpringDecoder;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.lang.reflect.Type;

@Configuration
public class ResponseDecoder extends SpringDecoder {
    private Logger logger = LogManager.getLogger(getClass());

    public ResponseDecoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        super(messageConverters);
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        if (type != null) {
            Object obj = super.decode(response, org.sc.common.model.vo.Response.class);

            logger.debug("fegin recive data:" + obj.toString());

            if (obj != null) {
                if (obj instanceof org.sc.common.model.vo.Response) {
                	org.sc.common.model.vo.Response responseVo = (org.sc.common.model.vo.Response) obj;
                    if (responseVo != null) {
                        if (responseVo.getCode() != 0) {
                            throw new ServiceRunTimeException(responseVo.getMessage(), responseVo.getCode());
//                            throw new ServiceRunTimeException(responseVo.getMessage());
                        }
                        String jsonText = JSON.toJSONString(responseVo.getData());
                        Object tmpObj = JSON.parseObject(jsonText, type);
                        return tmpObj;
                    } else {
                        return ((org.sc.common.model.vo.Response) obj).getData();
                    }
                } else {
                    return obj;
                }
            }
        }

        return super.decode(response, type);
    }

}
