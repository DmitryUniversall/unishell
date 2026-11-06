#!/usr/bin/env bash

echo 'Commands'
./gradlew run '--args=--vfs other/stage4/vfs.json --script other/stage4/startup.shell'
