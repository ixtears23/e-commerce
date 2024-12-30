package junseok.snr.ecommerce.order.api

import junseok.snr.ecommerce.order.application.exception.OrderNotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler
    fun handleOrderNotFoundException(exception: OrderNotFoundException): ResponseEntity<ApiErrorResponse> {
        return ResponseEntity.badRequest()
            .body(ApiErrorResponse(exception.error, exception.message ?: "orderNotFoundException"))
    }
}