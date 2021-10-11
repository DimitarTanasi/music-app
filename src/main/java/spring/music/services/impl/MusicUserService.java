package spring.music.services.impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.music.models.entities.UserEntity;
import spring.music.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MusicUserService implements UserDetailsService {

    private final UserRepository userRepository;

    public MusicUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userFromDb = userRepository.findByUsername(username)
                .orElseThrow(
                        ()->new UsernameNotFoundException("User " +username+ " is not found!")
                );

         return mapToUserDetails(userFromDb);
    }
    private UserDetails mapToUserDetails(UserEntity entity){


        List<GrantedAuthority> authorities = entity
                .getRoles()
                .stream()
                .map(r->new SimpleGrantedAuthority(r.getRole().name()))
                .collect(Collectors.toList());

        return new User(entity.getUsername(),entity.getPassword(),authorities);

    }
}
