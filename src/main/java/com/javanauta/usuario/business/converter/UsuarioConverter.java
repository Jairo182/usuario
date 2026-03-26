package com.javanauta.usuario.business.converter;

import com.javanauta.usuario.business.dto.EnderecoDTO;
import com.javanauta.usuario.business.dto.TelefoneDTO;
import com.javanauta.usuario.business.dto.UsuarioDTO;
import com.javanauta.usuario.infrastructure.entity.Endereco;
import com.javanauta.usuario.infrastructure.entity.Telefone;
import com.javanauta.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsuarioConverter {

    // ===================== ENTITY -> DTO =====================

    public UsuarioDTO paraUsuarioDTO(Usuario usuario) {
        return UsuarioDTO.builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .enderecos(paraListaEnderecoDTO(usuario.getEnderecos()))
                .telefones(paraListaTelefonesDTO(usuario.getTelefones()))
                .build();
    }

    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> enderecos) {
        List<EnderecoDTO> lista = new ArrayList<>();

        if (enderecos != null) {
            for (Endereco endereco : enderecos) {
                lista.add(paraEnderecoDTO(endereco));
            }
        }

        return lista;
    }

    public EnderecoDTO paraEnderecoDTO(Endereco endereco) {
        return EnderecoDTO.builder()
                .id(endereco.getId())
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .cidade(endereco.getCidade())
                .complemento(endereco.getComplemento())
                .cep(endereco.getCep())
                .estado(endereco.getEstado())
                .build();
    }

    public List<TelefoneDTO> paraListaTelefonesDTO(List<Telefone> telefones) {
        if (telefones == null) {
            return new ArrayList<>();
        }

        return telefones.stream()
                .map(this::paraTelefoneDTO)
                .toList();
    }

    public TelefoneDTO paraTelefoneDTO(Telefone telefone) {
        return TelefoneDTO.builder()
                .id(telefone.getId())
                .numero(telefone.getNumero())
                .ddd(telefone.getDdd())
                .build();
    }

    // ===================== DTO -> ENTITY =====================

    public Usuario paraUsuario(UsuarioDTO usuarioDTO) {
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListaEndereco(usuarioDTO.getEnderecos()))
                .telefones(paraListaTelefone(usuarioDTO.getTelefones()))
                .build();
    }

    public List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecoDTOS) {
        List<Endereco> lista = new ArrayList<>();

        if (enderecoDTOS != null) {
            for (EnderecoDTO enderecoDTO : enderecoDTOS) {
                lista.add(paraEndereco(enderecoDTO));
            }
        }

        return lista;
    }

    public Endereco paraEndereco(EnderecoDTO dto) {
        return Endereco.builder()
                .id(dto.getId())
                .rua(dto.getRua())
                .numero(dto.getNumero())
                .cidade(dto.getCidade())
                .complemento(dto.getComplemento())
                .cep(dto.getCep())
                .estado(dto.getEstado())
                .build();
    }

    public List<Telefone> paraListaTelefone(List<TelefoneDTO> telefoneDTOS) {
        if (telefoneDTOS == null) {
            return new ArrayList<>();
        }

        return telefoneDTOS.stream()
                .map(this::paraTelefone)
                .toList();
    }

    public Telefone paraTelefone(TelefoneDTO dto) {
        return Telefone.builder()
                .id(dto.getId())
                .numero(dto.getNumero())
                .ddd(dto.getDdd())
                .build();
    }

    // ===================== UPDATE =====================

    public Usuario updateUsuario(UsuarioDTO usuarioDTO, Usuario entity) {

        return Usuario.builder()
                .id(entity.getId())
                .nome(getOrDefault(usuarioDTO.getNome(), entity.getNome()))
                .email(getOrDefault(usuarioDTO.getEmail(), entity.getEmail()))
                .senha(getOrDefault(usuarioDTO.getSenha(), entity.getSenha()))
                .enderecos(usuarioDTO.getEnderecos() != null
                        ? paraListaEndereco(usuarioDTO.getEnderecos())
                        : entity.getEnderecos())
                .telefones(usuarioDTO.getTelefones() != null
                        ? paraListaTelefone(usuarioDTO.getTelefones())
                        : entity.getTelefones())
                .build();
    }

    public Endereco updateEndereco(EnderecoDTO dto, Endereco entity) {
        return Endereco.builder()
                .id(entity.getId())
                .rua(getOrDefault(dto.getRua(), entity.getRua()))
                .numero(getOrDefault(dto.getNumero(), entity.getNumero()))
                .cidade(getOrDefault(dto.getCidade(), entity.getCidade()))
                .cep(getOrDefault(dto.getCep(), entity.getCep()))
                .complemento(getOrDefault(dto.getComplemento(), entity.getComplemento()))
                .estado(getOrDefault(dto.getEstado(), entity.getEstado()))
                .build();
    }

    public Telefone updateTelefone(TelefoneDTO dto, Telefone entity) {
        return Telefone.builder()
                .id(entity.getId())
                .ddd(getOrDefault(dto.getDdd(), entity.getDdd()))
                .numero(getOrDefault(dto.getNumero(), entity.getNumero()))
                .build();
    }

    // ===================== UTIL =====================

    private <T> T getOrDefault(T novo, T antigo) {
        return novo != null ? novo : antigo;
    }
}