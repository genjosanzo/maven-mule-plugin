/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tools.maven.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.test.plugin.BuildTool;

public class AbstractMuleMavenPluginTestCase extends AbstractMojoTestCase
{
    private static final String BUILD_OUTPUT_DIRECTORY = "target/surefire-reports/build-output";

    private BuildTool _buildTool;
    private File _outputFile;

    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        _buildTool = (BuildTool) lookup(BuildTool.ROLE, "default");
    }
    
    protected InvocationResult buildProject(String projectName) throws Exception
    {
        createBuildLogFile(projectName);

        File pomFile = pomInProject(projectName);
        return runMaven(pomFile);
    }
    
    private void createBuildLogFile(String basename)
    {
        File outputDir = new File(BUILD_OUTPUT_DIRECTORY);
        outputDir.mkdirs();
    
        _outputFile = new File(outputDir, basename + ".log");
    }

    private File pomInProject(String projectName)
    {
        File projectFolder = new File("target/it", projectName);
        File pomFile = new File(projectFolder, "pom.xml");
        assertFileExists(pomFile);
        return pomFile;
    }

    private InvocationResult runMaven(File pom) throws Exception
    {
        Properties properties = new Properties();

        List<String> goals = new ArrayList<String>();
        goals.add("compile");
        goals.add("package");

        InvocationRequest request = _buildTool.createBasicInvocationRequest(pom, properties, goals,
            _outputFile);
        request.setUpdateSnapshots(false);
        request.setShowErrors(true);
        request.setDebug(false);

        return _buildTool.executeMaven(request);
    }
    
    protected void assertFileExists(File file)
    {
        assertTrue(file.getAbsolutePath() + " must exist", file.exists());
    }
    
    protected void assertFileDoesNotExist(File file)
    {
        assertFalse(file.getAbsolutePath() + " must not exist", file.exists());
    }
    
    protected void assertSuccess(InvocationResult result)
    {
        assertEquals("Expected exit code 0", 0, result.getExitCode());
    }

    protected void assertFailure(InvocationResult result)
    {
        assertNotSame("Expected exit code != 0", 0, result.getExitCode());
    }
}


