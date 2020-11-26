package _Self.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand

object DockerTestBit : BuildType({
    name = "Docker-test-bit"

    vcs {
        root(BitbucketOrgArmadik)
    }

    steps {
        dockerCommand {
            name = "docker-build"
            commandType = build {
                source = file {
                    path = "DockerFiles/Dash/Dockerfile"
                }
                namesAndTags = "armadik/dashtest:%build.number%"
                commandArgs = "--pull"
            }
            param("dockerImage.platform", "linux")
        }
        dockerCommand {
            name = "docker-push"
            commandType = push {
                namesAndTags = "armadik/dashtest:%build.number%"
            }
        }
    }

    features {
        dockerSupport {
            cleanupPushedImages = true
            loginToRegistry = on {
                dockerRegistryId = "PROJECT_EXT_4"
            }
        }
    }
})
