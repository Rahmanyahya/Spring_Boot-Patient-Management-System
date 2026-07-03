service-up:
	@docker compose -f ./$(service)/docker-compose.yml up -d

service-down:
	@docker compose -f ./$(service)/docker-compose.yml down

rebuild:
	@docker compose -f ./$(service)/docker-compose.yml up -d --build
