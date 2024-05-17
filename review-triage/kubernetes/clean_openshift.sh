#!/bin/bash

kubectl delete -f 3-review-triage-stats.yaml
kubectl delete -f 2-review-triage-ui.yaml

kubectl delete -f 1-kafka.yaml