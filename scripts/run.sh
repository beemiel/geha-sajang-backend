#! /bin/bash

# application.yml을 S3에서 가져옴(IAM으로 S3 FullAccess를 줬으므로 가능)
echo "> Get Application.yml From S3"
aws s3 cp s3://lynn-test-deploy/application.yml /home/ubuntu/geha-sajang/src/main/resources/

# 기존거는 죽여버려
# 중간에는 프로젝트 제목이니까 잘 바꿔주자
echo "> Kill Current Application"
CURRENT_PID=$(ps -ef | grep geha-sajang | grep java | awk '{print $2}')
sudo kill -15 ${CURRENT_PID}

# 새로 어플리케이션 실행
echo "> Now new WAS runs."
nohup java -jar /home/ubuntu/geha-sajang/build/libs/* > /home/ubuntu/nohup.out 2>&1 &

