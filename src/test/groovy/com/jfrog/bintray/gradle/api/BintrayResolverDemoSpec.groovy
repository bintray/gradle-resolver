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
        def plugin = resolvedPlugins.first()
        plugin.id == 'SingalongBlog'
        plugin.version == '1.0'
    }

    def 'Resolve the package that is associated with a Gradle plugin'() {
        setup:
        BintrayResolver resolver = new SimpleBintrayResolver()
        GradlePlugin singalongBlogPlugin = new SimpleGradlePlugin()

        when:
        BintrayPackage resolvedPackage = resolver.pkg(singalongBlogPlugin)

        then:
        resolvedPackage.name == 'captain-hammer'
        resolvedPackage.uri.toString() == 'https://bintray.com/joss/singalong-blog/captain-hammer'
    }

    def 'Resolve all versions belonging to a Gradle plugin'() {
        setup:
        BintrayResolver resolver = new SimpleBintrayResolver()

        when:
        Collection<BintrayVersion> resolvedVersions = resolver.versions('SingalongBlog')


        then:
        def version = resolvedVersions.first()
        version.name == '1.0'
        version.uri.toString() == 'https://bintray.com/joss/singalong-blog/captain-hammer/1.0'
    }

    def 'Resolve the latest version of a Gradle plugin'() {
        setup:
        BintrayResolver resolver = new SimpleBintrayResolver()

        when:
        BintrayVersion latestVersion = resolver.latestVersion('SingalongBlog')

        then:
        latestVersion.name == '1.0'
        latestVersion.uri.toString() == 'https://bintray.com/joss/singalong-blog/captain-hammer/1.0'
    }
}

private class SimpleBintrayResolver implements BintrayResolver {

    @Override
    Collection<GradlePlugin> plugins() {
        [new SimpleGradlePlugin()]
    }

    @Override
    BintrayPackage pkg(GradlePlugin plugin) {
        new SimpleBintrayPackage()
    }

    @Override
    Collection<BintrayVersion> versions(String pluginId) {
        [new SimpleBintrayVersion()]
    }

    @Override
    BintrayVersion latestVersion(String pluginId) {
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