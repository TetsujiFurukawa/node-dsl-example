job('testcafe-example-dsl-node') {
    scm {
        git('https://github.com/TetsujiFurukawa/testcafe-ci-example.git', 'master') {  node ->
            node / gitConfigName('DSL User')
            node / gitConfigEmail('jenkins-dsl@example.com')
        }
    }
    wrappers {
        nodejs('node_latest')
    }
    steps {
        shell("npm install")
        shell("npx testcafe chrome:headless tests/test.js -r xunit:report.xml")
    }
    publishers {
        archiveJunit('report.xml')
    }
}