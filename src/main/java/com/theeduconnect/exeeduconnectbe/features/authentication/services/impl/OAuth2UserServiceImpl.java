package com.theeduconnect.exeeduconnectbe.features.authentication.services.impl;

import com.theeduconnect.exeeduconnectbe.constants.authentication.provider.ProviderEnum;
import com.theeduconnect.exeeduconnectbe.domain.entities.User;
import com.theeduconnect.exeeduconnectbe.features.authentication.dtos.CustomOAuth2User;
import com.theeduconnect.exeeduconnectbe.repositories.RoleRepository;
import com.theeduconnect.exeeduconnectbe.repositories.UserRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class OAuth2UserServiceImpl {
    private UserRepository repo;
    private RoleRepository roleRepository;

    public OAuth2UserServiceImpl(UserRepository repo, RoleRepository roleRepository) {
        this.repo = repo;
        this.roleRepository = roleRepository;
    }

    public void processOAuthPostLogin(CustomOAuth2User user) {
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
            /*TODO: Role Parameter NOT YET AVAILABLE for Google Login!!!
             *  This is just a failsafe method.*/
            newUser.setRole(roleRepository.findById(1).get());
            repo.save(newUser);
        }
    }
}
