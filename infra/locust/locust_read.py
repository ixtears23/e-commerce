from locust import HttpUser, TaskSet, task, between
import random

USER_ID_RANGE = (1, 1000)

class ReadUserBehavior(TaskSet):

    def on_start(self):
        self.order_ids = [] # order_ids를 클래스 변수로 선언

    @task(2)
    def get_orders_by_user_id(self):
        user_id = random.randint(*USER_ID_RANGE)
        with self.client.get(f"/api/v1/orders/user/{user_id}", catch_response=True) as response:
            if response.status_code == 200:
                # 조회한 주문 중 order_id를 추출하여 저장 (필요한 경우)
                for order in response.json().get("orders", []):
                    order_id = order.get("orderId")
                    if order_id:
                        self.order_ids.append(order_id)
            else:
                response.failure("Failed to get orders by user ID: " + response.text)

    @task(3)
    def get_order_by_id(self):
        if not self.order_ids: # on_start에서 저장한 order_id가 없으면 on_start 호출
            self.get_orders_by_user_id()
            if not self.order_ids:
                return

        order_id = random.choice(self.order_ids) # 저장된 order_id 사용
        with self.client.get(f"/api/v1/orders/{order_id}", catch_response=True) as response:
            if response.status_code != 200:
                response.failure("Failed to get order by ID: " + response.text)

class ReadUser(HttpUser):
    tasks = [ReadUserBehavior]
    wait_time = between(1, 2)