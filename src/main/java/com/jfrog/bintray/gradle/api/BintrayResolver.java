package com.jfrog.bintray.gradle.api;

import java.util.Collection;

/**
 * An extension/wrapper over the Bintray Java Client. Provides Gradle specific utilities.
 *
 * @author Noam Y. Tenne
 */
public interface BintrayResolver {

    Collection<GradlePlugin> plugins();

    Collection<BintrayPackage> packages(GradlePlugin plugin);

    Collection<BintrayVersion> versions(BintrayPackage bintrayPackage);

    BintrayVersion latestVersion(BintrayPackage bintrayPackage);
}
