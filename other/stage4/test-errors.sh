#!/usr/bin/env bash

echo 'Missing path'
./gradlew run '--args=--vfs other/stage4/vfs.json --script other/stage4/errors/missing-path.shell'

echo 'Not a directory'
./gradlew run '--args=--vfs other/stage4/vfs.json --script other/stage4/errors/not-directory.shell'

echo 'Cat directory'
./gradlew run '--args=--vfs other/stage4/vfs.json --script other/stage4/errors/cat-directory.shell'

echo 'Uniq directory'
./gradlew run '--args=--vfs other/stage4/vfs.json --script other/stage4/errors/uniq-directory.shell'

echo 'Cat arguments'
./gradlew run '--args=--vfs other/stage4/vfs.json --script other/stage4/errors/cat-arguments.shell'

echo 'Uniq arguments'
./gradlew run '--args=--vfs other/stage4/vfs.json --script other/stage4/errors/uniq-arguments.shell'
