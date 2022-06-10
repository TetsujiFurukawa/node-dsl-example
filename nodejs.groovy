job('testcafe-dsl-example') {
    scm {
        git('https://github.com/TetsujiFurukawa/testcafe-ci-example.git', 'master') {  node ->
            node / gitConfigName('DSL User')
            node / gitConfigEmail('jenkins-dsl@example.com')
        }
    }
    steps {
        shell("npm install")
        shell("npx testcafe chrome:headless tests/test.js -s takeOnFails=true --video artifacts/videos --video-options ,failedOnly=true -r jenkins:report.xml")
    } 
    publishers {
        archiveJunit('report.xml'){
            allowEmptyResults()
            retainLongStdout()
            healthScaleFactor(1.5)
            testDataPublishers {
                // Allows claiming of failed tests.
                allowClaimingOfFailedTests()
                // Publishes a report about flaky tests.
                publishFlakyTestsReport()
                // Published test attachments.
                publishTestAttachments()
                // Publishes the test stability history.
                publishTestStabilityData()
            }            
        }
    }
}