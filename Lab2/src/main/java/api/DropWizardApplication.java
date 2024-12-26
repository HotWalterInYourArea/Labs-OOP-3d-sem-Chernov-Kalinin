package api;

import api.controller.MathFunctionResource;
import api.controller.PingResource;
import api.controller.UserResource;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.FilterRegistration;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import ui.resources.TabulatedFunctionOperationResource;
import ui.resources.TabulatedFunctionResource;
import ui.resources.ToFunctionConverterResource;

import java.util.EnumSet;

public class DropWizardApplication extends Application<MyConfiguration>
{
    public static void main(String[] args) throws  Exception{
        new DropWizardApplication().run(args);
    }
    @Override
    public void run(MyConfiguration config, Environment environment)throws Exception{
        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "*");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        environment.jersey().register(new PingResource());
        environment.jersey().register(new MathFunctionResource());
        environment.jersey().register(new UserResource());
        SessionHandler sessionHandler =new SessionHandler();
        environment.servlets().setSessionHandler(new SessionHandler());
        environment.jersey().register(new TabulatedFunctionResource());
        environment.jersey().register(new TabulatedFunctionOperationResource());
        environment.jersey().register(new ToFunctionConverterResource());
    }
    @Override
    public void initialize(Bootstrap<MyConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider());
        super.initialize(bootstrap);
    }
}
