package com.jfrog.bintray.gradle.api;

import java.util.Collection;

/**
 * An extension/wrapper over the Bintray Java Client. Provides Gradle specific utilities.
 *
 * @author Noam Y. Tenne
 */
public interface BintrayResolver {

    Collection<GradlePlugin> plugins();

    BintrayPackage pkg(GradlePlugin plugin);

    Collection<BintrayVersion> versions(String pluginId);

    BintrayVersion latestVersion(String pluginId);
}
