#!/bin/bash

# 设置变量
REPO_PATH="/home/jia/workspace/jdd-server"  # 替换为你的仓库路径
BRANCH_NAME="dev"  # 替换为你的分支名称
IMAGE_NAME="web-server"    # 替换为你的镜像名称
CONTAINER_NAME="web-server"    # 替换为你的容器名称

# 进入仓库目录
echo "进入仓库目录: $REPO_PATH"
cd "$REPO_PATH" || { echo "无法进入仓库目录: $REPO_PATH"; exit 1; }

# 拉取最新代码
echo "拉取最新代码..."
git pull || { echo "拉取代码失败"; exit 1; }

# 检查本地是否存在指定分支
if ! git show-ref --quiet refs/heads/"$BRANCH_NAME"; then
    # 如果本地不存在该分支，则从远程创建并切换
    echo "本地不存在分支 $BRANCH_NAME，从远程创建并切换..."
    git checkout -b "$BRANCH_NAME" origin/"$BRANCH_NAME" || { echo "创建并切换分支失败"; exit 1; }
else
    # 如果本地已存在该分支，则直接切换
    echo "本地已存在分支 $BRANCH_NAME，直接切换..."
    git checkout "$BRANCH_NAME" || { echo "切换分支失败"; exit 1; }
fi

# 使用 Maven 打包
echo "使用 Maven 打包..."
mvn clean install -Dmaven.test.skip=true || { echo "Maven 打包失败"; exit 1; }
sleep 1

# 清除旧容器
echo "清除旧容器..."
docker rm -f "$IMAGE_NAME" || { echo "清除旧容器失败"; exit 1; }

# 清除旧镜像
echo "清除旧镜像..."
docker rmi -f "$CONTAINER_NAME" || { echo "清除旧镜像失败"; exit 1; }
sleep 1

# 启动docker-compose
echo "启动docker-compose"
docker-compose up -d || { echo "启动docker-compose失败"; exit 1; }
