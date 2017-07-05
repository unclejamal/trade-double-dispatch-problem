package com.pduda

import org.junit.Test

class DoubleDispatchTest {

    interface Trade {
        fun aggregate(aggregateId: String, other: Trade) : Trade
        fun aggregate(aggregateId: String, other: PaymentTrade) : PaymentTrade
        fun aggregate(aggregateId: String, other: CashTrade) : CashTrade
    }

    class CashTrade(val name: String) : Trade {

        override fun aggregate(aggregateId: String, other: Trade) : CashTrade {
            println("aggregate myself [CashTrade ${cashName()}] with other [Trade ${other.javaClass}]")
            return other.aggregate(aggregateId, this)
        }

        override fun aggregate(aggregateId: String, other: CashTrade) : CashTrade {
            println("aggregate myself [CashTrade ${cashName()}] with other [CashTrade ${other.cashName()}]")
            return CashTrade(aggregateId)
        }

        override fun aggregate(aggregateId: String, other: PaymentTrade) : PaymentTrade {
            throw RuntimeException()
        }

        fun cashName(): String {
            return name
        }
    }

    class PaymentTrade(val name: String) : Trade {

        override fun aggregate(aggregateId: String, other: Trade) : PaymentTrade {
            println("aggregate myself [PaymentTrade ${paymentName()}] with other [Trade ${other.javaClass}]")
            return other.aggregate(aggregateId, this)
        }

        override fun aggregate(aggregateId: String, other: PaymentTrade) : PaymentTrade {
            println("aggregate myself [PaymentTrade ${paymentName()}] with other [PaymentTrade ${other.paymentName()}]")
            return PaymentTrade(aggregateId)
        }

        override fun aggregate(aggregateId: String, other: CashTrade) : CashTrade {
            throw RuntimeException()
        }

        fun paymentName(): String {
            return name
        }
    }

    @Test
    fun cash() {
        val cashTrade1 = CashTrade("c1")
        val cashTrade2 = CashTrade("c2")
        process(cashTrade1, cashTrade2)
    }

    @Test
    fun payment() {
        val paymentTrade1 = PaymentTrade("c1")
        val paymentTrade2 = PaymentTrade("c2")
        process(paymentTrade1, paymentTrade2)
    }

    private fun process(trade1: Trade, trade2: Trade) {
        trade1.aggregate("aggId", trade2)
    }
}

