from locust import HttpUser, TaskSet, task, between
import random

# USER_IDS와 ORDER_IDS를 범위로 변경
USER_ID_RANGE = (1, 1000)
ORDER_ID_RANGE = (1, 100) # 생성된 주문 ID 범위로 수정 필요

class ReadUserBehavior(TaskSet):

    @task(2)
    def get_orders_by_user_id(self):
        user_id = random.randint(*USER_ID_RANGE)
        with self.client.get(f"/api/v1/orders/user/{user_id}", catch_response=True) as response:
            if response.status_code != 200:
                response.failure("Failed to get orders by user ID: " + response.text)

    @task(3)
    def get_order_by_id(self):
        order_id = random.randint(*ORDER_ID_RANGE)
        with self.client.get(f"/api/v1/orders/{order_id}", catch_response=True) as response:
            if response.status_code != 200:
                response.failure("Failed to get order by ID: " + response.text)

class ReadUser(HttpUser):
    tasks = [ReadUserBehavior]
    wait_time = between(1, 2)