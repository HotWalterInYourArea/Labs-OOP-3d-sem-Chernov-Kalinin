package api;

import api.controller.MathFunctionResource;
import api.controller.PingResource;
import api.controller.UserResource;
import api.dto.UserDTO;
import api.security.BasicAuthentication;
import api.security.BasicAuthorizer;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;

public class DropWizardApplication extends Application<MyConfiguration>
{
    public static void main(String[] args) throws  Exception{
        new DropWizardApplication().run(args);
    }
    @Override
    public void run(MyConfiguration config, Environment environment)throws Exception{
        environment.jersey().register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<UserDTO>()
                .setAuthenticator(new BasicAuthentication())
                .setAuthorizer(new BasicAuthorizer())
                .setRealm("BASIC-AUTH-REALM")
                .buildAuthFilter()));
        environment.jersey().register(new PingResource());
        environment.jersey().register(new MathFunctionResource());
        environment.jersey().register(new UserResource());
    }
    @Override
    public void initialize(Bootstrap<MyConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider());
        super.initialize(bootstrap);
    }
}
