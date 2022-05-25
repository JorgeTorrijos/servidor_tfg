package EE.security;

import EE.servicios.ServiciosUsuarios;
import dao.modelos.UserLogin;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import utils.CreateHash;

import java.util.Set;

import static jakarta.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;

@Singleton
public class InMemoryIdentityStore implements IdentityStore {

    private final ServiciosUsuarios serviciosUsuarios;
    private final CreateHash createHash;

    @Inject
    public InMemoryIdentityStore(ServiciosUsuarios serviciosUsuarios, CreateHash createHash) {
        this.serviciosUsuarios = serviciosUsuarios;
        this.createHash = createHash;
    }

    @Override
    public CredentialValidationResult validate(Credential credential) {

        if (credential instanceof UsernamePasswordCredential) {
            UsernamePasswordCredential user = (UsernamePasswordCredential) credential;


            Either<String, UserLogin> getUserActivo = serviciosUsuarios.getUserActivo(user.getCaller());

            if (getUserActivo.isRight()) {

                UserLogin userLogin = getUserActivo.get();

                //COMPROBAMOS CONTRASEÑA
                if (createHash.verify(user.getPasswordAsString(), userLogin.getPass())) {

                    //COMPROBAMOS TIPO DE USUARIO
                    if (userLogin.getTipo_user() == 1) {
                        return new CredentialValidationResult(userLogin.getUsername(), Set.of(ConstantesSecurity.ADMIN, ConstantesSecurity.USER));
                    } else if (userLogin.getTipo_user() == 2) {
                        return new CredentialValidationResult(userLogin.getUsername(), Set.of(ConstantesSecurity.USER));
                    }

                } else {
                    return INVALID_RESULT;
                }

            } else {
                return INVALID_RESULT;
            }

        }
        return INVALID_RESULT;

    }
}
