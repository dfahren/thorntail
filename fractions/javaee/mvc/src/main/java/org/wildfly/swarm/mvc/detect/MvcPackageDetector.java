package org.wildfly.swarm.mvc.detect;

import org.wildfly.swarm.spi.meta.PackageFractionDetector;

public class MvcPackageDetector extends PackageFractionDetector {

    public MvcPackageDetector() {
        anyClassOf("javax.mvc.Controller");
    }

    public String artifactId() {
        return "mvc";
    }
}
