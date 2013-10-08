package com.jfrog.bintray.gradle.api;

import java.net.URI;

/**
 * A representation of a Bintray version that will most likely be part of the "Bintray Java Client" API
 *
 * @author Noam Y. Tenne
 */
public interface BintrayVersion {

    String getName();

    URI getUri();
}
