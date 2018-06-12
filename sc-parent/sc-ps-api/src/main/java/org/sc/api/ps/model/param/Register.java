package org.sc.api.ps.model.param;

import lombok.Data;

/**
 * Created by Sonic Wang on 2017/5/24.
 */
@Data
public class Register {

    private String countryId;

    private String phone;

    private String email;

    private String password;

    private String messageCode;

    private String wxOpenId;
}
