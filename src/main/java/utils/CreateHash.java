package utils;

import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

import java.util.HashMap;
import java.util.Map;

public class CreateHash {

    private Pbkdf2PasswordHash passwordHash;

    @Inject
    public CreateHash(Pbkdf2PasswordHash passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String hashear(String pass){

        Map<String, String> parameters = new HashMap<>();
        parameters.put(ConstantesUtils.PBKDF_2_PASSWORD_HASH_ITERATIONS, ConstantesUtils.VALUE_3072);
        parameters.put(ConstantesUtils.PBKDF_2_PASSWORD_HASH_ALGORITHM, ConstantesUtils.PBKDF_2_WITH_HMAC_SHA_512);
        parameters.put(ConstantesUtils.PBKDF_2_PASSWORD_HASH_SALT_SIZE_BYTES, ConstantesUtils.VALUE_32);
        parameters.put(ConstantesUtils.PBKDF_2_PASSWORD_HASH_KEY_SIZE_BYTES, ConstantesUtils.VALUE_32);
        passwordHash.initialize(parameters);

        return passwordHash.generate(pass.toCharArray());

    }

    public boolean verify(String pass,String hass){

        boolean solution = false;

        if(passwordHash.verify(pass.toCharArray(),hass)){
            solution = true;
        }

        return solution;

    }


}
