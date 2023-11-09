package br.com.maikon.orderserviceapi.decoders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maikon.hdcommonslib.models.exceptions.GenericFeignException;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.InputStream;
import java.util.Map;

public class RetrieveMessageErrorDecoder implements ErrorDecoder {//ErrorDecoder interface do feign que implementa erros
    // de requisição do feign

    @Override
    public Exception decode(String methodKey, Response response) {
        try (InputStream bodyIs = response.body().asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();

            final var error = mapper.readValue(bodyIs, Map.class);
            final var status = (Integer) error.get("status");

            return new GenericFeignException(status, error);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}