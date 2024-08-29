package joseph.ciaravella.TeeTimeFinder.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import joseph.ciaravella.TeeTimeFinder.dao.UserAccountRepository;
import joseph.ciaravella.TeeTimeFinder.model.UserAccount;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    private UserAccountRepository userAccountRepository;

    @Autowired
    public CustomUserDetailsService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserAccount user = userAccountRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("No user found with this email"));
        // return new User(user.getEmail(), user.getPassword(), user.getClass().getSimpleName());
        return null;
    }

    // private Collection<GrantedAuthority> mapRoleToAuthorities

}
