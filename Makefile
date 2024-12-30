rebuild:
	docker compose down -v
	docker compose up --build -d
up:
	docker compose up -d
down:
	docker compose down -v