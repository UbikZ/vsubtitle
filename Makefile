.PHONY: usage

rootDir = $(shell pwd)
volDir = $(shell [ -n "$(DOCKER_SHARED)" ] && echo "$(DOCKER_SHARED)" || echo "$(rootDir)")/vsub-docker
nginxProxyId = "nginx-proxy"
containerIds = $(shell docker ps | grep vsub | awk '{print $$1}')
delImageIds = $(shell docker images -aq -f "dangling=true")

usage:
	@echo "JScraper Control :"
	@echo "  * make dev"

clean-docker:
	@for id in $(containerIds); do docker rm -f $$id; done
	@for id in $(delImageIds); do docker rmi -f $$id; done

clean-nginx:
	@for id in $(shell docker ps | grep $(nginxProxyId) | awk '{print $$1}'); do docker rm -f $$id; done

clean-db:
	@rm -rf $(volDir)/pgsql

clean-cache:
	@rm -rf $(volDir)/vsub/mvn

clean-logs:
	@rm -rf $(volDir)/vsub/log

nginx-up: clean-nginx
	@docker run --name $(nginxProxyId) -d -p 80:80 --env-file .env -v $(volDir)/nginx/sites-enabled:/etc/nginx/sites-enabled:ro -v $(volDir)/nginx/log:/var/log/nginx ubikz/docker-nginx-proxy

up-dev:
	@docker-compose -f docker-compose.yml up --build -d
	@docker kill -s HUP $(nginxProxyId)

dev: nginx-up clean-docker up-dev

dev-new: clean-db clean-logs dev