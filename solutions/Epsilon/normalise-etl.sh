#!/bin/bash

# Removes whitespace lines and comments from ETL

if [[ "$#" != 1 ]]; then
  echo "Usage: $0 file"
  exit 1
fi

FILE="$1"

sed -r 's/[/][*](.*?)[*][/]//g' "$FILE" | # remove single line /* */
  sed -r 's/(.+)([/][*])/\1\n\2/g'      | # break up /* into its own line
  sed -r 's/([*][/])(.+)/\1\n\2/g'      | # break up */ into its own line
  sed -r '/[/][*]/,/[*][/]/d'           | # remove /* ... */ line ranges
  sed -r 's/([/][/].*)//g'              | # remove // comments
  sed -r '/^\s*$/d'                     | # remove whitespace lines
  awk '1' -                               # ensure output ends on newline
