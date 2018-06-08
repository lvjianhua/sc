package org.sc.common.utils.web;



import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * Created by Webb Dong on 2017/3/31.
 */
public class BindingResultUtils {
    /**
     * 获取错误信息
     * @param br
     * @return
     */
    public static String getErrorInfo(BindingResult br) {
        String errorInfo = null;
        List<ObjectError> allErrors = br.getAllErrors();
        for (ObjectError error : allErrors) {
            errorInfo = error.getCode();
            break;
        }
        return errorInfo;
    }

    private BindingResultUtils() {
    }
}
