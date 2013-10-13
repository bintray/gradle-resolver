package com.jfrog.bintray.gradle.api;

/**
 * A representation of a Gradle plugin that will most likely be part of the Gradle API
 *
 * @author Noam Y. Tenne
 */
public interface GradlePlugin {

    String getId();

    Object getVersionSelector();
}
