#!/bin/bash

for i in src/main/resources/etl/*.e*l; do
  echo -e "$i\t$(./normalise-etl.sh "$i" | wc -l)"
done
