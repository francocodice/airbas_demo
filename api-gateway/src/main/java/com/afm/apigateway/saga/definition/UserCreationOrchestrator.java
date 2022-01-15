package com.afm.apigateway.saga.definition;

import com.afm.apigateway.saga.core.Orchestrator;
import com.afm.apigateway.saga.core.SagaBuilder;
import com.afm.apigateway.saga.core.SagaDefinition;
import com.afm.apigateway.saga.core.SagaParamsResolver;
import com.afm.apigateway.service.AuthService;
import com.afm.apigateway.service.ProfileService;
import lombok.RequiredArgsConstructor;
import model.utils.UserPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//Scheme from https://chrisrichardson.net/post/sagas/2019/12/12/developing-sagas-part-4.html


@RequiredArgsConstructor
public class UserCreationOrchestrator extends Orchestrator {
    private final AuthService authService;
    private final ProfileService profileService;

    private static Logger logger = LoggerFactory.getLogger(UserCreationOrchestrator.class);

    @Override
    protected SagaDefinition buildSaga(SagaBuilder builder) {
        logger.info("Building user creation saga");

        return builder
                .invoke(authService::createUser).addParam("credentials").saveTo("userDatailsData")
                .withCompensation(authService::deleteUser)

                .step()
                .invoke(profileService::register).addParam("userDatailsData").saveTo("jwt")

                .build();
    }

    public UserPayload createUser(UserPayload credentials) throws Throwable {
        SagaParamsResolver resolver = getExecutor()
                .withArg("credentials", credentials)
                .run()
                .collect();
        return resolver.get("jwt");
    }

}
