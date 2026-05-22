package com.learningcode.student_management_system.service;

import com.learningcode.student_management_system.dto.request.AuthenticationRequest;
import com.learningcode.student_management_system.dto.request.IntrospectRequest;
import com.learningcode.student_management_system.dto.response.AuthenticationResponse;
import com.learningcode.student_management_system.dto.response.IntrospectResponse;
import com.learningcode.student_management_system.exception.AppException;
import com.learningcode.student_management_system.exception.ErrorCode;
import com.learningcode.student_management_system.repository.StudentRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    StudentRepository studentRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var student = studentRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_EXISTED));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean authenticated =   passwordEncoder.matches(request.getPassword(), student.getPassword());

        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        var token = generateToken(request.getEmail());

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    public IntrospectResponse introspect(IntrospectRequest request) {
            /*throws JOSEException , ParseException {*/
        try {
            var token = request.getToken();

            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

            SignedJWT singedJWT = SignedJWT.parse(token);

            Date expiryTime = singedJWT.getJWTClaimsSet().getExpirationTime();

            var verified = singedJWT.verify(verifier);

            return IntrospectResponse.builder()
                    .valid(verified && expiryTime.after(new Date()))
                    .build();
        } catch (Exception e) {
            return IntrospectResponse.builder()
                    .valid(false)
                    .build();
        }
    }

    String generateToken(String email) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet claimSet = new JWTClaimsSet.Builder()
                .subject(email)
                .issuer("student.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("role", "student")
                .build();

        Payload payload = new Payload(claimSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }
}
