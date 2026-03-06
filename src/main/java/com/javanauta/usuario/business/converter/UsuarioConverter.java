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

    // ENTITY -> DTO
    public UsuarioDTO paraUsuarioDTO(Usuario usuario){
        return UsuarioDTO.builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .enderecos(paraListaEnderecoDTO(usuario.getEnderecos()))
                .telefones(paraListaTelefonesDTO(usuario.getTelefones()))
                .build();
    }

    // DTO -> ENTITY
    public Usuario paraUsuario(UsuarioDTO usuarioDTO){
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListaEndereco(usuarioDTO.getEnderecos()))
                .telefones(paraListaTelefone(usuarioDTO.getTelefones()))
                .build();
    }

    // LISTA ENDERECO -> DTO
    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> enderecos){
        List<EnderecoDTO> lista = new ArrayList<>();

        if(enderecos != null){
            for(Endereco endereco : enderecos){
                lista.add(paraEnderecoDTO(endereco));
            }
        }

        return lista;
    }

    // ENDERECO -> DTO
    public EnderecoDTO paraEnderecoDTO(Endereco endereco){
        return EnderecoDTO.builder()
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .cidade(endereco.getCidade())
                .complemento(endereco.getComplemento())
                .cep(endereco.getCep())
                .estado(endereco.getEstado())
                .build();
    }

    // LISTA TELEFONE -> DTO
    public List<TelefoneDTO> paraListaTelefonesDTO(List<Telefone> telefones){

        if(telefones == null){
            return new ArrayList<>();
        }

        return telefones.stream()
                .map(this::paraTelefoneDTO)
                .toList();
    }

    // TELEFONE -> DTO
    public TelefoneDTO paraTelefoneDTO(Telefone telefone){
        return TelefoneDTO.builder()
                .numero(telefone.getNumero())
                .ddd(telefone.getDdd())
                .build();
    }

    // LISTA ENDERECO DTO -> ENTITY
    public List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecoDTOS){
        List<Endereco> lista = new ArrayList<>();

        if(enderecoDTOS != null){
            for(EnderecoDTO enderecoDTO : enderecoDTOS){
                lista.add(paraEndereco(enderecoDTO));
            }
        }

        return lista;
    }

    // ENDERECO DTO -> ENTITY
    public Endereco paraEndereco(EnderecoDTO enderecoDTO){
        return Endereco.builder()
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .cidade(enderecoDTO.getCidade())
                .complemento(enderecoDTO.getComplemento())
                .cep(enderecoDTO.getCep())
                .estado(enderecoDTO.getEstado())
                .build();
    }

    // LISTA TELEFONE DTO -> ENTITY
    public List<Telefone> paraListaTelefone(List<TelefoneDTO> telefoneDTOS){

        if(telefoneDTOS == null){
            return new ArrayList<>();
        }

        return telefoneDTOS.stream()
                .map(this::paraTelefone)
                .toList();
    }

    // TELEFONE DTO -> ENTITY
    public Telefone paraTelefone(TelefoneDTO telefoneDTO){
        return Telefone.builder()
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .build();
    }

}