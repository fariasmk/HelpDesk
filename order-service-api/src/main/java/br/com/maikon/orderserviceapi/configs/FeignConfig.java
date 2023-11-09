package br.com.maikon.orderserviceapi.configs;

import br.com.maikon.orderserviceapi.decoders.RetrieveMessageErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new RetrieveMessageErrorDecoder();//O Feign vai utilizar essa minha classe para decodificar os erros.
        // Quando houver excepção na resposta, o Feign ira procurar um bean de ErrorDecoder e como temos um bean persoalizado,
        // o RetrieveMessageErrorDecoder, O Spring ira chamar o RetrieveMessageErrorDecoder para decodificar os erros.
    }
}