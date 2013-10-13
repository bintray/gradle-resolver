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
        plugin.versionSelector == '1.0+'
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
        BintrayPackageVersions resolvedVersions = resolver.versions('SingalongBlog')


        then:
        resolvedVersions.owningPackage.name == 'captain-hammer'
        def version = resolvedVersions.versions.first()
        version.name == '1.0'
        version.uri.toString() == 'https://bintray.com/joss/singalong-blog/captain-hammer/1.0'
    }

    def 'Resolve the versions specified by the version selector of a Gradle plugin'() {
        setup:
        BintrayResolver resolver = new SimpleBintrayResolver()

        when:
        BintrayPackageVersions resolvedVersions = resolver.versions(new SimpleGradlePlugin())

        then:
        resolvedVersions.owningPackage.name == 'captain-hammer'
        def version = resolvedVersions.versions.first()
        version.name == '1.0'
        version.uri.toString() == 'https://bintray.com/joss/singalong-blog/captain-hammer/1.0'
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
    BintrayPackageVersions versions(String pluginId) {
        new BintrayPackageVersions() {
            @Override
            BintrayPackage getOwningPackage() {
                new SimpleBintrayPackage()
            }

            @Override
            Collection<BintrayVersion> getVersions() {
                [new SimpleBintrayVersion()]
            }
        }
    }

    @Override
    BintrayPackageVersions versions(GradlePlugin plugin) {
        new BintrayPackageVersions() {

            @Override
            BintrayPackage getOwningPackage() {
                new SimpleBintrayPackage()
            }

            @Override
            Collection<BintrayVersion> getVersions() {
                [new SimpleBintrayVersion()]
            }
        }
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
    Object getVersionSelector() {
        '1.0+'
    }
}