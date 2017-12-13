package by.vbalanse.facade.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author mikolasusla@gmail.com
 */
public class GrantedAuthorityImpl implements GrantedAuthority {
    private static final long serialVersionUID = 1029928088340565343L;

    private String roleName;

    public GrantedAuthorityImpl(String roleName){
        this.roleName = roleName;
    }

    public String getAuthority() {
        return this.roleName;
    }
}
