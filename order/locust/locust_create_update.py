from locust import HttpUser, TaskSet, task, between
import random
import json

# 사용자 데이터 및 상품 ID는 미리 생성되어 있다고 가정합니다.
# 실제 환경에서는 동적으로 생성하거나 외부 파일에서 읽어오는 것이 좋습니다.
USER_IDS = [1, 2, 3, 4, 5]
PRODUCT_IDS = [101, 102, 103, 104, 105]
ORDER_STATUSES = ["PENDING", "PAID", "SHIPPED", "COMPLETED", "CANCELLED"]

class CreateUpdateUserBehavior(TaskSet):

    def on_start(self):
        self.order_ids = []

    @task(4)  # 주문 생성 빈도 조절 가능 (예: 4배 더 자주 실행)
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

    @task(1)  # 주문 상태 업데이트 빈도 조절
    def update_order_status(self):
        if not self.order_ids:
            return

        order_id = random.choice(self.order_ids)
        status = random.choice(ORDER_STATUSES)

        with self.client.put(f"/api/v1/orders/{order_id}/status?status={status}", catch_response=True) as response:
            if response.status_code != 200:
                response.failure("Failed to update order status: " + response.text)

class CreateUpdateUser(HttpUser):
    tasks = [CreateUpdateUserBehavior]
    wait_time = between(1, 3)  # 사용자 행동 간 대기 시간