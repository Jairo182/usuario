package com.javanauta.usuario.business;

import com.javanauta.usuario.business.converter.UsuarioConverter;
import com.javanauta.usuario.business.dto.EnderecoDTO;
import com.javanauta.usuario.business.dto.TelefoneDTO;
import com.javanauta.usuario.business.dto.UsuarioDTO;
import com.javanauta.usuario.infrastructure.entity.Endereco;
import com.javanauta.usuario.infrastructure.entity.Telefone;
import com.javanauta.usuario.infrastructure.entity.Usuario;
import com.javanauta.usuario.infrastructure.exeptions.ConfliteExeception;
import com.javanauta.usuario.infrastructure.exeptions.ResourceNotFoundExeption;
import com.javanauta.usuario.infrastructure.repository.EnderecoRepository;
import com.javanauta.usuario.infrastructure.repository.TelefoneRepository;
import com.javanauta.usuario.infrastructure.repository.UsuarioRepository;
import com.javanauta.usuario.infrastructure.security.JwtUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;

    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO) {

        emailExiste(usuarioDTO.getEmail());

        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));

        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return usuarioConverter.paraUsuarioDTO(usuarioSalvo);
    }

    public void emailExiste(String email) {
        if (verificaEmailExistente(email)) {
            throw new ConfliteExeception("Email já cadastrado " + email);
        }
    }

    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.findByEmail(email).isPresent();
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundExeption("Email não encontrado " + email));
    }

    public void deletaUsuarioPorEmail(String email) {
        if (!verificaEmailExistente(email)) {
            throw new ResourceNotFoundExeption("Email não encontrado " + email);
        }
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO dto) {

        String tokenLimpo = token.startsWith("Bearer ") ? token.substring(7) : token;

        String email = jwtUtil.extrairEmailToken(tokenLimpo);

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundExeption("Email não encontrado " + email));

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());

        if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
            usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        Usuario usuarioAtualizado = usuarioRepository.save(usuario);

        return usuarioConverter.paraUsuarioDTO(usuarioAtualizado);
    }

    public EnderecoDTO atualizaEndereco(Long idEndereco, EnderecoDTO enderecoDTO) {

        Endereco entity = enderecoRepository.findById(idEndereco)
                .orElseThrow(() ->
                        new ResourceNotFoundExeption("Id nao encontrado " + idEndereco));

        // 🔥 AQUI está a correção: método precisa ser PUBLIC no converter
        Endereco enderecoAtualizado = usuarioConverter.updateEndereco(enderecoDTO, entity);

        Endereco salvo = enderecoRepository.save(enderecoAtualizado);

        return usuarioConverter.paraEnderecoDTO(salvo);
    }

    public TelefoneDTO atualizaTelefone(Long idTelefone, TelefoneDTO dto) {

        Telefone entity = telefoneRepository.findById(idTelefone)
                .orElseThrow(() ->
                        new ResourceNotFoundExeption("Id nao encontrado " + idTelefone));

        Telefone telefone = usuarioConverter.updateTelefone(dto, entity);

        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }
}