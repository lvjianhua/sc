package org.sc.facade.ps.model.param;

import lombok.Data;

import java.util.List;

/**
 * Created by Sonic Wang on 2017/8/24.
 */
@Data
public class UserRole {

    private String userId;

    private List<String> roleIds;
}
