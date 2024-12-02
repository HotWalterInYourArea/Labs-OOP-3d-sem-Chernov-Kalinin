package api;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.core.Configuration;
import io.dropwizard.db.DataSourceFactory;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class MyConfiguration extends Configuration {
    private final DataSourceFactory dataSourceFactory;
    public MyConfiguration(@JsonProperty("datasource") @Valid @NotNull final DataSourceFactory dataSourceFactory) {
        this.dataSourceFactory = dataSourceFactory;
    }
    public DataSourceFactory getDataSourceFactory() {
        return dataSourceFactory;
    }
}
