#!/bin/bash

read -p "Installing Kafka"

kubectl apply -f 1-kafka.yaml

kubectl wait --for=condition=ready pod -l app=kafka-no-keeper

read -p "Installing services"

kubectl apply -f 3-review-triage-stats.yaml
kubectl apply -f 2-review-triage-ui.yaml

kubectl wait --for=condition=ready pod -l app.kubernetes.io/name=review-triage
kubectl wait --for=condition=ready pod -l app.kubernetes.io/name=review-triage-stats

reviewHost=`oc get route triage-ui -o jsonpath={.spec.host}`

echo "https://$reviewHost"

reviewHostMetrics=`oc get route triage-review-stats -o jsonpath={.spec.host}`

read -p "Create a review and then continue with the execution"

curl "https://$reviewHostMetrics/q/metrics" | grep triage