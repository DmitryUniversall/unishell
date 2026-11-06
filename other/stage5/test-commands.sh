#!/usr/bin/env bash

echo 'Create'
./gradlew run '--args=--vfs other/stage4/vfs.json --script other/stage5/startup.shell'

echo 'Reload'
./gradlew run '--args=--vfs other/stage4/vfs.json --script other/stage5/reload.shell'
