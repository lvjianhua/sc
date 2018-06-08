package org.sc.api.ps.model.vo;

import org.sc.facade.ps.model.table.Privilege;
import lombok.Data;

import java.util.List;

/**
 * Created by Sonic Wang on 2017/11/29.
 */
@Data
public class PrivilegeVo extends Privilege {

    private List<String> roleNames;
}
