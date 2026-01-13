package otus;


import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;


@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("otus.scenarious")
@ConfigurationParameter(key = "cucumber.glue", value = "otus")
public class RunnerTest {
}
