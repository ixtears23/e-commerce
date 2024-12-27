from locust import HttpUser, TaskSet, task, between
import random

USER_IDS = [1, 2, 3, 4, 5]
# 테스트를 위해 생성된 주문 ID 목록 (실제 환경에서는 동적으로 조회해야 함)
ORDER_IDS = [1, 2, 3, 4, 5]

class ReadUserBehavior(TaskSet):

    @task(2)  # 사용자 주문 목록 조회 빈도 조절
    def get_orders_by_user_id(self):
        user_id = random.choice(USER_IDS)
        with self.client.get(f"/api/v1/orders/user/{user_id}", catch_response=True) as response:
            if response.status_code != 200:
                response.failure("Failed to get orders by user ID: " + response.text)

    @task(3)  # 주문 ID로 조회 빈도 조절
    def get_order_by_id(self):
        if not ORDER_IDS:
            return # 주문이 없으면 종료

        order_id = random.choice(ORDER_IDS)
        with self.client.get(f"/api/v1/orders/{order_id}", catch_response=True) as response:
            if response.status_code != 200:
                response.failure("Failed to get order by ID: " + response.text)

class ReadUser(HttpUser):
    tasks = [ReadUserBehavior]
    wait_time = between(1, 2)