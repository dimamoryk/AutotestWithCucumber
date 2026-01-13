package otus;

import io.cucumber.junit.platform.engine.Cucumber;
import org.junit.platform.suite.api.*;

@Suite
@SelectPackages("otus.scenarious")
@SuiteDisplayName("Feature Scenarios")
@IncludeEngines("cucumber")
@ConfigurationParameter(key = "cucumber.glue", value = "otus")
public class RunnerTest {}