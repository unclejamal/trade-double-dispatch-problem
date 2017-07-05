package com.pduda

import org.junit.Test

class CastTest {

    interface Trade {
        fun aggregate(aggregateId: String, other: Trade) : Trade
    }

    class CashTrade(val name: String) : Trade {

        override fun aggregate(aggregateId: String, other: Trade) : CashTrade {
            val otherCashTrade = other as CashTrade
            println("aggregate myself [CashTrade ${cashName()}] with other [CashTrade ${otherCashTrade.cashName()}]")
            return CashTrade("c1")
        }

        fun cashName(): String {
            return name
        }
    }

    class PaymentTrade(val name: String) : Trade {

        override fun aggregate(aggregateId: String, other: Trade) : PaymentTrade {
            val otherPaymentTrade = other as PaymentTrade
            println("aggregate myself [PaymentTrade ${paymentName()}] with other [PaymentTrade ${otherPaymentTrade.paymentName()}]")
            return PaymentTrade("p1")
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
        val paymentTrade1 = PaymentTrade("p1")
        val paymentTrade2 = PaymentTrade("p2")
        process(paymentTrade1, paymentTrade2)
    }

    private fun process(trade1: Trade, trade2: Trade) {
        trade1.aggregate("aggId", trade2)
    }
}

