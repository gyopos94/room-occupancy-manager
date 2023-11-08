# room-occupancy-manager

curl -X 'GET' \
'http://localhost:8080/api/revenue/available-rooms' \
-H 'Content-Type: application/json' \
-d '{
"rooms": {
"premium": 3,
"economy": 3
}
}'