package com.theeduconnect.exeeduconnectbe.features.authentication.services.impl;

import com.theeduconnect.exeeduconnectbe.constants.authentication.ProviderEnum;
import com.theeduconnect.exeeduconnectbe.constants.authentication.RegistrationConstants;
import com.theeduconnect.exeeduconnectbe.domain.User;
import com.theeduconnect.exeeduconnectbe.features.authentication.dtos.OAuth2User;
import com.theeduconnect.exeeduconnectbe.repositories.RoleRepository;
import com.theeduconnect.exeeduconnectbe.repositories.UserRepository;
import java.time.LocalDate;
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

    public void processOAuthPostLogin(OAuth2User user, int roleId) {
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
            newUser.setRole(roleRepository.findById(roleId).get());
            newUser.setUsername(RegistrationConstants.SAMPLE_USER_NAME);
            newUser.setDateofbirth(LocalDate.of(2024, 1, 1));
            newUser.setPhone(RegistrationConstants.SAMPLE_PHONE_NUMBER);
            newUser.setAddress(RegistrationConstants.SAMPLE_ADDRESS);
            newUser.setStatus(true);
            newUser.setBalance(0.0);
            newUser.setResetPasswordToken("");
            repo.save(newUser);
        }
    }
}
