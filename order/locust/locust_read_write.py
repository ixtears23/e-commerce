from locust import HttpUser, TaskSet, task, between
import random
import json

USER_IDS = [1, 2, 3, 4, 5]
PRODUCT_IDS = [101, 102, 103, 104, 105]
ORDER_STATUSES = ["PENDING", "PAID", "SHIPPED", "COMPLETED", "CANCELLED"]
# 테스트를 위해 생성된 주문 ID 목록 (실제 환경에서는 동적으로 조회 또는 생성해야 함)
ORDER_IDS = [1, 2, 3, 4, 5]

class AllUserBehavior(TaskSet):

    def on_start(self):
        self.order_ids = []

    @task(3)  # 주문 생성
    def create_order(self):
        user_id = random.choice(USER_IDS)
        product_id = random.choice(PRODUCT_IDS)
        quantity = random.randint(1, 5)
        price = round(random.uniform(10.0, 100.0), 2)

        headers = {'Content-Type': 'application/json'}
        payload = {
            "userId": user_id,
            "orderItems": [
                {
                    "productId": product_id,
                    "quantity": quantity,
                    "price": price
                }
            ]
        }

        with self.client.post("/api/v1/orders", json=payload, headers=headers, catch_response=True) as response:
            if response.status_code == 201:
                order_id = response.json()['order']['orderId']
                self.order_ids.append(order_id)
            else:
                response.failure("Failed to create order: " + response.text)

    @task(1)  # 주문 상태 업데이트
    def update_order_status(self):
        if not self.order_ids:
            return

        order_id = random.choice(self.order_ids)
        status = random.choice(ORDER_STATUSES)

        with self.client.put(f"/api/v1/orders/{order_id}/status?status={status}", catch_response=True) as response:
            if response.status_code != 200:
                response.failure("Failed to update order status: " + response.text)

    @task(2)  # 사용자 주문 목록 조회
    def get_orders_by_user_id(self):
        user_id = random.choice(USER_IDS)
        with self.client.get(f"/api/v1/orders/user/{user_id}", catch_response=True) as response:
            if response.status_code != 200:
                response.failure("Failed to get orders by user ID: " + response.text)

    @task(4)  # 주문 ID로 조회
    def get_order_by_id(self):
        if not self.order_ids and not ORDER_IDS:
            return

        # 생성한 주문 또는 기존 주문 ID 중에서 무작위 선택
        order_id = random.choice(self.order_ids if self.order_ids else ORDER_IDS)

        with self.client.get(f"/api/v1/orders/{order_id}", catch_response=True) as response:
            if response.status_code != 200:
                response.failure("Failed to get order by ID: " + response.text)

class AllUser(HttpUser):
    tasks = [AllUserBehavior]
    wait_time = between(1, 3)