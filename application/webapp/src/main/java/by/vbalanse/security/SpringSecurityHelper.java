package by.vbalanse.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * @author mikolasusla@gmail.com
 */
@Service
public class SpringSecurityHelper {

    public static boolean hasRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof String) {
            return role.equals(principal);
        }
        UserDetails user = (UserDetails) principal;
        for (GrantedAuthority authority : user.getAuthorities()) {
            if (authority.getAuthority().equals(role)) {
                return true;
            }
        }
        return false;
    }
}
