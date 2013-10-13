package com.jfrog.bintray.gradle.api;

import java.util.Collection;

/**
 * @author Noam Y. Tenne
 */
public interface BintrayPackageVersions {

    BintrayPackage getOwningPackage();

    Collection<BintrayVersion> getVersions();
}
