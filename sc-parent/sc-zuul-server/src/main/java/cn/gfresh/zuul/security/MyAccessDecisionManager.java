package cn.gfresh.zuul.security;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

/**
 * Created by admin on 2017/5/11.
 */
@Service
public class MyAccessDecisionManager implements AccessDecisionManager {

    public void decide(Authentication authentication, Object object,
                       Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException {
        if (configAttributes == null) {
            return;
        }
        
        String profileActive = System.getProperty("spring.profiles.active");
		if ("development".equals(profileActive) || "test".equals(profileActive)) {
			System.out.println("test 环境全部放行");
			return;
		}

        Iterator<ConfigAttribute> ite = configAttributes.iterator();

        while (ite.hasNext()) {
            ConfigAttribute ca = ite.next();
            String needRole = ca.getAttribute();

            System.out.println("auth id is =======================" + authentication.getPrincipal().toString());
            //ga 为用户所被赋予的权限。 needRole 为访问相应的资源应该具有的权限。
            for (GrantedAuthority ga : authentication.getAuthorities()) {
                if (needRole.trim().toUpperCase().equals(ga.getAuthority().trim().toUpperCase())) {
                    return;
                }
                if (needRole.trim().toUpperCase().equals("ROLE_UAA_ACCESS")) {
                    return;
                }
                /**如果是匿名的访问则可以直接访问*/
                if (needRole.trim().toUpperCase().equals("ROLE_ANONYMOUS")) {
                    return;
                }
            }
        }

        throw new AccessDeniedException("权限不足");

    }

    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    public boolean supports(Class<?> clazz) {
        return true;
    }
}
