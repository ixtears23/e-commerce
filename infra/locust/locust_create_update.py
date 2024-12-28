from locust import HttpUser, TaskSet, task, between
import random
import json

# PRODUCT_IDS를 범위로 변경
PRODUCT_ID_RANGE = (0, 100)  # 0 ~ 100
ORDER_STATUSES = ["PENDING", "PAID", "SHIPPED", "COMPLETED", "CANCELLED"]

class CreateUpdateUserBehavior(TaskSet):

    def on_start(self):
        self.order_ids = []  # 생성된 order_id를 저장할 리스트

    @task(4)
    def create_order(self):
        user_id = random.randint(1, 1000)  # USER_ID는 여전히 1~1000
        product_id = random.randint(*PRODUCT_ID_RANGE)  # 랜덤 productId 생성
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
                self.order_ids.append(order_id)  # 생성된 order_id 저장
            else:
                response.failure("Failed to create order: " + response.text)

    @task(1)
    def update_order_status(self):
        if not self.order_ids:
            return

        order_id = random.choice(self.order_ids)  # 저장된 order_id 사용
        status = random.choice(ORDER_STATUSES)

        with self.client.put(f"/api/v1/orders/{order_id}/status?status={status}", catch_response=True) as response:
            if response.status_code != 200:
                response.failure("Failed to update order status: " + response.text)

class CreateUpdateUser(HttpUser):
    tasks = [CreateUpdateUserBehavior]
    wait_time = between(1, 3)