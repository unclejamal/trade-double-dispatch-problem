package com.pduda

import org.junit.Assert.assertEquals
import org.junit.Test

class FunctionalTest {

    interface Aggregator {
        fun aggregate(aggregateId: String, first: Trade, second: Trade) : Trade
    }

    class CashTradeAggregator : Aggregator {
        override fun aggregate(aggregateId: String, first: Trade, second: Trade): Trade {
            println("aggregate first [CashTrade ${first.cashName()}] with other [CashTrade ${second.cashName()}]")
            return Trade(aggregateId)
        }

        fun Trade.cashName() : String {
            return name
        }
    }

    class PaymentTradeAggregator : Aggregator {
        override fun aggregate(aggregateId: String, first: Trade, second: Trade): Trade {
            println("aggregate myself [PaymentTrade ${first.paymentName()}] with other [Payment ${second.paymentName()}]")
            return Trade(aggregateId)
        }

        fun Trade.paymentName() : String {
            return name
        }
    }

    class Trade(val name: String) {

        fun isCashTrade() : Boolean {
            return name.startsWith("c")
        }

        fun isPaymentTrade() : Boolean {
            return name.startsWith("p")
        }

        // specific class related attributes shouldn't exist on this generic class
        // fun cashName(): String {
        //     return name
        // }
    }

    @Test
    fun cashTrades() {
        val cashTrade1 = Trade("c1")
        val cashTrade2 = Trade("c2")

        val result = process(cashTrade1, cashTrade2, "aggId")

        assertEquals(Trade("aggId").name, result.name)
    }

    @Test
    fun paymentTrades() {
        val paymentTrade1 = Trade("p1")
        val paymentTrade2 = Trade("p2")

        val result = process(paymentTrade1, paymentTrade2, "aggId")

        assertEquals(Trade("aggId").name, result.name)
    }

    private fun process(trade1: Trade, trade2: Trade, aggregationId: String) : Trade {
        if (trade1.isCashTrade() && trade2.isCashTrade()) {
            val aggregator = CashTradeAggregator()
            return aggregator.aggregate(aggregationId, trade1, trade2)
        } else if (trade1.isPaymentTrade() && trade2.isPaymentTrade()) {
            val aggregator = PaymentTradeAggregator()
            return aggregator.aggregate(aggregationId, trade1, trade2)
        } else {
            throw RuntimeException("no aggregator found")
        }

    }
}

