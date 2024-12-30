package junseok.snr.ecommerce.order.application.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class OrderNotFoundException(
    val error: OrderError,
    vararg args: String
) : RuntimeException(error.createMessage(*args)) {

    constructor(error: OrderError): this(error, *emptyArray())
}
