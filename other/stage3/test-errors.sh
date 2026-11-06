#!/usr/bin/env bash

echo 'Unknown command'
./gradlew run '--args=--vfs other/stage3/vfs/minimal.json --script other/stage2/error.shell'

echo 'Bad arguments'
./gradlew run '--args=--vfs other/stage3/vfs/files.json --script other/stage3/arguments-error.shell'

echo 'Bad quotes'
./gradlew run '--args=--vfs other/stage3/vfs/deep.json --script other/stage2/parse-error.shell'

echo 'Missing VFS'
./gradlew run '--args=--vfs other/stage3/vfs/missing.json'

echo 'Bad JSON'
./gradlew run '--args=--vfs other/stage3/vfs/invalid.json'

echo 'Bad base64'
./gradlew run '--args=--vfs other/stage3/vfs/invalid-base64.json'
