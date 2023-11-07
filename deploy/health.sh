#!/bin/bash

# 定义容器名称
CONTAINER_NAME="my_container"

# 定义通知接收人的电子邮件地址
NOTIFICATION_EMAIL="your@email.com"

# 获取容器的ID
CONTAINER_ID=$(docker ps -qf "name=$CONTAINER_NAME")

# 检查容器是否运行
if [ -z "$CONTAINER_ID" ]; then
    echo "Container $CONTAINER_NAME is not running. Starting it..."
    docker start $CONTAINER_NAME
    exit 0
fi

# 获取容器的健康状态
HEALTH=$(docker inspect --format='{{.State.Health.Status}}' "$CONTAINER_ID")

# 如果容器健康状态不是"healthy"，则重启容器并发送通知
if [ "$HEALTH" != "healthy" ]; then
    echo "Container $CONTAINER_NAME is not healthy. Restarting..."
    docker restart $CONTAINER_NAME
    # 发送通知，可以使用邮件、Slack、短信等通知方式
    echo "Container $CONTAINER_NAME restarted due to health check failure." | mail -s "Container Health Alert" $NOTIFICATION_EMAIL
fi
