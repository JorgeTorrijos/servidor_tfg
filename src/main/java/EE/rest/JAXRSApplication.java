package EE.rest;

import jakarta.annotation.security.DeclareRoles;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath(ConstantesREST.API)
@DeclareRoles({ConstantesREST.ADMIN_ROL, ConstantesREST.USER_ROL})
public class JAXRSApplication extends Application {

}



