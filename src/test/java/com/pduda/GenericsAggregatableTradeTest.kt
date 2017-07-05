package com.pduda

import org.junit.Test

class GenericsAggregatableTradeTest {

    interface Trade

    interface AggregatableTrade<T : Trade> {
        fun aggregate(aggregateId: String, other: T) : T
    }

    class CashTrade(val name: String) : Trade, AggregatableTrade<CashTrade> {

        override fun aggregate(aggregateId: String, other: CashTrade) : CashTrade {
            println("aggregate myself [CashTrade ${cashName()}] with other [CashTrade ${other.cashName()}]")
            return other.aggregate(aggregateId, this)
        }


        fun cashName(): String {
            return name
        }
    }

    @Test
    fun name() {
        val cashTrade1 = CashTrade("c1")
        val cashTrade2 = CashTrade("c2")
//        process(cashTrade1, cashTrade2)

    }

    private fun process(trade1: AggregatableTrade<Trade>, trade2: AggregatableTrade<Trade>) {
//        trade1.aggregate("aggId", trade2)
    }
}

