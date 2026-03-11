package com.javanauta.usuario.infrastructure.exeptions;

public class ConfliteExeception extends RuntimeException{
 public ConfliteExeception(String mensagem){
     super(mensagem);
 }

 public ConfliteExeception(String mensagem, Throwable throwable){
     super(mensagem);

 }
}
