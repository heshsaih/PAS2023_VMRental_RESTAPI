package com.example.vmrentalrest.security;

import com.example.vmrentalrest.exceptions.ErrorMessages;
import com.example.vmrentalrest.exceptions.JwsException;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Component
public class JwsService {

    @Value("${jwt.secret.key}")
    String jwsSecretKey;

    public String generateSign(String id) {
        try {
            JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload(id));
            JWSSigner signer = new MACSigner(jwsSecretKey);
            jwsObject.sign(signer);
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new JwsException(ErrorMessages.InvalidJWSMessages.INVALID_JWS_MESSAGE);
        }
    }
    public void verifySign(String jws,String id) {
        try {
            JWSObject jwsObject = JWSObject.parse(jws);
            JWSVerifier verifier = new MACVerifier(jwsSecretKey);
                if(jwsObject.verify(verifier)) {
                    String expectedSign = generateSign(id);
                    if(!jws.equals(expectedSign)) {
                        throw new JwsException(ErrorMessages.InvalidJWSMessages.NOT_MATCHING_JWS_MESSAGE);
                    }
                }
        } catch (ParseException | JOSEException e) {
            throw new JwsException(ErrorMessages.InvalidJWSMessages.INVALID_JWS_MESSAGE);
        }
    }
}
