aws ecr get-login --no-include-email --region ap-southeast-2

sudo docker tag cryptosquaremono_frontend:latest 228549613180.dkr.ecr.ap-southeast-2.amazonaws.com/cryptosquaremono_frontend:latest

sudo docker push 228549613180.dkr.ecr.ap-southeast-2.amazonaws.com/cryptosquaremono_frontend:latest
