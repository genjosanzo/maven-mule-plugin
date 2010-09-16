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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.project.MavenProject;

public class ArtifactFilter
{
    private Set<Artifact> projectArtifacts;
    private List<Exclusion> excludes;

    @SuppressWarnings("unchecked")
    public ArtifactFilter(MavenProject project, List<Exclusion> exclusions)
    {
        super();
        projectArtifacts = project.getArtifacts();
        excludes = exclusions;
    }
    
    public Set<Artifact> getArtifactsToArchive()
    {
        Set<Artifact> filteredArtifacts = keepOnlyArtifactsWithCompileOrRuntimeScope();
        filteredArtifacts = applyAllExcludes(filteredArtifacts);
        return filteredArtifacts;
    }

    private Set<Artifact> keepOnlyArtifactsWithCompileOrRuntimeScope()
    {
        Set<Artifact> filteredArtifacts = new HashSet<Artifact>();
        
        for (Artifact artifact : projectArtifacts)
        {
            String scope = artifact.getScope();
            if (Artifact.SCOPE_COMPILE.equals(scope) || Artifact.SCOPE_RUNTIME.equals(scope))
            {
                filteredArtifacts.add(artifact);
            }
        }
        
        return filteredArtifacts;
    }
    
    private Set<Artifact> applyAllExcludes(Set<Artifact> artifacts)
    {
        for (Exclusion exclude : excludes)
        {
            artifacts = applyExclude(exclude, artifacts);
        }
        return artifacts;
    }

    private Set<Artifact> applyExclude(Exclusion exclude, Set<Artifact> artifacts)
    {
        String filter = exclude.toString();
        Set<Artifact> filteredArtifacts = new HashSet<Artifact>();
        
        for (Artifact artifact : artifacts)
        {
            if (dependencyTrailContains(artifact, filter) == false)
            {
                filteredArtifacts.add(artifact);
            }
        }
        
        return filteredArtifacts;
    }

    private boolean dependencyTrailContains(Artifact artifact, String filter)
    {
        List<?> dependencyTrail = artifact.getDependencyTrail();
System.out.println("***** filter: " + filter + " dep trail: " + artifact.getDependencyTrail());
        for (Object trailElement : dependencyTrail)
        {
            if (trailElement.toString().startsWith(filter))
            {
                return true;
            }
        }
        
        return false;
    }
}
