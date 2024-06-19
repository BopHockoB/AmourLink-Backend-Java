package ua.nure.userservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.nure.userservice.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class AmourlinkUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(AmourlinkUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("There's no such user: " + username));
    }
}

