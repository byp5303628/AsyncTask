#!/bin/bash

mvn clean cobertura:cobertura

bash <(curl -s https://codecov.io/bash)