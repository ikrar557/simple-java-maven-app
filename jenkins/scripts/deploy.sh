#!/usr/bin/env bash

# Extract hostname without username for URL display
HOST_URL=$(echo ${EC2_HOST} | cut -d '@' -f2)

# Copy files to EC2
scp -o StrictHostKeyChecking=no target/*.jar ${EC2_HOST}:${DEPLOY_PATH}/app.jar
scp -o StrictHostKeyChecking=no Dockerfile ${EC2_HOST}:${DEPLOY_PATH}/

# Deploy to EC2
ssh -o StrictHostKeyChecking=no ${EC2_HOST} "
    cd ${DEPLOY_PATH}
    docker stop java-app || true
    docker rm java-app || true
    docker build -t java-app .
    docker run -d --name java-app -p 8080:8080 java-app
"

echo "✨ Application deployed successfully!"
echo "🌍 You can now access the application at: http://${HOST_URL}:8080"
echo "⏳ The application will remain accessible for 1 minute."