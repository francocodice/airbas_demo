package com.afm.apigateway.saga.orchestrators;

import com.afm.apigateway.saga.core.*;
import com.afm.apigateway.security.jwt.JwtService;
import com.afm.apigateway.service.AuthService;
import com.afm.apigateway.service.ProfileService;
import lombok.RequiredArgsConstructor;
import model.utils.UserPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//Scheme from https://chrisrichardson.net/post/sagas/2019/12/12/developing-sagas-part-4.html
//https://github.com/eventuate-tram/eventuate-tram-sagas/tree/master/eventuate-tram-sagas-reactive-orchestration-simple-dsl/src/main/java/io/eventuate/tram/sagas/reactive/simpledsl


@RequiredArgsConstructor
public class UserCreationOrchestrator extends Orchestrator {
    private final AuthService authService;
    private final ProfileService profileService;
    private final JwtService jwtService;

    private static Logger logger = LoggerFactory.getLogger(UserCreationOrchestrator.class);

    @Override
    protected SagaDefinition buildSaga(SagaBuilder builder) {
        logger.info("Building user creation saga");

        return builder
                .invoke(authService::createUser).addParam("credentials").saveTo("userDatailsData")
                .withCompensation(authService::deleteUser)
                .step()

                .invoke(profileService::saveDetails).addParam("userDatailsData")
                .step()

                .invoke(authService::authenticateUserSaga).addParam("credentials").saveTo("currentUser")
                .step()

                .invoke(jwtService::generateJwt).addParam("currentUser").saveTo("jwt")
                .build();
    }

    public String createUser(UserPayload credentials) throws Throwable, SagaException {
        SagaParamsResolver resolver = getExecutor()
                .withArg("credentials", credentials)
                .run()
                .collect();
        return resolver.get("jwt");
    }

}
