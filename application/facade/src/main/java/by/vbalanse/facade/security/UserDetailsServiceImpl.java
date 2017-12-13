package by.vbalanse.facade.security;

import by.vbalanse.facade.user.UserFacade;
import by.vbalanse.model.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author mikolasusla@gmail.com
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserFacade userFacade;

  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserDetails matchingUser = null;
    UserEntity user = userFacade.findByEmail(email);
    if (user != null) {
      Set<GrantedAuthority> authorities = new HashSet<>();
      authorities.add(new GrantedAuthorityImpl(user.getRole().getRoleType().getCode()));
      matchingUser = new UserDetailsImpl(email, user.getPasswordMD5hash(), user.getId(), authorities);
    }

    if (matchingUser == null) {
      throw new UsernameNotFoundException("Wrong username or password");
    }

    return matchingUser;
  }
}
