package com.theeduconnect.exeeduconnectbe.features.authentication.services.impl;

import com.theeduconnect.exeeduconnectbe.constants.authentication.ProviderEnum;
import com.theeduconnect.exeeduconnectbe.domain.User;
import com.theeduconnect.exeeduconnectbe.features.authentication.dtos.OAuth2User;
import com.theeduconnect.exeeduconnectbe.repositories.RoleRepository;
import com.theeduconnect.exeeduconnectbe.repositories.UserRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class RegisterOAuth2UserServiceImpl {
    private UserRepository repo;
    private RoleRepository roleRepository;

    public RegisterOAuth2UserServiceImpl(UserRepository repo, RoleRepository roleRepository) {
        this.repo = repo;
        this.roleRepository = roleRepository;
    }

    public void processOAuthPostLogin(OAuth2User user) {
        String email = user.getEmail();
        String fullName = user.getName();
        String avatarUrl = user.getAvatarUrl();
        Optional<User> existUser = repo.findUserByEmail(email);

        if (existUser.isEmpty()) {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setFullname(fullName);
            newUser.setProvider(ProviderEnum.GOOGLE);
            newUser.setAvatarurl(avatarUrl);
            /*TODO: Role Parameter is NOT YET AVAILABLE for Google Login!!!
             *  This is just a failsafe method.*/
            newUser.setRole(roleRepository.findById(1).get());
            repo.save(newUser);
        }
    }
}
