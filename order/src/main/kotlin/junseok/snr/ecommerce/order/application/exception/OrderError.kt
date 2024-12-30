package junseok.snr.ecommerce.order.application.exception

enum class OrderError(private val messageSupplier: (Array<out String>) -> String) {
    ORDER_NOT_FOUND({ args -> "Order not found with id: ${args[0]}" }),
    NOTHING({ "Nothing" });

    fun createMessage(vararg args: String): String {
        return messageSupplier(args)
    }

    operator fun invoke(vararg args: String): String {
        return createMessage(*args)
    }
}