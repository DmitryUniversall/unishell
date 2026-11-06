#!/usr/bin/env bash

echo 'Existing directory'
./gradlew run '--args=--vfs other/stage4/vfs.json --script other/stage5/errors/existing-directory.shell'

echo 'Existing file'
./gradlew run '--args=--vfs other/stage4/vfs.json --script other/stage5/errors/existing-file.shell'

echo 'Missing parent'
./gradlew run '--args=--vfs other/stage4/vfs.json --script other/stage5/errors/missing-parent.shell'

echo 'Parent is a file'
./gradlew run '--args=--vfs other/stage4/vfs.json --script other/stage5/errors/parent-file.shell'

echo 'Mkdir arguments'
./gradlew run '--args=--vfs other/stage4/vfs.json --script other/stage5/errors/mkdir-arguments.shell'

echo 'Pwd arguments'
./gradlew run '--args=--vfs other/stage4/vfs.json --script other/stage5/errors/pwd-arguments.shell'
