package com.jfrog.bintray.gradle.api

import spock.lang.Specification

/**
 * <p>A POC specification for the Bintray resolver.</p>
 * <p>Demos the expected use cases using stub implementations.</p>
 *
 * @author Noam Y. Tenne
 */
class BintrayResolverDemoSpec extends Specification {

    def 'Resolve all Gradle plugins from Bintray'() {
        setup:
        BintrayResolver resolver = new SimpleBintrayResolver()

        when:
        Collection<GradlePlugin> resolvedPlugins = resolver.plugins()

        then:
        resolvedPlugins.first().with {
            id == 'SingalongBlog'
            version == '1.0'
        }
    }

    def 'Resolve all packages associated with a Gradle plugin'() {
        setup:
        BintrayResolver resolver = new SimpleBintrayResolver()
        GradlePlugin singalongBlogPlugin = new SimpleGradlePlugin()

        when:
        Collection<BintrayPackage> resolvedPackages = resolver.packages(singalongBlogPlugin)

        then:
        resolvedPackages.first().with {
            name == 'captain-hammer'
            uri.toString() == 'https://bintray.com/joss/singalong-blog/captain-hammer'
        }
    }

    def 'Resolve all versions belonging to a Bintray package'() {
        setup:
        BintrayResolver resolver = new SimpleBintrayResolver()
        BintrayPackage captainHammersPackage = new SimpleBintrayPackage()

        when:
        Collection<BintrayVersion> resolvedVersions = resolver.versions(captainHammersPackage)

        then:
        resolvedVersions.first().with {
            name == '1.0'
            uri.toString() == 'https://bintray.com/joss/singalong-blog/captain-hammer/1.0'
        }
    }

    def 'Resolve the latest version of a Bintray package'() {
        setup:
        BintrayResolver resolver = new SimpleBintrayResolver()
        BintrayPackage captainHammersPackage = new SimpleBintrayPackage()

        when:
        BintrayVersion latestVersion = resolver.latestVersion(captainHammersPackage)

        then:
        latestVersion.with {
            name == '1.0'
            uri.toString() == 'https://bintray.com/joss/singalong-blog/captain-hammer/1.0'
        }
    }
}

private class SimpleBintrayResolver implements BintrayResolver {

    @Override
    Collection<GradlePlugin> plugins() {
        [new SimpleGradlePlugin()]
    }

    @Override
    Collection<BintrayPackage> packages(GradlePlugin plugin) {
        [new SimpleBintrayPackage()]
    }

    @Override
    Collection<BintrayVersion> versions(BintrayPackage bintrayPackage) {
        [new SimpleBintrayVersion()]
    }

    @Override
    BintrayVersion latestVersion(BintrayPackage bintrayPackage) {
        new SimpleBintrayVersion()
    }
}

private class SimpleBintrayPackage implements BintrayPackage {

    @Override
    String getName() {
        'captain-hammer'
    }

    @Override
    URI getUri() {
        new URI('https://bintray.com/joss/singalong-blog/captain-hammer')
    }
}

private class SimpleBintrayVersion implements BintrayVersion {

    @Override
    String getName() {
        '1.0'
    }

    @Override
    URI getUri() {
        new URI('https://bintray.com/joss/singalong-blog/captain-hammer/1.0')
    }
}

private class SimpleGradlePlugin implements GradlePlugin {

    @Override
    String getId() {
        'SingalongBlog'
    }

    @Override
    String getVersion() {
        '1.0'
    }
}