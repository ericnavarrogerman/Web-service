package com.pqr.demopqr.service;

import com.pqr.demopqr.dao.IRolRepository;
import com.pqr.demopqr.dao.IUsuarioRepository;
import com.pqr.demopqr.dto.UsuarioDTO;
import com.pqr.demopqr.model.Rol;
import com.pqr.demopqr.model.Usuario;
import com.pqr.demopqr.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final IRolRepository rolRepository;
    private final IUsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(IRolRepository rolRepository, IUsuarioRepository usuarioRepository){
        this.rolRepository = rolRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<UsuarioDTO> getAllUsersByRoleOrderByIdasc(){
        Rol rol = rolRepository.findByNombre("usuario").orElse(null);

        assert rol != null;
        return usuarioRepository.findAllByRolIdOrderByIdAsc(rol.getId()).stream()
                .map(u -> new UsuarioDTO(u, true)).collect(Collectors.toList());
    }

    public UsuarioDTO getUsuarioById(@NotNull Long id) throws Exception {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if(usuario == null){
            throw new Exception("El usuario no existe.");
        }

        return new UsuarioDTO(usuario, true);
    }

    public UsuarioDTO getUsuarioByUsername(@NotNull String username) throws Exception {
        Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);
        if(usuario == null){
            throw new Exception("El usuario no existe.");
        }

        return new UsuarioDTO(usuario, true);
    }

    public UsuarioDTO createUsuario(@NotNull UsuarioDTO usuarioDTO) throws Exception {
        if(!Utils.isEmpty(usuarioDTO.getUsername())){
            if(usuarioRepository.findByUsername(usuarioDTO.getUsername()).isPresent()){
                throw new Exception("El usuario ya existe.");
            }
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setPassword(Utils.getSHA5FromString(usuarioDTO.getPassword()));

        if(usuarioDTO.getRol() != null) {
            rolRepository.findByNombre(usuarioDTO.getRol().getNombre()).
                    ifPresent(usuario::setRol);
        }

        return new UsuarioDTO(usuarioRepository.saveAndFlush(usuario), true);
    }

    public UsuarioDTO updateUsuario(@NotNull UsuarioDTO usuarioDTO) throws Exception {
        Usuario usuario = null;
        if(!Utils.isEmpty(usuarioDTO.getId())){
            usuario = usuarioRepository.findById(usuarioDTO.getId()).orElse(null);
        }

        if(usuario == null){
            throw new Exception("El usuario no existe.");
        }

        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        if(!Utils.isEmpty(usuarioDTO.getPassword())) {
            usuario.setPassword(Utils.getSHA5FromString(usuarioDTO.getPassword()));
        }

        if(usuarioDTO.getRol() != null) {
            rolRepository.findByNombre(usuarioDTO.getRol().getNombre()).
                    ifPresent(usuario::setRol);
        }

        return new UsuarioDTO(usuarioRepository.saveAndFlush(usuario), true);
    }

    public UsuarioDTO loginUser(@NotNull UsuarioDTO usuarioDTO) throws Exception {
        Usuario usuario = null;
        if(!Utils.isEmpty(usuarioDTO.getUsername())){
            usuario = usuarioRepository.findByUsername(usuarioDTO.getUsername()).orElse(null);
        }

        if(usuario == null){
            throw new Exception("El usuario no existe.");
        }

        if(!Utils.getSHA5FromString(usuarioDTO.getPassword()).equals(usuario.getPassword())){
            throw new Exception("La contraseña es incorrecta.");
        }

        return new UsuarioDTO(usuario, true);
    }
}
