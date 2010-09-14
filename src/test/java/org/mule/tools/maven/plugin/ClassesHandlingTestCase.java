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

import org.apache.maven.shared.invoker.InvocationResult;

public class ClassesHandlingTestCase extends AbstractMuleMavenPluginTestCase
{
    public void testPackageWithClassesFolder() throws Exception
    {
        InvocationResult result = buildProject("project-without-classes");
        assertSuccess(result);
    }
    
//    public void testPackageWithArchivedClasses()
//    {
//    }
//
//    public void testPackageWithoutClassesFolder()
//    {
//    }
//
//    public void testPackageWithEmptyArchive()
//    {
//    }
}


