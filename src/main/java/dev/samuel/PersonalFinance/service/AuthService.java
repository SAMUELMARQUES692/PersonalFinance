package dev.samuel.PersonalFinance.service;

import dev.samuel.PersonalFinance.DTOs.UserRegisterDTO;
import dev.samuel.PersonalFinance.DTOs.UserResponseDTO;
import dev.samuel.PersonalFinance.exception.BusinessException;
import dev.samuel.PersonalFinance.mapper.UserMapper;
import dev.samuel.PersonalFinance.model.UserModel;
import dev.samuel.PersonalFinance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Usuario ou senha invalidos"));
    }


    @Transactional
    public UserResponseDTO register(UserRegisterDTO userDTO) {
        if (userRepository.findByEmail(userDTO.email()).isPresent()) {
            throw new BusinessException(userDTO.email());
        }

        UserModel user = userMapper.toModel(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.password()));

        return userMapper.toDto(userRepository.save(user));

    }



}
