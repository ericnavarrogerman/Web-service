package com.pqr.demopqr.controller.security;

import com.pqr.demopqr.dao.IUsuarioRepository;
import com.pqr.demopqr.dto.UsuarioDTO;
import com.pqr.demopqr.dto.security.UserDTO;
import com.pqr.demopqr.model.Usuario;
import com.pqr.demopqr.util.BackendResponse;
import com.pqr.demopqr.util.Utils;
import com.pqr.demopqr.util.enums.ResponseStatus;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserSecurityController {

    private final IUsuarioRepository usuarioRepository;

    @Autowired
    public UserSecurityController(IUsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/user")
    public BackendResponse login(@RequestParam("username") String username, @RequestParam("password") String pwd) {
        Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);
        try {

            if (username == null) {
                return new BackendResponse(ResponseStatus.ERROR, "El usuario no existe.");
            }

            if (!usuario.getPassword().equals(Utils.getSHA5FromString(pwd))) {
                return new BackendResponse(ResponseStatus.ERROR, "La contraseña es incorrecta.");
            }

            String token = getJWTToken(username);
            UserDTO user = new UserDTO();
            user.setUsername(username);
            user.setToken(token);
            user.setPassword(pwd);
            user.setUsuarioDTO(new UsuarioDTO(usuario, true));
            return new BackendResponse(user);
        }catch(Exception e){
            return new BackendResponse(ResponseStatus.ERROR, e.getMessage());
        }
    }

    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }

}
