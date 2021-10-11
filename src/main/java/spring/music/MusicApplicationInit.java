package spring.music;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import spring.music.models.entities.UserEntity;
import spring.music.models.entities.UserRoleEntity;
import spring.music.models.entities.enums.UserRole;
import spring.music.repositories.UserRepository;
import spring.music.repositories.UserRoleRepository;

import java.util.List;

@Component
public class MusicApplicationInit implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public MusicApplicationInit(UserRepository userRepository,
                                UserRoleRepository userRoleRepository,
                                PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRoleRepository.count()==0) {
            UserRoleEntity admin = new UserRoleEntity().setRole(UserRole.ADMIN);
            UserRoleEntity user = new UserRoleEntity().setRole(UserRole.USER);
            userRoleRepository.saveAll(List.of(admin, user));

            UserEntity adminUser = initUser("admin","topsecret",List.of(admin,user));
            UserEntity userUser = initUser("user","topsecretU",List.of(user));

            userRepository.saveAll(List.of(adminUser,userUser));
        }

    }
    private UserEntity initUser(String username, String password,List<UserRoleEntity> roles){
        return new UserEntity()
                .setUsername(username)
                .setPassword(passwordEncoder.encode(password))
                .setRoles(roles);
    }
}
