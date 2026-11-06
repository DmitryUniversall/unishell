#!/usr/bin/env bash

echo 'CLI'
./gradlew run '--args=--vfs other/stage2/cli-vfs.json --script other/stage2/success.shell'

echo 'INI'
./gradlew run '--args=--config other/stage2/config.ini'

echo 'CLI priority'
./gradlew run '--args=--config other/stage2/config.ini --vfs other/stage2/override-vfs.json --script other/stage2/exit.shell'
