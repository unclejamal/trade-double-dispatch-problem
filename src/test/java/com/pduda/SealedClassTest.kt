package com.pduda

import org.junit.Test

class SealedClassTest {

    abstract sealed class Trade {
        class CashTrade(val name: String) : Trade() {

            fun aggregate(aggregateId: String, other: CashTrade) : CashTrade {
                println("aggregate myself [CashTrade ${cashName()}] with other [CashTrade ${other.cashName()}]")
                return CashTrade("c1")
            }

            fun cashName(): String {
                return name
            }
        }

        class PaymentTrade(val name: String) : Trade() {

            fun aggregate(aggregateId: String, other: PaymentTrade) : PaymentTrade {
                println("aggregate myself [PaymentTrade ${paymentName()}] with other [PaymentTrade ${other.paymentName()}]")
                return PaymentTrade("p1")
            }

            fun paymentName(): String {
                return name
            }
        }
    }



    @Test
    fun cash() {
        val cashTrade1 = Trade.CashTrade("c1")
        val cashTrade2 = Trade.CashTrade("c2")
        process(cashTrade1, cashTrade2)

    }

    @Test
    fun payment() {
        val paymentTrade1 = Trade.PaymentTrade("p1")
        val paymentTrade2 = Trade.PaymentTrade("p2")
        process(paymentTrade1, paymentTrade2)
    }

    private fun process(trade1: Trade, trade2: Trade) {
        when(trade1) {
            is Trade.CashTrade -> trade1.aggregate("agg1", trade1)
            is Trade.PaymentTrade -> trade1.aggregate("agg1", trade1)
        }
    }
}

