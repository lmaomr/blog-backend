// package cn.lmao.blogbackend.services;

// import cn.lmao.blogbackend.exception.CustomException;
// import cn.lmao.blogbackend.model.entity.User;
// import cn.lmao.blogbackend.model.enums.ExceptionCodeMsg;
// import cn.lmao.blogbackend.repository.UserRepository;
// import lombok.RequiredArgsConstructor;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;

// import java.util.Collections;

// @Service
// @RequiredArgsConstructor
// public class CustomUserDetailsService implements UserDetailsService {

//     private final UserRepository userRepository;

//     @Override
//     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//         User user = userRepository.getUserByUsername(username);
//         if (user == null) {
//             throw new CustomException(ExceptionCodeMsg.USER_NOT_EXISTS);
//         }
        
//         return new org.springframework.security.core.userdetails.User(
//             user.getUsername(),
//             user.getPassword(),
//             Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
//         );
//     }
// } 