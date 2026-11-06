#!/usr/bin/env bash

echo 'Unknown command'
./gradlew run '--args=--vfs other/stage2/demo-vfs.json --script other/stage2/error.shell'

echo 'Bad quotes'
./gradlew run '--args=--script other/stage2/parse-error.shell'

echo 'Missing script'
./gradlew run '--args=--script other/stage2/missing.shell'

echo 'Missing config'
./gradlew run '--args=--config other/stage2/missing.ini'

echo 'Bad config'
./gradlew run '--args=--config other/stage2/invalid.ini'
