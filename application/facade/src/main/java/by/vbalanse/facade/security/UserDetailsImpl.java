package by.vbalanse.facade.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author mikolasusla@gmail.com
 */
public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = -6509897037222767090L;

  private Collection<GrantedAuthority> authorities = new HashSet<>();
  private String password;
  private String username;
  private Long id;

  public UserDetailsImpl(String username, String password, Long id, Collection<GrantedAuthority> authorities) {
    this.username = username;
    this.password = password;
    this.id = id;
    this.authorities = authorities;
  }

  public Collection<GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  public String getPassword() {
    return this.password;
  }

  public String getUsername() {
    return this.username;
  }

  public boolean isAccountNonExpired() {
    return true;
  }

  public boolean isAccountNonLocked() {
    return true;
  }

  public boolean isCredentialsNonExpired() {
    return true;
  }

  public boolean isEnabled() {
    return true;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
